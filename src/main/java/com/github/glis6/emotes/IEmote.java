package com.github.glis6.emotes;

import com.github.glis6.emotes.particles.ParticleMovement;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public interface IEmote extends ConfigurationSerializable {
    /**
     * This will actually do the emote.
     *
     * @param executor The {@link Player} executing the emote.
     * @param receiver The {@link EmoteReceiver} receiving the emote.
     */
    IEmoteResult execute(Player executor, EmoteReceiver receiver);

    /**
     * @return A {@link Map} of cooldowns. Mapped by the {@link Player#getUniqueId()}.
     */
    Map<String, Long> getCooldowns();

    /**
     * @return The easter egg messages. When an emote is used on an entity that is in this list
     * then it'll give a special different message.
     */
    Map<String, MessagePair> getEasterEggs();

    /**
     * @return The name of the emote.
     */
    String getEmoteName();

    /**
     * @return The cooldown between each of the emotes.
     */
    long getCooldown();

    /**
     * @return The permissions required to do this emote.
     */
    Collection<Permission> getRequiredPermissions();

    /**
     * @return The message that the executor will receive upon doing the emote.
     */
    String getExecutorMessage();

    /**
     * @return The message that the receiving player will get.
     */
    String getReceiverMessage();

    /**
     * The {@link ParticleMovement} for this emote. This can be null.
     */
    ParticleMovement getParticleMovement();

    /**
     * {@inheritDoc}
     */
    default Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<>();
        result.put("name", getEmoteName());
        result.put("cooldown", getCooldown());
        result.put("particle", getParticleMovement());
        result.put("executor_message", getExecutorMessage());
        result.put("receiver_message", getReceiverMessage());
        result.put("required_permissions", getRequiredPermissions());
        result.put("easter_eggs", getEasterEggs());
        return result;
    }
}
