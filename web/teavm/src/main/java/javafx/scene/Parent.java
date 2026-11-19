package javafx.scene;

import javafx.collections.ObservableList;

/** Shim: a node with children. */
public abstract class Parent extends Node {
    private final ObservableList<Node> children = new ObservableList<>();

    protected ObservableList<Node> getChildren() { return children; }
}
