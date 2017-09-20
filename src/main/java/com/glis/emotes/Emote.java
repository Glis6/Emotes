package com.glis.emotes;

import lombok.Data;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a single emote. Emotes are defined in the config file.
 *
 * @author Glis
 */
@Data
public class Emote implements IEmote {
    /**
     * A {@link Map} of cooldowns. Mapped by the {@link Player#getUniqueId()}.
     */
    private final Map<String, Long> cooldowns = new HashMap<>();

    /**
     * The easter egg messages. When an emote is used on an entity that is in this list
     * then it'll give a special different message.
     */
    private final Map<String, MessagePair> easterEggs;

    /**
     * The name of the emote.
     */
    private final String emoteName;

    /**
     * The cooldown between each of the emotes.
     */
    private final long cooldown;

    /**
     * The permissions required to do this emote.
     */
    private final Collection<Permission> requiredPermissions;

    /**
     * The message that the executor will receive upon doing the emote.
     * This message can be empty or null and will not be displayed in that case.
     */
    private final String executorMessage;

    /**
     * The message that the receiving player will get.
     */
    private final String receiverMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public IEmoteResult execute(final Player executor, final Entity receiver) {
        //First we'll check the cooldown since that is the easiest one to check in terms of
        //operation time.
        if (getCooldowns().getOrDefault(executor.getUniqueId().toString(), System.currentTimeMillis()) + getCooldown() > System.currentTimeMillis()) {
            return new EmoteResult(emotePlugin -> executor.sendMessage(emotePlugin.getCooldownMessage()));
        }

        //After we check the permissions.
        if (!getRequiredPermissions().stream().allMatch(executor::hasPermission)) {
            return new EmoteResult(emotePlugin -> executor.sendMessage(emotePlugin.getInsufficientPermissionsMessage()));
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
            cooldowns.put(executor.getUniqueId().toString(), System.currentTimeMillis());
            Optional.ofNullable(messagePair.getExecutorMessage()).ifPresent(executor::sendMessage);
            Optional.ofNullable(messagePair.getReceiverMessage()).ifPresent(receiver::sendMessage);
        });
    }
}
