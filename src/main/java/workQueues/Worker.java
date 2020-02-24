package workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerParameters;
import webSocket.QueueProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Worker {

    private static Random random = new Random();

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String queueName = RabbitMqConfig.TASK_QUEUE_NAME;
        final QueueProperties queueProperties = RabbitMqConfig.DURABLE_QUEUE;
        final ConsumerParameters consumerParameters = RabbitMqConfig.WORKER_CONSUMER;

        final Channel channel = webSocketService.newConsumerChannel(queueName, queueProperties, consumerParameters);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            webSocketService.defaultDeliverCallback.handle(consumerTag, delivery);
            handleWork(channel, delivery);
        };

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(queueName, consumerParameters.isAutoAck(), deliverCallback, consumerTag -> { });
    }

    private static void handleWork(Channel channel, Delivery delivery) throws IOException {
        try {
            doWork(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println(" [-] Done");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            channel.abort();
        }
    }

    private static void doWork(String task) throws Exception {
        for (char ch : task.toCharArray()) {
            if (random.nextInt(25) == 0) {
                throw new Exception(" [*] Worker Shutting Down");
            }
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}