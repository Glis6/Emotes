package com.github.glis6.emotes.particles.movement;

import com.github.glis6.emotes.particles.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang.SerializationException;
import org.bukkit.Particle;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
public abstract class CircularMovement extends DefinedReceiverBase {
    /**
     * The amount of particles in the circle.
     */
    @Getter(AccessLevel.PROTECTED)
    private final int particleCount;

    /**
     * The radius to apply the particles around.
     */
    @Getter(AccessLevel.PROTECTED)
    private final double radius;

    /**
     * @param particles            A {@link Collection} of {@link Particle}s to use.
     * @param particleReceiverType A modifier to see who receives the final particle.
     * @param particleCount        The amount of particles in the circle.
     */
    public CircularMovement(Collection<Particle> particles, ParticleReceiverType particleReceiverType, int particleCount, double radius) {
        super(particles, particleReceiverType);
        this.particleCount = particleCount;
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyParticles(ParticleReceiver executor, ParticleReceiver receiver) {
        final Consumer<ParticleData> particleConsumer = getParticleReceiverType().getParticleConsumer(executor, receiver);
        for(int i = 1; i <= particleCount; i++) {
            double a = ((2 * Math.PI) / particleCount) * i;
            double x = Math.cos(a) * radius;
            double z = Math.sin(a) * radius;
            particleConsumer.accept(new ParticleData(getRandomParticle(), x, 2, z));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = super.serialize();
        result.put("particle_count", particleCount);
        result.put("radius", radius);
        return result;
    }
}
