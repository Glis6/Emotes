package com.glis.emotes;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public final class NoEmoteResult implements IEmoteResult {
    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer<EmotePlugin> result() {
        return null;
    }
}
