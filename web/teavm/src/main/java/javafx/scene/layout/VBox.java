package javafx.scene.layout;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/** Shim: vertical box; the page lays out its only child, the canvas. */
public class VBox extends Parent {
    @Override
    public ObservableList<Node> getChildren() { return super.getChildren(); }
}
