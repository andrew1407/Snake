package javafx.scene.paint;

/** Shim: named colours; JavaFX's named colours equal the CSS ones. toString() gives the CSS colour. */
public final class Color extends Paint {
    public static final Color BLACK = new Color("black");
    public static final Color WHITE = new Color("white");
    public static final Color RED = new Color("red");
    public static final Color GREEN = new Color("green");
    public static final Color DARKGREEN = new Color("darkgreen");
    public static final Color LIGHTGREEN = new Color("lightgreen");
    public static final Color OLIVE = new Color("olive");
    public static final Color AQUA = new Color("aqua");
    public static final Color PURPLE = new Color("purple");
    public static final Color ORANGE = new Color("orange");
    public static final Color YELLOW = new Color("yellow");
    public static final Color CORAL = new Color("coral");

    private final String css;

    private Color(String css) { this.css = css; }

    public static Color web(String css) { return new Color(css); }

    @Override
    public String toString() { return css; }
}
