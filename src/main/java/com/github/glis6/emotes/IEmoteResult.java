package com.github.glis6.emotes;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public interface IEmoteResult {
    /**
     * @return The result from executing the emote.
     */
    Consumer<EmoteMessageProvider> result();
}
