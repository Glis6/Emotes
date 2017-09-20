package com.glis.emotes;

import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author Glis
 */
public class EmoteReceiver {
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
     * @param name The name of the {@link EmoteReceiver}.
     * @param messageSender The {@link Consumer} to send the message.
     */
    public EmoteReceiver(String name, Consumer<String> messageSender) {
        this.name = name;
        this.messageSender = messageSender;
    }

    /**
     * @param message The message to send to the {@link EmoteReceiver}.
     */
    public void sendMessage(String message) {
        messageSender.accept(message);
    }
}
