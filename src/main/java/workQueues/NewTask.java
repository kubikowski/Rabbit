package workQueues;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ProducerType;
import webSocket.QueueType;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String queueName = RabbitMqConfig.TASK_QUEUE_NAME;
        final QueueType queueType = RabbitMqConfig.DURABLE_QUEUE;
        final ProducerType producerType = RabbitMqConfig.PERSISTENT_PRODUCER;
        final Channel channel = webSocketService.newQueueChannel(queueName, queueType);
        final Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String message = i + ".".repeat(random.nextInt(10));

            webSocketService.publishMessage(channel,
                                            RabbitMqConfig.NULL_EXCHANGE_NAME,
                                            queueName,
                                            producerType,
                                            message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}