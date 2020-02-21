package webSocket;

import java.util.Map;

public class QueueType {

    private final boolean durable;  // enqueued messages will write to disk, in case Rabbit goes down
    private final boolean exclusive;
    private final boolean autoDelete;
    private final Map<String, Object> arguments;

    public QueueType(boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.arguments = arguments;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }
}
