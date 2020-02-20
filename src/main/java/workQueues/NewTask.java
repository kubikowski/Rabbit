package workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import config.RabbitMqConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewTask {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(RabbitMqConfig.TASK_QUEUE_NAME, true, false, false, null);

            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                publishMessage(channel, random.nextInt(10));
            }
        }
    }

    private static void publishMessage(Channel channel, Integer secondsOfWork) throws IOException {
        String message = Stream.generate(String::new)
                               .limit(secondsOfWork + 1)
                               .collect(Collectors.joining("."));
        channel.basicPublish("",
                             RabbitMqConfig.TASK_QUEUE_NAME,
                             MessageProperties.PERSISTENT_TEXT_PLAIN,
                             message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}