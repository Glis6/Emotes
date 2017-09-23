package com.github.glis6.emotes.particles;

import org.bukkit.Particle;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public enum ParticleReceiverType {
    EXECUTOR {
        @Override
        public Consumer<ParticleData> getParticleConsumer(ParticleReceiver executor, ParticleReceiver receiver) {
            return executor.getParticleConsumer();
        }
    },
    RECEIVER {
        @Override
        public Consumer<ParticleData> getParticleConsumer(ParticleReceiver executor, ParticleReceiver receiver) {
            return receiver.getParticleConsumer();
        }
    },
    BOTH {
        @Override
        public Consumer<ParticleData> getParticleConsumer(ParticleReceiver executor, ParticleReceiver receiver) {
            return particle -> {
                executor.getParticleConsumer().accept(particle);
                receiver.getParticleConsumer().accept(particle);
            };
        }
    };

    /**
     * This will modify the consumer to apply to one of the receivers or both.
     *
     * @param executor The {@link ParticleReceiver} executing the emote.
     * @param receiver The {@link ParticleReceiver} receiving the emote.
     * @return A {@link Consumer} for {@link Particle}s that applies to the correct receiver.
     */
    public abstract Consumer<ParticleData> getParticleConsumer(ParticleReceiver executor, ParticleReceiver receiver);
}
