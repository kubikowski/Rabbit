package config;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.MessageProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String hostLocation = "localhost";

    public static final String HELLO_QUEUE_NAME = "hello";
    public static final String TASK_QUEUE_NAME = "task_queue";

    // Queue Parameters
    public static final boolean durable = true;     // enqueued messages will write to disk, in case Rabbit goes down

    // Producer Parameters
    public static final BasicProperties messageProperties = MessageProperties.PERSISTENT_TEXT_PLAIN;    // Messages will persist to Disk

    // Worker Parameters
    public static final int prefetchCount = 1;      // workers only accept only one unacked message at a time
    public static final boolean autoAck = false;    // Manual ack on process completion
}
