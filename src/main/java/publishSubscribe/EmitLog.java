package publishSubscribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import config.RabbitMqConfig;
import service.WebSocketService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLog {

    public static void main(String[] argv) throws IOException, TimeoutException {
        WebSocketService webSocketService = new WebSocketService();

        final Channel channel = webSocketService.newExchangeChannel(RabbitMqConfig.LOGS_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

        webSocketService.publishMessage(channel,
                                        RabbitMqConfig.LOGS_EXCHANGE_NAME,
                                        RabbitMqConfig.NULL_ROUTING_KEY,
                                        RabbitMqConfig.BASIC_PRODUCER,
                                        message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}