package publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        ConsumerType consumerType = RabbitMqConfig.FIRE_AND_FORGET_CONSUMER;

        final Channel channel = webSocketService.newExchangeChannel(RabbitMqConfig.LOGS_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, RabbitMqConfig.LOGS_EXCHANGE_NAME, RabbitMqConfig.NULL_ROUTING_KEY);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(queueName, consumerType.isAutoAck(), webSocketService.defaultDeliverCallback, consumerTag -> { });
    }
}