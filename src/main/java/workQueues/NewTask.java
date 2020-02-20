package workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import config.RabbitMqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NewTask {

    private static Random random = new Random();

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(RabbitMqConfig.TASK_QUEUE_NAME, RabbitMqConfig.durable, false, false, null);

            for (int i = 0; i < 10; i++) {
                publishMessage(channel, i);
            }
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