package com.github.glis6.emotes.particles.movement;

import com.github.glis6.emotes.particles.ParticleData;
import com.github.glis6.emotes.particles.ParticleMovement;
import com.github.glis6.emotes.particles.ParticleReceiver;
import com.github.glis6.emotes.particles.ParticleReceiverType;
import org.bukkit.Particle;
import org.apache.commons.lang.SerializationException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
public class CircleMovement extends CircularMovement {
    /**
     * @param particles            A {@link Collection} of {@link Particle}s to use.
     * @param particleReceiverType A modifier to see who receives the final particle.
     * @param particleCount        The amount of particles in the circle.
     * @param radius               The radius of the circle.
     */
    public CircleMovement(Collection<Particle> particles, ParticleReceiverType particleReceiverType, int particleCount, double radius) {
        super(particles, particleReceiverType, particleCount, radius);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyParticles(ParticleReceiver executor, ParticleReceiver receiver) {
        final Consumer<ParticleData> particleConsumer = getParticleReceiverType().getParticleConsumer(executor, receiver);
        for(int i = 1; i <= getParticleCount(); i++) {
            double a = ((2 * Math.PI) / getParticleCount()) * i;
            double x = Math.cos(a) * getRadius();
            double z = Math.sin(a) * getRadius();
            particleConsumer.accept(new ParticleData(getRandomParticle(), x, 2, z));
        }
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
        double radius;
        Collection<Particle> particles;
        ParticleReceiverType particleReceiverType;

        if (args.containsKey("particle_count")) {
            particleCount = (Integer) args.get("particle_count");
        } else {
            particleCount = 20;
        }

        if (args.containsKey("radius")) {
            radius = (Double) args.get("radius");
        } else {
            radius = 0.5;
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

        return new CircleMovement(particles, particleReceiverType, particleCount, radius);
    }
}
