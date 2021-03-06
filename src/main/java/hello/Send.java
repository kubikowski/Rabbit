package hello;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ProducerProperties;
import webSocket.QueueProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.DEFAULT_EXCHANGE_NAME;
        final String queueName = RabbitMqConfig.HELLO_QUEUE_NAME;
        final QueueProperties queueProperties = RabbitMqConfig.NON_DURABLE_QUEUE;
        final ProducerProperties producerProperties = RabbitMqConfig.BASIC_PRODUCER;

        final Channel channel = webSocketService.newQueueChannel(queueName, queueProperties);

        String message = "Hello Java!";
        webSocketService.publishMessage(channel, exchangeName, queueName, producerProperties, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
