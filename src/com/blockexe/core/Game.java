package com.blockexe.core;

import com.blockexe.config.GameConfig;
import com.blockexe.io.BestScoreStore;
import com.blockexe.io.GameLogger;
import com.blockexe.model.BlockPiece;
import com.blockexe.model.Board;
import com.blockexe.model.GameClock;
import com.blockexe.model.GameMode;
import com.blockexe.model.GameState;
import com.blockexe.model.PieceGenerator;
import com.blockexe.model.Tray;

import java.util.List;
import java.util.logging.Logger;

/**
 * The game rules. Holds the board, tray, score, timer, mode and state,
 * and decides what happens when the player places a piece.
 */
public class Game {

    private static final Logger LOG = GameLogger.get(Game.class);

    private final Board board = new Board();
    private final Tray tray = new Tray();
    private final PieceGenerator generator = new PieceGenerator();
    private final GameClock clock = new GameClock();
    private final ScoreManager scoreManager;

    private GameMode mode = GameMode.CLASSIC;
    private GameState state = GameState.MENU;
    private int selectedSlot = -1;
    private boolean won;
    private boolean newBest;
    private int gamesStarted;

    public Game() {
        this.scoreManager = new ScoreManager(new BestScoreStore(GameConfig.BEST_SCORE_FILE));
    }

    public void startNewGame(GameMode newMode) {
        mode = newMode;
        board.reset();
        tray.refill(generator);
        scoreManager.reset();
        clock.reset();
        selectedSlot = -1;
        won = false;
        newBest = false;
        gamesStarted++;
        state = GameState.PLAYING;
        LOG.info("New game started in " + mode + " mode");
    }

    public void restart() {
        startNewGame(mode);
    }

    /** Called every frame with the time since the last frame. */
    public void update(long deltaMillis) {
        if (state != GameState.PLAYING) {
            return;
        }
        clock.update(deltaMillis);
        if (mode == GameMode.TIME_ATTACK && getRemainingSeconds() <= 0) {
            endGame(false);
        }
    }

    public boolean selectPiece(int slot) {
        if (state != GameState.PLAYING || tray.getPiece(slot) == null) {
            return false;
        }
        selectedSlot = slot;
        return true;
    }

    public void clearSelection() {
        selectedSlot = -1;
    }

    /**
     * Tries to place the piece from the given tray slot with its top-left cell at (row, col).
     * An invalid placement changes nothing and the piece stays in the tray.
     */
    public PlaceResult tryPlace(int slot, int row, int col) {
        BlockPiece piece = tray.getPiece(slot);
        if (state != GameState.PLAYING || piece == null || !board.place(piece, row, col)) {
            return PlaceResult.invalid();
        }

        tray.removePiece(slot);
        selectedSlot = -1;
        int points = scoreManager.addPlacement(piece.getCellCount());

        List<Integer> rows = board.findFullRows();
        List<Integer> cols = board.findFullColumns();
        int lines = rows.size() + cols.size();
        if (lines > 0) {
            board.clearLines(rows, cols);
            points += scoreManager.addLineClear(lines);
        }

        if (tray.isEmpty()) {
            tray.refill(generator);
        }
        checkEndConditions();

        return new PlaceResult(true, rows, cols, points);
    }

    public void togglePause() {
        if (state == GameState.PLAYING) {
            state = GameState.PAUSED;
        } else if (state == GameState.PAUSED) {
            state = GameState.PLAYING;
        }
    }

    public void openMenu() {
        state = GameState.MENU;
    }

    public void openModeSelect() {
        state = GameState.MODE_SELECT;
    }

    public void openInstructions() {
        state = GameState.INSTRUCTIONS;
    }

    private void checkEndConditions() {
        if (mode == GameMode.TIME_ATTACK && scoreManager.getScore() >= GameConfig.TIME_ATTACK_TARGET_SCORE) {
            endGame(true);
        } else if (!anyPieceFits()) {
            endGame(false);
        }
    }

    private boolean anyPieceFits() {
        for (int i = 0; i < tray.getSlotCount(); i++) {
            BlockPiece piece = tray.getPiece(i);
            if (piece != null && board.canFitAnywhere(piece)) {
                return true;
            }
        }
        return false;
    }

    private void endGame(boolean playerWon) {
        won = playerWon;
        state = GameState.GAME_OVER;
        selectedSlot = -1;
        newBest = scoreManager.saveIfBest();
        LOG.info("Game over. Score: " + scoreManager.getScore() + (newBest ? " (new best)" : ""));
    }

    public Board getBoard() {
        return board;
    }

    public Tray getTray() {
        return tray;
    }

    public GameMode getMode() {
        return mode;
    }

    public GameState getState() {
        return state;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isNewBest() {
        return newBest;
    }

    /** Goes up by one every time a game starts, so the UI can tell a restart happened. */
    public int getGamesStarted() {
        return gamesStarted;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public int getBestScore() {
        return scoreManager.getBestScore();
    }

    public int getElapsedSeconds() {
        return clock.getElapsedSeconds();
    }

    public int getRemainingSeconds() {
        long remaining = GameConfig.TIME_ATTACK_SECONDS * 1000L - clock.getElapsedMillis();
        return (int) Math.max(0, (remaining + 999) / 1000);
    }
}
