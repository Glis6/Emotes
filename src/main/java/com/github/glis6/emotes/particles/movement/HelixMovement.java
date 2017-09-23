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
public final class HelixMovement extends DefinedReceiverBase {

    /**
     * The radius to apply the particles around.
     */
    private final int radius;

    /**
     * @param radius               The radius to apply the particles around.
     * @param particles            A {@link Collection} of {@link Particle}s to use.
     * @param particleReceiverType A modifier to see who receives the final particle.
     */
    public HelixMovement(int radius, Collection<Particle> particles, ParticleReceiverType particleReceiverType) {
        super(particles, particleReceiverType);
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyParticles(ParticleReceiver executor, ParticleReceiver receiver) {
        final Consumer<ParticleData> particleConsumer = getParticleReceiverType().getParticleConsumer(executor, receiver);
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            particleConsumer.accept(new ParticleData(getRandomParticle(), x, y, z));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = super.serialize();
        result.put("radius", radius);
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
        int radius;
        Collection<Particle> particles;
        ParticleReceiverType particleReceiverType;

        if (args.containsKey("radius")) {
            radius = (Integer) args.get("radius");
        } else {
            radius = 1;
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

        return new HelixMovement(radius, particles, particleReceiverType);
    }
}
