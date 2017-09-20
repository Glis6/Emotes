package com.glis.emotes;

/**
 * @author Glis
 */
public interface EmoteMessageProvider {
    /**
     * @return A message displayed when the permissions to use the emote aren't high enough.
     */
    String getInsufficientPermissionsMessage();

    /**
     * @return A message displayed when the emote is still on cooldown.
     */
    String getCooldownMessage();
}
