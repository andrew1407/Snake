package javafx.event;

/** Shim: event callback. */
@FunctionalInterface
public interface EventHandler<T extends Event> {
    void handle(T event);
}
