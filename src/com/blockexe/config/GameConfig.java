package com.blockexe.config;

import java.nio.file.Path;

/**
 * Central place for tunable constants. Nothing here changes at runtime.
 */
public final class GameConfig {

    private GameConfig() {
    }

    // Window
    public static final String GAME_TITLE = "Block.exe";
    public static final int WINDOW_WIDTH = 540;
    public static final int WINDOW_HEIGHT = 820;

    // Board and tray
    public static final int BOARD_SIZE = 8;
    public static final int CELL_SIZE = 56;
    public static final int TRAY_SLOTS = 3;

    // Game loop
    public static final int FPS = 60;
    public static final int FRAME_DELAY_MS = 1000 / FPS;

    // Scoring
    public static final int POINTS_PER_CELL = 1;
    public static final int POINTS_PER_LINE = 10;

    // Time Attack
    public static final int TIME_ATTACK_SECONDS = 180;
    public static final int TIME_ATTACK_TARGET_SCORE = 500;

    // File locations, relative to the working directory (project root)
    public static final Path ASSETS_DIR = Path.of("assets");
    public static final Path UI_DIR = ASSETS_DIR.resolve("ui");
    public static final Path SOUND_DIR = ASSETS_DIR.resolve("sound");
    public static final Path DATA_DIR = Path.of("data");
    public static final Path LOGS_DIR = Path.of("logs");
    public static final Path BEST_SCORE_FILE = DATA_DIR.resolve("best_score.txt");
    public static final Path LOG_FILE = LOGS_DIR.resolve("game.log");
}
