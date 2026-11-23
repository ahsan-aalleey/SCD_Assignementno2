package com.blockexe.core;

import com.blockexe.config.GameConfig;
import com.blockexe.io.BestScoreStore;

/**
 * Keeps the current score and the best score.
 */
public class ScoreManager {

    private final BestScoreStore store;
    private int score;
    private int bestScore;

    public ScoreManager(BestScoreStore store) {
        this.store = store;
        this.bestScore = store.load();
    }

    public int addPlacement(int cellCount) {
        int points = cellCount * GameConfig.POINTS_PER_CELL;
        score += points;
        return points;
    }

    /** Clearing more lines at once gives a combo bonus: 1 line = 10, 2 lines = 40, 3 lines = 90. */
    public int addLineClear(int lineCount) {
        int points = lineCount * lineCount * GameConfig.POINTS_PER_LINE;
        score += points;
        return points;
    }

    /** Saves the score if it beats the best score. Returns true if it was a new best. */
    public boolean saveIfBest() {
        if (score > bestScore) {
            bestScore = score;
            store.save(bestScore);
            return true;
        }
        return false;
    }

    public void reset() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return Math.max(bestScore, score);
    }
}
