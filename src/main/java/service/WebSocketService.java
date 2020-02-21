package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitMqConfig;
import webSocket.ConsumerType;
import webSocket.ProducerType;
import webSocket.QueueType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class WebSocketService {

    public Channel newChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqConfig.HOST_LOCATION);

        final Connection connection = factory.newConnection();
        return connection.createChannel();
    }

    public Channel newQueueChannel(String queueName, QueueType queueType) throws IOException, TimeoutException {
        Channel channel = newChannel();

        channel.queueDeclare(queueName, queueType.isDurable(), queueType.isExclusive(), queueType.isAutoDelete(), queueType.getArguments());
        return channel;
    }

    public Channel newConsumerChannel(String queueName, QueueType queueType, ConsumerType consumerType) throws IOException, TimeoutException {
        Channel channel = newQueueChannel(queueName, queueType);

        if (consumerType.getPrefetchCount() != null) {
            channel.basicQos(consumerType.getPrefetchCount());
        }
        return channel;
    }

    public void publishMessage(Channel channel, String queueName, ProducerType producerType, String message) throws IOException {
        channel.basicPublish("",
                             queueName,
                             producerType.getMessageProperties(),
                             message.getBytes(StandardCharsets.UTF_8));
    }
}
