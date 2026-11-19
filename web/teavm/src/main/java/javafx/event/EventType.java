package javafx.event;

/** Shim: event type tag. */
public final class EventType<T extends Event> {
    private final String name;

    public EventType(String name) { this.name = name; }

    public String getName() { return name; }
}
