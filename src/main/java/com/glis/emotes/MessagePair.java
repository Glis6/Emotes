package com.glis.emotes;

/**
 * @author Glis
 */
public interface MessagePair {
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
}
