package com.github.glis6.emotes;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author Glis
 */
@Data
public class EmoteResult implements IEmoteResult {
    /**
     * The result from executing the plugin.
     */
    private final Consumer<EmoteMessageProvider> result;

    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer<EmoteMessageProvider> result() {
        return result;
    }
}
