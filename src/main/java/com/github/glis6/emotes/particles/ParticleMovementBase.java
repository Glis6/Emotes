package com.github.glis6.emotes.particles;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Particle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
public abstract class ParticleMovementBase implements ParticleMovement {
    /**
     * The {@link Random} to use to pick the particles.
     */
    private final static Random RANDOM = new Random();

    /**
     * An array of {@link Particle}s to use.
     */
    @Getter(AccessLevel.PROTECTED)
    private final Particle[] particles;

    /**
     * @param particles A {@link Collection} of {@link Particle}s to use.
     */
    public ParticleMovementBase(Collection<Particle> particles) {
        this.particles = particles.toArray(new Particle[particles.size()]);
    }

    /**
     * @return A random particle from the given list.
     */
    protected Particle getRandomParticle() {
        if (getParticles().length == 1)
            return getParticles()[0];
        return getParticles()[RANDOM.nextInt(getParticles().length)];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<>();
        result.put("types", Arrays.stream(particles).map(Enum::name).collect(Collectors.toList()));
        return result;
    }
}
