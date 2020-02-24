package publishSubscribe;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ConsumerProperties;
import webSocket.ExchangeProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogs {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.LOGS_EXCHANGE_NAME;
        final String routingKey = RabbitMqConfig.NULL_ROUTING_KEY;
        final ExchangeProperties exchangeProperties = RabbitMqConfig.FANOUT_EXCHANGE;
        final ConsumerProperties consumerProperties = RabbitMqConfig.FIRE_AND_FORGET_CONSUMER;

        final Channel channel = webSocketService.newExchangeChannel(exchangeName, exchangeProperties);

        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, routingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        channel.basicConsume(queueName, consumerProperties.isAutoAck(), webSocketService.defaultDeliverCallback, consumerTag -> { });
    }
}