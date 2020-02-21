package config;

import com.rabbitmq.client.MessageProperties;
import org.springframework.context.annotation.Configuration;
import webSocket.ConsumerType;
import webSocket.ProducerType;
import webSocket.QueueType;

@Configuration
public class RabbitMqConfig {

    public static final String HOST_LOCATION = "localhost";

    public static final String HELLO_QUEUE_NAME = "hello";
    public static final String TASK_QUEUE_NAME = "task_queue";

    // Queue Type Parameters
    public static final QueueType NON_DURABLE_QUEUE = new QueueType(false, false, false, null);
    public static final QueueType DURABLE_QUEUE = new QueueType(true, false, false, null);

    // Producer Type Parameters
    public static final ProducerType BASIC_PRODUCER = new ProducerType(MessageProperties.MINIMAL_BASIC);
    public static final ProducerType PERSISTENT_PRODUCER = new ProducerType(MessageProperties.PERSISTENT_TEXT_PLAIN);

    // Consumer Type Parameters
    public static final ConsumerType FIRE_AND_FORGET_CONSUMER = new ConsumerType(null, true);
    public static final ConsumerType WORKER_CONSUMER = new ConsumerType(1, false);
}
