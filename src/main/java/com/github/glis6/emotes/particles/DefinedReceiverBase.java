package com.github.glis6.emotes.particles;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Particle;

import java.util.Collection;
import java.util.Map;

/**
 * @author Glis
 */
public abstract class DefinedReceiverBase extends ParticleMovementBase {
    /**
     * A modifier to see who receives the final particle.
     */
    @Getter(AccessLevel.PROTECTED)
    private final ParticleReceiverType particleReceiverType;

    /**
     * @param particles            A {@link Collection} of {@link Particle}s to use.
     * @param particleReceiverType A modifier to see who receives the final particle.
     */
    public DefinedReceiverBase(Collection<Particle> particles, ParticleReceiverType particleReceiverType) {
        super(particles);
        this.particleReceiverType = particleReceiverType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = super.serialize();
        result.put("receiver_type", particleReceiverType.name());
        return result;
    }
}
