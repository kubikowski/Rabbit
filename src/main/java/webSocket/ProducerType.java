package webSocket;

import com.rabbitmq.client.AMQP.BasicProperties;

public class ProducerType {

    private final BasicProperties messageProperties;    // If Persistent, Messages will persist to Disk

    public ProducerType(BasicProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    public BasicProperties getMessageProperties() {
        return messageProperties;
    }
}
