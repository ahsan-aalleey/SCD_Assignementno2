package com.blockexe.core;

import java.util.ArrayList;
import java.util.List;

/**
 * What happened after trying to place a piece. The UI uses this to play
 * sounds and start animations.
 */
public class PlaceResult {

    private final boolean placed;
    private final List<Integer> clearedRows;
    private final List<Integer> clearedCols;
    private final int pointsGained;

    public PlaceResult(boolean placed, List<Integer> clearedRows, List<Integer> clearedCols, int pointsGained) {
        this.placed = placed;
        this.clearedRows = new ArrayList<>(clearedRows);
        this.clearedCols = new ArrayList<>(clearedCols);
        this.pointsGained = pointsGained;
    }

    public static PlaceResult invalid() {
        return new PlaceResult(false, new ArrayList<>(), new ArrayList<>(), 0);
    }

    public boolean isPlaced() {
        return placed;
    }

    public List<Integer> getClearedRows() {
        return new ArrayList<>(clearedRows);
    }

    public List<Integer> getClearedCols() {
        return new ArrayList<>(clearedCols);
    }

    public int getLinesCleared() {
        return clearedRows.size() + clearedCols.size();
    }

    public int getPointsGained() {
        return pointsGained;
    }
}
