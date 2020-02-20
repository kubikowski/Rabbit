package workQueues;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import dto.WebSocketMessage;
import service.WebSocketService;

import java.io.IOException;
import java.util.Random;

public class NewTask {

    public static void main(String[] argv) throws Exception {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newChannel(RabbitMqConfig.TASK_QUEUE_NAME);
        final Random random = new Random();

        for (int i = 0; i < 10; i++) {
            publishMessage(channel, i, random.nextInt(10));
        }
    }

    private static void publishMessage(Channel channel, int messageNumber, int secondsOfWork) throws IOException {
        WebSocketService webSocketService = new WebSocketService();

        String message = messageNumber + ".".repeat(secondsOfWork);

        webSocketService.publishMessage(channel, WebSocketMessage.from(RabbitMqConfig.TASK_QUEUE_NAME, message));
        System.out.println(" [x] Sent '" + message + "'");
    }
}