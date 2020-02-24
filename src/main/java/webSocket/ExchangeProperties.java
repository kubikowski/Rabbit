package webSocket;

import com.rabbitmq.client.BuiltinExchangeType;

public class ExchangeProperties {

    private final BuiltinExchangeType exchangeType;

    public ExchangeProperties(BuiltinExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public BuiltinExchangeType getExchangeType() {
        return exchangeType;
    }
}
