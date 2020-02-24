package publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerParameters;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.LOGS_EXCHANGE_NAME;
        final String routingKey = RabbitMqConfig.NULL_ROUTING_KEY;
        final ConsumerParameters consumerParameters = RabbitMqConfig.FIRE_AND_FORGET_CONSUMER;

        final Channel channel = webSocketService.newExchangeChannel(exchangeName, BuiltinExchangeType.FANOUT);

        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(queueName, consumerParameters.isAutoAck(), webSocketService.defaultDeliverCallback, consumerTag -> { });
    }
}