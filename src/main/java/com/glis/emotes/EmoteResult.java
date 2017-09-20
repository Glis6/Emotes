package com.glis.emotes;

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
    private final Consumer<EmotePlugin> result;

    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer<EmotePlugin> result() {
        return result;
    }
}
