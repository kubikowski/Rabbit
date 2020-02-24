package config;

import com.rabbitmq.client.MessageProperties;
import org.springframework.context.annotation.Configuration;
import webSocket.ConsumerParameters;
import webSocket.ProducerProperties;
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
    public static final ProducerProperties BASIC_PRODUCER =       new ProducerProperties(MessageProperties.MINIMAL_BASIC);
    public static final ProducerProperties PERSISTENT_PRODUCER =  new ProducerProperties(MessageProperties.PERSISTENT_TEXT_PLAIN);

    // Consumer Type Parameters
    public static final ConsumerParameters FIRE_AND_FORGET_CONSUMER = new ConsumerParameters(null, true);
    public static final ConsumerParameters WORKER_CONSUMER =          new ConsumerParameters(1, false);
}
