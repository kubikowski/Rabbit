package workQueues;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newChannel(RabbitMqConfig.TASK_QUEUE_NAME, RabbitMqConfig.durable);
        final Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String message = i + ".".repeat(random.nextInt(10));

            webSocketService.publishMessage(channel, RabbitMqConfig.TASK_QUEUE_NAME, RabbitMqConfig.persistent, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}