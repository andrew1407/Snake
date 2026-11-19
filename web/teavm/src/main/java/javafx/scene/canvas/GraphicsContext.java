package javafx.scene.canvas;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import showcase.FxHost;

/** Shim: the drawing calls the game uses, forwarded to CanvasRenderingContext2D. */
public class GraphicsContext {
    GraphicsContext() {}

    public void setFill(Paint p) { FxHost.fill(p.toString()); }
    public void setFont(Font f) { FxHost.font(f.getName(), f.getSize()); }
    public void fillRect(double x, double y, double w, double h) { FxHost.fillRect(x, y, w, h); }
    public void fillOval(double x, double y, double w, double h) { FxHost.fillOval(x, y, w, h); }
    public void fillText(String text, double x, double y) { FxHost.fillText(text, x, y); }
}
