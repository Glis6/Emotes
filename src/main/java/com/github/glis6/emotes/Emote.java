package com.github.glis6.emotes;

import com.github.glis6.emotes.particles.ParticleMovement;
import lombok.Getter;
import org.apache.commons.lang.SerializationException;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.*;

/**
 * Represents a single emote. Emotes are defined in the config file.
 *
 * @author Glis
 */
@SerializableAs("Emote")
public class Emote implements IEmote {

    /**
     * The name of the emote.
     */
    @Getter
    private final String emoteName;

    /**
     * The cooldown between each of the emotes.
     */
    @Getter
    private final long cooldown;

    /**
     * The message that the executor will receive upon doing the emote.
     * This message can be empty or null and will not be displayed in that case.
     */
    @Getter
    private final String executorMessage;

    /**
     * The message that the receiving player will get.
     */
    @Getter
    private final String receiverMessage;

    /**
     * The {@link ParticleMovement} for this emote. This can be null.
     */
    @Getter
    private final ParticleMovement particleMovement;

    /**
     * The permissions required to do this emote.
     */
    @Getter
    private final Collection<Permission> requiredPermissions;

    /**
     * The easter egg messages. When an emote is used on an entity that is in this list
     * then it'll give a special different message.
     */
    @Getter
    private final Map<String, MessagePair> easterEggs;

    /**
     * A {@link Map} of cooldowns. Mapped by the {@link Player#getUniqueId()}.
     */
    @Getter
    @SuppressWarnings("all")
    private final Map<String, Long> cooldowns = new HashMap<>();

    /**
     * @param emoteName           The name of the emote.
     * @param cooldown            The cooldown between each of the emotes.
     * @param executorMessage     The message that the executor will receive upon doing the emote.
     * @param receiverMessage     The message that the receiving player will get.
     * @param particleMovement    The {@link ParticleMovement} for this emote. This can be null.
     * @param requiredPermissions The permissions required to do this emote.
     * @param easterEggs          The easter egg messages. When an emote is used on an entity that is in this list.
     */
    public Emote(String emoteName, long cooldown, String executorMessage, String receiverMessage, ParticleMovement particleMovement, Collection<Permission> requiredPermissions, Map<String, MessagePair> easterEggs) {
        this.emoteName = emoteName;
        this.cooldown = cooldown;
        this.executorMessage = executorMessage;
        this.receiverMessage = receiverMessage;
        this.particleMovement = particleMovement;
        this.requiredPermissions = requiredPermissions;
        this.easterEggs = easterEggs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmoteResult execute(final Player executor, final EmoteReceiver receiver) {
        //First we'll check if we're actually hugging something.
        if(receiver.getName().equalsIgnoreCase("air")) {
            return new EmoteResult(emoteMessageProvider -> executor.sendMessage(emoteMessageProvider.getNoTargetMessage()));
        }
        //We'll check the cooldown since that is the easiest one to check in terms of
        //operation time.
        final long nextUse = getCooldowns().getOrDefault(executor.getUniqueId().toString(), System.currentTimeMillis());
        if (nextUse > System.currentTimeMillis()) {
            return new EmoteResult(emoteMessageProvider -> executor.sendMessage(emoteMessageProvider.getCooldownMessage().replace("%s", Integer.toString(Math.round((nextUse - System.currentTimeMillis()) / 1000)))));
        }

        //After we check the permissions.
        if (!getRequiredPermissions().stream().allMatch(executor::hasPermission)) {
            return new EmoteResult(emoteMessageProvider -> executor.sendMessage(emoteMessageProvider.getInsufficientPermissionsMessage()));
        }

        //We define the default message that will be received.
        final MessagePair messagePair = new SimpleMessagePair(getExecutorMessage(), getReceiverMessage());

        //Now we check if there is an easter egg for this entity.
        Optional.ofNullable(getEasterEggs().get(receiver.getName())).ifPresent(easterEgg -> {
            //If there is then we replace the current messages.
            messagePair.setExecutorMessage(easterEgg.getExecutorMessage());
            messagePair.setReceiverMessage(easterEgg.getReceiverMessage());
        });

        return new EmoteResult(emotePlugin -> {
            cooldowns.put(executor.getUniqueId().toString(), System.currentTimeMillis() + getCooldown());
            Optional.ofNullable(messagePair.getExecutorMessage()).ifPresent(s -> executor.sendMessage(s.replace("%r", receiver.getName())));
            Optional.ofNullable(messagePair.getReceiverMessage()).ifPresent(s -> receiver.sendMessage(s.replace("%e", executor.getName())));
            Optional.ofNullable(particleMovement).ifPresent(consumer -> consumer.applyParticles(() -> particleData -> {
                double x = executor.getLocation().getX() + particleData.getX();
                double y = executor.getLocation().getY() + particleData.getY();
                double z = executor.getLocation().getZ() + particleData.getZ();
                executor.spawnParticle(particleData.getParticle(), x, y, z, 1);
            }, receiver));
        });
    }

    /**
     * Deserializes the object with Bukkit.
     *
     * @param args The arguments given.
     * @return The new object.
     */
    @SuppressWarnings("unchecked")
    public static Emote deserialize(Map<String, Object> args) throws SerializationException {
        String emoteName;
        long cooldown;
        String executorMessage;
        String receiverMessage;
        ParticleMovement particleMovement;
        Collection<Permission> requiredPermissions;
        Map<String, MessagePair> easterEggs;

        if (args.containsKey("name")) {
            emoteName = (String) args.get("name");
        } else {
            throw new SerializationException("Could not deserialize Emote, missing name.");
        }

        if (args.containsKey("cooldown")) {
            cooldown = (Integer) args.get("cooldown");
        } else {
            cooldown = 0;
        }

        if (args.containsKey("executor_message")) {
            executorMessage = (String) args.get("executor_message");
        } else {
            throw new SerializationException("Could not deserialize Emote, missing executor message.");
        }

        if (args.containsKey("receiver_message")) {
            receiverMessage = (String) args.get("receiver_message");
        } else {
            throw new SerializationException("Could not deserialize Emote, missing receiver message.");
        }

        if (args.containsKey("particle")) {
            particleMovement = (ParticleMovement) args.get("particle");
        } else {
            particleMovement = null;
        }

        if (args.containsKey("required_permissions")) {
            requiredPermissions = (Collection<Permission>) args.get("required_permissions");
        } else {
            requiredPermissions = new ArrayList<>();
        }

        if (args.containsKey("easter_eggs")) {
            easterEggs = (Map<String, MessagePair>) args.get("easter_eggs");
        } else {
            easterEggs = new HashMap<>();
        }
        return new Emote(emoteName, cooldown, executorMessage, receiverMessage, particleMovement, requiredPermissions, easterEggs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Emote{" +
                "emoteName='" + emoteName + '\'' +
                ", cooldown=" + cooldown +
                ", executorMessage='" + executorMessage + '\'' +
                ", receiverMessage='" + receiverMessage + '\'' +
                ", particleMovement=" + particleMovement +
                ", requiredPermissions=" + requiredPermissions +
                ", easterEggs=" + easterEggs +
                ", cooldowns=" + cooldowns +
                '}';
    }
}
