package workQueues;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ProducerProperties;
import webSocket.QueueProperties;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class NewTask {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.NULL_EXCHANGE_NAME;
        final String queueName = RabbitMqConfig.TASK_QUEUE_NAME;
        final QueueProperties queueProperties = RabbitMqConfig.DURABLE_QUEUE;
        final ProducerProperties producerProperties = RabbitMqConfig.PERSISTENT_PRODUCER;

        final Channel channel = webSocketService.newQueueChannel(queueName, queueProperties);
        final Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String message = i + ".".repeat(random.nextInt(10));
            webSocketService.publishMessage(channel, exchangeName, queueName, producerProperties, message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}