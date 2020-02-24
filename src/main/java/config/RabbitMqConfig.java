package config;

import com.rabbitmq.client.MessageProperties;
import org.springframework.context.annotation.Configuration;
import webSocket.ConsumerType;
import webSocket.ProducerType;
import webSocket.QueueProperties;

@Configuration
public class RabbitMqConfig {

    public static final String HOST_LOCATION = "localhost";

    public static final String NULL_ROUTING_KEY = "";

    public static final String HELLO_QUEUE_NAME = "hello";
    public static final String TASK_QUEUE_NAME = "task_queue";

    public static final String NULL_EXCHANGE_NAME = "";
    public static final String LOGS_EXCHANGE_NAME = "logs";

    // Queue Type Parameters
    public static final QueueProperties NON_DURABLE_QUEUE =   new QueueProperties(false, false, false, null);
    public static final QueueProperties DURABLE_QUEUE =       new QueueProperties(true, false, false, null);

    // Producer Type Parameters
    public static final ProducerType BASIC_PRODUCER =       new ProducerType(MessageProperties.MINIMAL_BASIC);
    public static final ProducerType PERSISTENT_PRODUCER =  new ProducerType(MessageProperties.PERSISTENT_TEXT_PLAIN);

    // Consumer Type Parameters
    public static final ConsumerType FIRE_AND_FORGET_CONSUMER = new ConsumerType(null, true);
    public static final ConsumerType WORKER_CONSUMER =          new ConsumerType(1, false);
}
