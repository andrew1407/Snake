package javafx.scene.canvas;

import javafx.scene.Node;
import showcase.FxHost;

/** Shim: the page's game canvas. */
public class Canvas extends Node {
    private final GraphicsContext gc = new GraphicsContext();

    public Canvas(double width, double height) { FxHost.canvas(width, height); }

    public GraphicsContext getGraphicsContext2D() { return gc; }
}
