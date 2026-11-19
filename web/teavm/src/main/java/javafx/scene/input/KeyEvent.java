package javafx.scene.input;

import javafx.event.Event;
import javafx.event.EventType;

/** Shim: a key press. */
public class KeyEvent extends Event {
    public static final EventType<KeyEvent> KEY_PRESSED = new EventType<>("KEY_PRESSED");

    private final KeyCode code;

    public KeyEvent(KeyCode code) { this.code = code; }

    public KeyCode getCode() { return code; }
}
