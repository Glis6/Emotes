package com.glis.emotes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;

/**
 * @author Glis
 */
@SerializableAs("MessagePair")
public final class SimpleMessagePair implements MessagePair {
    /**
     * The message to be sent to the receiver.
     */
    @Getter
    @Setter
    private String receiverMessage;

    /**
     * The message to be sent to the executor.
     */
    @Getter
    @Setter
    private String executorMessage;

    /**
     * @param executorMessage The message to be sent to the executor.
     * @param receiverMessage The message to be sent to the receiver.
     */
    public SimpleMessagePair(String executorMessage, String receiverMessage) {
        this.receiverMessage = receiverMessage;
        this.executorMessage = executorMessage;
    }

    /**
     * Deserializes the object with Bukkit.
     *
     * @param args The arguments given.
     * @return The new object.
     */
    @SuppressWarnings("unchecked")
    public static SimpleMessagePair deserialize(Map<String, Object> args) throws Exception {
        String receiverMessage;
        String executorMessage;

        if (args.containsKey("receiver_message")) {
            receiverMessage = (String) args.get("receiver_message");
        } else {
            throw new Exception("Could not deserialize SimpleMessagePair, missing receiver message.");
        }

        if (args.containsKey("executor_message")) {
            executorMessage = (String) args.get("executor_message");
        } else {
            throw new Exception("Could not deserialize SimpleMessagePair, missing executor message.");
        }
        return new SimpleMessagePair(executorMessage, receiverMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SimpleMessagePair{" +
                "receiverMessage='" + receiverMessage + '\'' +
                ", executorMessage='" + executorMessage + '\'' +
                '}';
    }
}
