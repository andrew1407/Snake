package javafx.scene.input;

/** Shim: the key codes the page can send (names match JavaFX). */
public enum KeyCode {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
    DIGIT0, DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9,
    UP, DOWN, LEFT, RIGHT, ESCAPE, SPACE, ENTER, TAB, BACK_SPACE, SHIFT, CONTROL, ALT,
    UNDEFINED;

    public static KeyCode byName(String name) {
        for (KeyCode k : values()) if (k.name().equals(name)) return k;
        return UNDEFINED;
    }
}
