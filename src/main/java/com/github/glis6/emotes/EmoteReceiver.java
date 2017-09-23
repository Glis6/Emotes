package com.github.glis6.emotes;

import com.github.glis6.emotes.particles.ParticleReceiver;
import com.github.glis6.emotes.particles.ParticleData;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public class EmoteReceiver implements ParticleReceiver {
    /**
     * The name of the {@link EmoteReceiver}.
     */
    @Getter
    private final String name;

    /**
     * The {@link Consumer} to send the message.
     */
    @Getter
    private final Consumer<String> messageSender;

    /**
     * The {@link Consumer} to display the particles.
     */
    @Getter
    private final Consumer<ParticleData> particleConsumer;

    /**
     * @param name The name of the {@link EmoteReceiver}.
     * @param messageSender The {@link Consumer} to send the message.
     */
    public EmoteReceiver(String name, Consumer<String> messageSender, Consumer<ParticleData> particleConsumer) {
        this.name = name;
        this.messageSender = messageSender;
        this.particleConsumer = particleConsumer;
    }

    /**
     * @param message The message to send to the {@link EmoteReceiver}.
     */
    public void sendMessage(String message) {
        messageSender.accept(message);
    }
}
