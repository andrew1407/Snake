package showcase;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;

/** Calls from the JavaFX shim into the page (window.fxHost, defined in web/fx.js). */
public final class FxHost {
    private FxHost() {}

    @JSFunctor
    public interface Frame extends JSObject { void run(double nowMs); }

    @JSFunctor
    public interface Key extends JSObject { void press(String code); }

    @JSBody(params = "title", script = "fxHost.title(title);")
    public static native void title(String title);

    @JSBody(params = { "w", "h" }, script = "fxHost.canvas(w, h);")
    public static native void canvas(double w, double h);

    @JSBody(script = "fxHost.show();")
    public static native void show();

    @JSBody(script = "fxHost.close();")
    public static native void close();

    @JSBody(params = "frame", script = "return fxHost.timer(frame);")
    public static native int timer(Frame frame);

    @JSBody(params = "id", script = "fxHost.stopTimer(id);")
    public static native void stopTimer(int id);

    @JSBody(params = "key", script = "fxHost.onKey(key);")
    public static native void onKey(Key key);

    @JSBody(params = "css", script = "fxHost.gc.fill(css);")
    public static native void fill(String css);

    @JSBody(params = { "name", "size" }, script = "fxHost.gc.font(name, size);")
    public static native void font(String name, double size);

    @JSBody(params = { "x", "y", "w", "h" }, script = "fxHost.gc.rect(x, y, w, h);")
    public static native void fillRect(double x, double y, double w, double h);

    @JSBody(params = { "x", "y", "w", "h" }, script = "fxHost.gc.oval(x, y, w, h);")
    public static native void fillOval(double x, double y, double w, double h);

    @JSBody(params = { "text", "x", "y" }, script = "fxHost.gc.text(text, x, y);")
    public static native void fillText(String text, double x, double y);
}
