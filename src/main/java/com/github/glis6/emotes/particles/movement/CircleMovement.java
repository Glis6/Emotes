package com.github.glis6.emotes.particles.movement;

import com.github.glis6.emotes.particles.*;
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
public final class CircleMovement extends DefinedReceiverBase {
    /**
     * The amount of particles in the circle.
     */
    private final int particleCount;

    /**
     * @param particles            A {@link Collection} of {@link Particle}s to use.
     * @param particleReceiverType A modifier to see who receives the final particle.
     * @param particleCount        The amount of particles in the circle.
     */
    public CircleMovement(Collection<Particle> particles, ParticleReceiverType particleReceiverType, int particleCount) {
        super(particles, particleReceiverType);
        this.particleCount = particleCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyParticles(ParticleReceiver executor, ParticleReceiver receiver) {
        final Consumer<ParticleData> particleConsumer = getParticleReceiverType().getParticleConsumer(executor, receiver);
        for(int i = 0; i < particleCount; i++) {
            double a = 2 * Math.PI / particleCount * i;
            double x = Math.cos(a) * 1;
            double z = Math.sin(a) * 1;
            particleConsumer.accept(new ParticleData(getRandomParticle(), x, 0.5, z));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = super.serialize();
        result.put("particle_count", particleCount);
        return result;
    }

    /**
     * Deserializes the object with Bukkit.
     *
     * @param args The arguments given.
     * @return The new object.
     */
    @SuppressWarnings("unchecked")
    public static ParticleMovement deserialize(Map<String, Object> args) throws SerializationException {
        int particleCount;
        Collection<Particle> particles;
        ParticleReceiverType particleReceiverType;

        if (args.containsKey("particle_count")) {
            particleCount = (Integer) args.get("particle_count");
        } else {
            particleCount = 20;
        }

        if (args.containsKey("types")) {
            List<String> particleNames = (List<String>) args.get("types");
            particles = particleNames.stream().map(Particle::valueOf).collect(Collectors.toList());
        } else {
            throw new SerializationException("Could not deserialize HelixMovement, missing types.");
        }

        try {
            if (args.containsKey("receiver_type")) {
                particleReceiverType = ParticleReceiverType.valueOf((String) args.get("receiver_type"));
            } else {
                throw new RuntimeException("No receiver type included. Controlled exception.");
            }
        } catch (Exception ignored) {
            particleReceiverType = ParticleReceiverType.EXECUTOR;
        }

        return new CircleMovement(particles, particleReceiverType, particleCount);
    }
}
