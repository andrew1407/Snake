# Snake

A classic Snake game built with **Java** and **JavaFX**. Steer the snake around a 30×20 grid, eat pills to grow, and avoid running into yourself — every pill speeds the game up, so your score is the pace you manage to survive.

## Gameplay

- The snake advances one cell per tick on a fixed grid (30 × 20, 25 px cells).
- Eating a pill grows the snake and raises the frame rate (speed); the current FPS doubles as the score.
- The game ends when the snake runs into its own body. Press `Esc` to quit.

## Controls

| Key | Action |
|---|---|
| `W` / `↑` | Move up |
| `S` / `↓` | Move down |
| `A` / `←` | Move left |
| `D` / `→` | Move right |
| `Esc` | Quit |

## Run

Requires a JDK with JavaFX (Java 11+, modules `javafx.controls` and `javafx.fxml`).

```bash
javac -d out --module-path <javafx>/lib --add-modules javafx.controls,javafx.fxml \
  src/module-info.java src/sample/*.java
java --module-path "<javafx>/lib:out" --add-modules javafx.controls,javafx.fxml -m Snake/sample.Main
```

Or open the project in IntelliJ IDEA (an `.idea` module is included) and run `sample.Main`.

## Project structure

```
src/
  module-info.java     # module Snake — requires javafx.controls, javafx.fxml
  sample/
    Main.java          # Application entry point + AnimationTimer game loop
    Controller.java    # keyboard input -> snake direction
    SnakeBody.java     # snake state: movement, growth, self-collision
    Pill.java          # pill spawning, eating, speed/score
    FrameView.java     # JavaFX window/scene rendering
    sample.fxml        # FXML layout
```

## License

[MIT](LICENSE) © Andrew1407
