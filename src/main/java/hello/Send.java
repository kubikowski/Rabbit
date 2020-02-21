package hello;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ProducerType;
import webSocket.QueueType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String queueName = RabbitMqConfig.HELLO_QUEUE_NAME;
        final QueueType queueType = RabbitMqConfig.NON_DURABLE_QUEUE;
        final ProducerType producerType = RabbitMqConfig.BASIC_PRODUCER;
        final Channel channel = webSocketService.newQueueChannel(queueName, queueType);

        String message = "Hello Java!";
        webSocketService.publishMessage(channel,
                                        RabbitMqConfig.NULL_EXCHANGE_NAME,
                                        queueName,
                                        producerType,
                                        message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
