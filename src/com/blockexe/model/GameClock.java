package com.blockexe.model;

/**
 * Counts game time. It only moves forward when update() is called,
 * so time stops while the game is paused.
 */
public class GameClock {

    private long elapsedMillis;

    public void update(long deltaMillis) {
        if (deltaMillis > 0) {
            elapsedMillis += deltaMillis;
        }
    }

    public void reset() {
        elapsedMillis = 0;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public int getElapsedSeconds() {
        return (int) (elapsedMillis / 1000);
    }
}
