// Compiles the game's own Java sources (src/sample/*.java, unmodified) to JavaScript with TeaVM
// for the GitHub Pages showcase. src/main/java holds a minimal shim of the few JavaFX classes the
// game uses (Application, Stage, Scene, Canvas, GraphicsContext, AnimationTimer, KeyEvent, ...),
// drawing onto the page's <canvas> through web/fx.js, plus the showcase.WebMain entry point.
import org.teavm.gradle.api.JSModuleType

plugins {
    java
    id("org.teavm") version "0.16.0"
}

tasks.withType<JavaCompile> { options.release.set(17) }

// The game's sources, without module-info.java (it requires the real JavaFX modules)
val gameSources = tasks.register<Sync>("gameSources") {
    from("../../src") { include("sample/*.java") }
    into(layout.buildDirectory.dir("gameSources"))
}

sourceSets {
    main {
        java.srcDir(gameSources)
    }
}

dependencies {
    implementation(teavm.libs.jso)
}

teavm.js {
    mainClass.set("showcase.WebMain")
    moduleType.set(JSModuleType.ES2015)
    targetFileName.set("snake.js")
    obfuscated.set(true)
    addedToWebApp.set(false)
}

// ./gradlew showcase  ->  web/snake-js/snake.js
tasks.register<Sync>("showcase") {
    dependsOn("generateJavaScript")
    from(layout.buildDirectory.dir("generated/teavm/js")) { include("snake.js") }
    into("../snake-js")
}
