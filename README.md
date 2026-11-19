# Snake

<p align="center">
  <a href="https://andrew1407.github.io/Snake/" title="Play the game in the browser">
    <img src="web/icon.svg" alt="Snake" width="140" height="140">
  </a>
</p>

[![pages deploy badge](https://github.com/andrew1407/Snake/actions/workflows/pages.yml/badge.svg)](https://andrew1407.github.io/Snake/)

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

## Showcase

**[Play it in the browser](https://andrew1407.github.io/Snake/)**, with the keyboard, swipes or an on-screen pad. It keeps a best score, pauses when the tab is hidden, and copies the board as a PNG image.

- The game's own Java sources in [`src/sample`](src/sample) are compiled to JavaScript with [TeaVM](https://teavm.org) by the Gradle build in [`web/teavm`](web/teavm). Nothing is ported.
- A minimal JavaFX shim in [`web/teavm/src/main/java`](web/teavm/src/main/java) stands in for the few JavaFX classes the game uses (`Application`, `Stage`, `Scene`, `Canvas`, `GraphicsContext`, `AnimationTimer`, `KeyEvent`, `Color`, `Font`), and draws on a `<canvas>` through [`web/fx.js`](web/fx.js).

Run it locally (needs JDK 17+):

```sh
cd web/teavm && ./gradlew showcase && cd ../..   # builds web/snake-js/snake.js
python -m http.server                            # then open http://localhost:8000/web/
```

The [pages workflow](.github/workflows/pages.yml) builds and deploys it on every push to `master`.

## Run

Requires a JDK with JavaFX (Java 11+, modules `javafx.controls` and `javafx.fxml`).

```bash
javac -d out --module-path <javafx>/lib --add-modules javafx.controls,javafx.fxml \
  src/module-info.java src/sample/*.java
java --module-path "<javafx>/lib:out" --add-modules javafx.controls,javafx.fxml -m Snake/sample.Main
```

Or open the project in IntelliJ IDEA (an [`.idea`](.idea) module is included) and run [`sample.Main`](src/sample/Main.java).

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
web/                   # GitHub Pages showcase: the page, the JavaFX shim and the TeaVM build
```

The [`src`](src) tree holds [`module-info.java`](src/module-info.java) (the `Snake` module declaration) and the [`sample`](src/sample) package: [`Main.java`](src/sample/Main.java) (entry point + game loop), [`Controller.java`](src/sample/Controller.java) (keyboard input), [`SnakeBody.java`](src/sample/SnakeBody.java) (snake state), [`Pill.java`](src/sample/Pill.java) (pill spawning/scoring), [`FrameView.java`](src/sample/FrameView.java) (rendering), and [`sample.fxml`](src/sample/sample.fxml) (the FXML layout).

## License

[MIT](LICENSE) © Andrew1407
