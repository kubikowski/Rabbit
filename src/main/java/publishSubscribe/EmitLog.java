package publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;
import webSocket.ProducerType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLog {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final String exchangeName = RabbitMqConfig.LOGS_EXCHANGE_NAME;
        final String routingKey = RabbitMqConfig.NULL_ROUTING_KEY;
        final ProducerType producerType = RabbitMqConfig.BASIC_PRODUCER;

        final Channel channel = webSocketService.newExchangeChannel(exchangeName, BuiltinExchangeType.FANOUT);

        String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

        webSocketService.publishMessage(channel, exchangeName, routingKey, producerType, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}