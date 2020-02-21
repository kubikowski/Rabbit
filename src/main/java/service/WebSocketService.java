package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitMqConfig;
import dto.WebSocketMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WebSocketService {

    public Channel newChannel(String queueName) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConfig.hostLocation);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, RabbitMqConfig.durable, false, false, null);
        return channel;
    }

    public Channel newConsumerChannel(String queueName, ConsumerType consumerType) throws IOException, TimeoutException {
        Channel channel = newChannel(queueName);

        if (consumerType.getPrefetchCount() != null) {
            channel.basicQos(consumerType.getPrefetchCount());
        }

        return channel;
    }

    public void publishMessage(WebSocketMessage webSocketMessage) throws IOException {
        webSocketMessage.getChannel().basicPublish("",
                                                   webSocketMessage.getQueueName(),
                                                   RabbitMqConfig.messageProperties,
                                                   webSocketMessage.getMessage());
    }
}
