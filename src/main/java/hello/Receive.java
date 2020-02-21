package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.nio.charset.StandardCharsets;

public class Receive {

    public static void main(String[] argv) throws Exception {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newChannel(RabbitMqConfig.HELLO_QUEUE_NAME, RabbitMqConfig.nonDurable);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        channel.basicConsume(RabbitMqConfig.HELLO_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}