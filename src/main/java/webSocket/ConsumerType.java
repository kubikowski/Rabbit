package webSocket;

public class ConsumerType {

    private final Integer prefetchCount;    // Consumer can accept this many unacked message at a time
    private final boolean autoAck;          // Manual ack (on process completion) or Immediate ack on message receipt.

    public ConsumerType(Integer prefetchCount, boolean autoAck) {
        this.prefetchCount = prefetchCount;
        this.autoAck = autoAck;
    }

    public Integer getPrefetchCount() {
        return prefetchCount;
    }

    public boolean isAutoAck() {
        return autoAck;
    }
}
