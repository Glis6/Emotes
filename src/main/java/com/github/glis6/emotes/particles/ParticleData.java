package com.github.glis6.emotes.particles;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * @author Glis
 */
@Data
public final class ParticleData {
    /**
     * The {@link Particle} to use.
     */
    private final Particle particle;

    /**
     * The relative {@link Location#x} to spawn the particle at.
     */
    private final double x;

    /**
     * The relative {@link Location#y} to spawn the particle at.
     */
    private final double y;

    /**
     * The relative {@link Location#z} to spawn the particle at.
     */
    private final double z;
}
