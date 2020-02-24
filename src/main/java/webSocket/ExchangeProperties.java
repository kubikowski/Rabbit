package webSocket;

import com.rabbitmq.client.BuiltinExchangeType;

import java.util.Map;

public class ExchangeProperties {

    private final BuiltinExchangeType exchangeType;
    private final boolean durable;  // enqueued messages will write to disk, in case Rabbit goes down
    private final boolean autoDelete;
    private final boolean internal;
    private final Map<String, Object> arguments;

    public ExchangeProperties(BuiltinExchangeType exchangeType, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments) {
        this.exchangeType = exchangeType;
        this.durable = durable;
        this.autoDelete = autoDelete;
        this.internal = internal;
        this.arguments = arguments;
    }

    public BuiltinExchangeType getExchangeType() {
        return exchangeType;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public boolean isInternal() {
        return internal;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }
}
