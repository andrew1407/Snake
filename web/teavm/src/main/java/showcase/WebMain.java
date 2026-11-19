package showcase;

import javafx.application.Application;
import sample.Main;

/** Browser entry point: runs the game's own sample.Main through the JavaFX shim. */
public final class WebMain {
    public static void main(String[] args) {
        Application.setLauncher(Main::new); // stands in for JavaFX's reflective launch()
        Main.main(args);
    }
}
