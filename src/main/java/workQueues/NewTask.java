package workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NewTask {

    private static Random random = new Random();

    public static void main(String[] argv) throws Exception {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newChannel(RabbitMqConfig.TASK_QUEUE_NAME);

        for (int i = 0; i < 10; i++) {
            publishMessage(channel, i);
        }
    }

    private static void publishMessage(Channel channel, int messageNumber) throws IOException {
        String message = messageNumber + ".".repeat(random.nextInt(10));

        channel.basicPublish("",
                             RabbitMqConfig.TASK_QUEUE_NAME,
                             MessageProperties.PERSISTENT_TEXT_PLAIN,
                             message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}