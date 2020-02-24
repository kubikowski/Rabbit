package webSocket;

import com.rabbitmq.client.AMQP.BasicProperties;

public class ProducerProperties {

    private final BasicProperties messageProperties;    // If Persistent, Messages will persist to Disk

    public ProducerProperties(BasicProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    public BasicProperties getMessageProperties() {
        return messageProperties;
    }
}
