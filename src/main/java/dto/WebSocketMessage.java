package dto;

import com.rabbitmq.client.AMQP;

import java.nio.charset.StandardCharsets;

public class WebSocketMessage {

    private String queueName;
    private byte[] message;

    private WebSocketMessage(String queueName, byte[] message) {
        this.queueName = queueName;
        this.message = message;
    }

    public static WebSocketMessage from(String queueName, String message) {
        return new WebSocketMessage(queueName,
                                    message.getBytes(StandardCharsets.UTF_8));
    }

    public String getQueueName() {
        return queueName;
    }

    public byte[] getMessage() {
        return message;
    }
}
