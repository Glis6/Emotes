package com.github.glis6.emotes.particles;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public interface ParticleReceiver {
    /**
     * @return The consumer that will apply the particle.
     */
    Consumer<ParticleData> getParticleConsumer();
}
