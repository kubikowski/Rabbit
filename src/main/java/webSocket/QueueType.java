package webSocket;

public class QueueType {

    private final boolean durable;  // enqueued messages will write to disk, in case Rabbit goes down

    public QueueType(boolean durable) {
        this.durable = durable;
    }

    public boolean isDurable() {
        return durable;
    }
}
