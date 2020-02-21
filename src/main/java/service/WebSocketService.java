package service;

import com.rabbitmq.client.*;
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

    public Channel newExchangeChannel(String exchangeName, BuiltinExchangeType builtinExchangeType) throws IOException, TimeoutException {
        Channel channel = newChannel();

        channel.exchangeDeclare(exchangeName, builtinExchangeType);
        return channel;
    }

    public Channel newConsumerChannel(String queueName, QueueType queueType, ConsumerType consumerType) throws IOException, TimeoutException {
        Channel channel = newQueueChannel(queueName, queueType);

        if (consumerType.getPrefetchCount() != null) {
            channel.basicQos(consumerType.getPrefetchCount());
        }
        return channel;
    }

    public void publishMessage(Channel channel, String exchangeName, String queueName, ProducerType producerType, String message) throws IOException {
        channel.basicPublish(exchangeName,
                             queueName,
                             producerType.getMessageProperties(),
                             message.getBytes(StandardCharsets.UTF_8));
    }

    public DeliverCallback defaultDeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
    };
}
