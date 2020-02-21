package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitMqConfig;
import webSocket.ConsumerType;
import webSocket.ProducerType;
import webSocket.QueueType;
import webSocket.WebSocketMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WebSocketService {

    public Channel newChannel(String queueName, QueueType queueType) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConfig.hostLocation);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, queueType.isDurable(), false, false, null);
        return channel;
    }

    public Channel newConsumerChannel(String queueName, QueueType queueType, ConsumerType consumerType) throws IOException, TimeoutException {
        Channel channel = newChannel(queueName, queueType);

        if (consumerType.getPrefetchCount() != null) {
            channel.basicQos(consumerType.getPrefetchCount());
        }

        return channel;
    }

    public void publishMessage(WebSocketMessage webSocketMessage, ProducerType producerType) throws IOException {
        webSocketMessage.getChannel()
                        .basicPublish("",
                                      webSocketMessage.getQueueName(),
                                      producerType.getMessageProperties(),
                                      webSocketMessage.getMessage());
    }
}
