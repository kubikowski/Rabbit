package hello;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerParameters;
import webSocket.QueueProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receive {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String queueName = RabbitMqConfig.HELLO_QUEUE_NAME;
        final QueueProperties queueProperties = RabbitMqConfig.NON_DURABLE_QUEUE;
        final ConsumerParameters consumerParameters = RabbitMqConfig.FIRE_AND_FORGET_CONSUMER;

        final Channel channel = webSocketService.newQueueChannel(queueName, queueProperties);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(queueName, consumerParameters.isAutoAck(), webSocketService.defaultDeliverCallback, consumerTag -> { });
    }
}