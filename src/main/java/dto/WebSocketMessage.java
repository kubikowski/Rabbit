package dto;

import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

public class WebSocketMessage {

    private Channel channel;
    private String queueName;
    private byte[] message;

    public WebSocketMessage(Channel channel, String queueName, byte[] message) {
        this.channel = channel;
        this.queueName = queueName;
        this.message = message;
    }

    public static WebSocketMessage from(Channel channel, String queueName, String message) {
        return new WebSocketMessage(channel,
                                    queueName,
                                    message.getBytes(StandardCharsets.UTF_8));
    }

    public Channel getChannel() {
        return channel;
    }

    public String getQueueName() {
        return queueName;
    }

    public byte[] getMessage() {
        return message;
    }
}
