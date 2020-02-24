package service;

import com.rabbitmq.client.*;
import config.RabbitMqConfig;
import webSocket.ConsumerParameters;
import webSocket.ProducerProperties;
import webSocket.QueueProperties;

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

    public Channel newQueueChannel(String queueName, QueueProperties queueProperties) throws IOException, TimeoutException {
        Channel channel = newChannel();

        channel.queueDeclare(queueName, queueProperties.isDurable(), queueProperties.isExclusive(), queueProperties.isAutoDelete(), queueProperties.getArguments());
        return channel;
    }

    public Channel newExchangeChannel(String exchangeName, BuiltinExchangeType builtinExchangeType) throws IOException, TimeoutException {
        Channel channel = newChannel();

        channel.exchangeDeclare(exchangeName, builtinExchangeType);
        return channel;
    }

    public Channel newConsumerChannel(String queueName, QueueProperties queueProperties, ConsumerParameters consumerParameters) throws IOException, TimeoutException {
        Channel channel = newQueueChannel(queueName, queueProperties);

        if (consumerParameters.getPrefetchCount() != null) {
            channel.basicQos(consumerParameters.getPrefetchCount());
        }
        return channel;
    }

    public void publishMessage(Channel channel, String exchangeName, String queueName, ProducerProperties producerProperties, String message) throws IOException {
        channel.basicPublish(exchangeName,
                             queueName,
                             producerProperties.getMessageProperties(),
                             message.getBytes(StandardCharsets.UTF_8));
    }

    public DeliverCallback defaultDeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
    };
}
