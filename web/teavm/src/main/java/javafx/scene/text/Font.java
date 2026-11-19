package javafx.scene.text;

/** Shim: font name and size. */
public class Font {
    private final String name;
    private final double size;

    public Font(String name, double size) { this.name = name; this.size = size; }

    public String getName() { return name; }
    public double getSize() { return size; }
}
