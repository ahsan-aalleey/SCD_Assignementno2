package com.blockexe.fx;

import java.awt.Graphics2D;

/**
 * A short animation that runs for a fixed time and then disappears.
 * Subclasses only decide how to draw themselves at a given progress (0 to 1).
 */
public abstract class Effect {

    private final long duration;
    private long elapsed;

    protected Effect(long durationMillis) {
        this.duration = durationMillis;
    }

    public void update(long deltaMillis) {
        elapsed += deltaMillis;
    }

    public boolean isFinished() {
        return elapsed >= duration;
    }

    protected float getProgress() {
        return Math.min(1f, (float) elapsed / duration);
    }

    public abstract void draw(Graphics2D g);
}
