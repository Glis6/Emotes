package com.glis.emotes;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.Collection;
import java.util.Map;

/**
 * @author Glis
 */
public interface IEmote {
    /**
     * This will actually do the emote.
     *
     * @param executor The {@link Player} executing the emote.
     * @param receiver The {@link Entity} receiving the emote.
     */
    IEmoteResult execute(Player executor, Entity receiver);

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
}
