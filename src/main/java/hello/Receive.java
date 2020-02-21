package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Receive {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        ConsumerType consumerType = RabbitMqConfig.FIRE_AND_FORGET_CONSUMER;

        final Channel channel = webSocketService.newQueueChannel(RabbitMqConfig.HELLO_QUEUE_NAME, RabbitMqConfig.NON_DURABLE_QUEUE);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(RabbitMqConfig.HELLO_QUEUE_NAME, consumerType.isAutoAck(), webSocketService.defaultDeliverCallback, consumerTag -> { });
    }
}