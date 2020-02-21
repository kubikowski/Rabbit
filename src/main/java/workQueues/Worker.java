package workQueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerType;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Worker {

    private static Random random = new Random();

    public static void main(String[] argv) throws Exception {
        WebSocketService webSocketService = new WebSocketService();

        ConsumerType consumerType = RabbitMqConfig.worker;

        final Channel channel = webSocketService.newConsumerChannel(RabbitMqConfig.TASK_QUEUE_NAME, RabbitMqConfig.durable, consumerType);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [+] Received '" + message + "'");
            try {
                doWork(message);
                System.out.println(" [-] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                channel.abort();
            }
        };
        channel.basicConsume(RabbitMqConfig.TASK_QUEUE_NAME, consumerType.isAutoAck(), deliverCallback, consumerTag -> { });
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