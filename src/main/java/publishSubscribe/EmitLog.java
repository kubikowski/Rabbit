package publishSubscribe;

import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ExchangeProperties;
import webSocket.ProducerProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLog {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.LOGS_EXCHANGE_NAME;
        final String routingKey = RabbitMqConfig.DEFAULT_ROUTING_KEY;
        final ExchangeProperties exchangeProperties = RabbitMqConfig.FANOUT_EXCHANGE;
        final ProducerProperties producerProperties = RabbitMqConfig.BASIC_PRODUCER;

        final Channel channel = webSocketService.newExchangeChannel(exchangeName, exchangeProperties);

        String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

        webSocketService.publishMessage(channel, exchangeName, routingKey, producerProperties, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}