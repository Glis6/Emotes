package com.glis.emotes;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Glis
 */
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
     * @param receiverMessage The message to be sent to the receiver.
     * @param executorMessage The message to be sent to the executor.
     */
    public SimpleMessagePair(String receiverMessage, String executorMessage) {
        this.receiverMessage = receiverMessage;
        this.executorMessage = executorMessage;
    }
}
