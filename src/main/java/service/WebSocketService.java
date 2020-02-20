package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitMqConfig;

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

    public Channel newWorkerChannel(String queueName) throws IOException, TimeoutException {
        Channel channel = newChannel(queueName);
        channel.basicQos(RabbitMqConfig.prefetchCount);

        return channel;
    }
}
