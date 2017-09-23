package com.github.glis6.emotes.particles;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * @author Glis
 */
public interface ParticleMovement extends ConfigurationSerializable {
    /**
     * Applies the particles.
     *
     * @param executor The {@link ParticleReceiver} executing the emote.
     * @param receiver The {@link ParticleReceiver} receiving the emote.
     */
    void applyParticles(ParticleReceiver executor, ParticleReceiver receiver);
}
