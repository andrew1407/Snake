package javafx.application;

import java.util.function.Supplier;
import javafx.stage.Stage;

/** Shim: launch() creates the application through a launcher set by showcase.WebMain. */
public abstract class Application {
    private static Supplier<? extends Application> launcher;

    public static void setLauncher(Supplier<? extends Application> factory) { launcher = factory; }

    public static void launch(String... args) {
        try {
            launcher.get().start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void start(Stage primaryStage) throws Exception;
}
