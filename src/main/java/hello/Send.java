package hello;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newQueueChannel(RabbitMqConfig.HELLO_QUEUE_NAME, RabbitMqConfig.NON_DURABLE_QUEUE);

        String message = "Hello Java!";
        webSocketService.publishMessage(channel,
                                        RabbitMqConfig.NULL_EXCHANGE_NAME,
                                        RabbitMqConfig.HELLO_QUEUE_NAME,
                                        RabbitMqConfig.BASIC_PRODUCER,
                                        message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
