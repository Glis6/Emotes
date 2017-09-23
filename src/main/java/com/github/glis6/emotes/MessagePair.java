package com.github.glis6.emotes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
public interface MessagePair extends ConfigurationSerializable {
    /**
     * @return The message that the executor will receive.
     */
    String getExecutorMessage();

    /**
     * @return The message that the receiver will get.
     */
    String getReceiverMessage();

    /**
     * @param message The message to set for the executor.
     */
    void setExecutorMessage(String message);

    /**
     * @param message The message to set for the receiver.
     */
    void setReceiverMessage(String message);

    /**
     * {@inheritDoc}
     */
    default Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<>();
        result.put("executor_message", getExecutorMessage());
        result.put("receiver_message", getReceiverMessage());
        return result;
    }
}
