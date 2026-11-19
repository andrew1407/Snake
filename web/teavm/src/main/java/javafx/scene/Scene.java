package javafx.scene;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import showcase.FxHost;

/** Shim: key presses come from the page (keyboard, swipes and the on-screen pad). */
public class Scene {
    private final Parent root;

    public Scene(Parent root, double width, double height) { this.root = root; }

    public Parent getRoot() { return root; }

    @SuppressWarnings("unchecked")
    public <T extends Event> void addEventFilter(EventType<T> type, EventHandler<? super T> handler) {
        if (type != KeyEvent.KEY_PRESSED) return;
        final EventHandler<? super KeyEvent> onKey = (EventHandler<? super KeyEvent>) handler;
        FxHost.onKey(code -> onKey.handle(new KeyEvent(KeyCode.byName(code))));
    }
}
