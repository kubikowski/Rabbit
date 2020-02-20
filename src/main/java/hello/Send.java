package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import config.RabbitMqConfig;
import java.nio.charset.StandardCharsets;

public class Send {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(RabbitMqConfig.HELLO_QUEUE_NAME, false, false, false, null);

            String message = "Hello Java!";
            channel.basicPublish("", RabbitMqConfig.HELLO_QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
