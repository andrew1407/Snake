package javafx.stage;

import javafx.scene.Scene;
import showcase.FxHost;

/** Shim: the stage is the page's game canvas. */
public class Stage {
    private Scene scene;

    public void setTitle(String title) { FxHost.title(title); }
    public void setScene(Scene scene) { this.scene = scene; }
    public Scene getScene() { return scene; }
    public void setResizable(boolean resizable) {}
    public void show() { FxHost.show(); }
    public void close() { FxHost.close(); }
}
