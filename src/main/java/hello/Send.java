package hello;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newChannel(RabbitMqConfig.HELLO_QUEUE_NAME, RabbitMqConfig.nonDurable);

        String message = "Hello Java!";
        webSocketService.publishMessage(channel, RabbitMqConfig.HELLO_QUEUE_NAME, RabbitMqConfig.basic, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
