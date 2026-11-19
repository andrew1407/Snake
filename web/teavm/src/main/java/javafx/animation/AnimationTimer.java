package javafx.animation;

import showcase.FxHost;

/** Shim: handle(now) runs on every requestAnimationFrame, with now in nanoseconds. */
public abstract class AnimationTimer {
    private int id = -1;

    public abstract void handle(long now);

    public void start() {
        if (id < 0) id = FxHost.timer(ms -> handle((long) (ms * 1_000_000)));
    }

    public void stop() {
        if (id >= 0) FxHost.stopTimer(id);
        id = -1;
    }
}
