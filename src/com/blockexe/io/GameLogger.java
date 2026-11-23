package com.blockexe.io;

import com.blockexe.config.GameConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Sends all game logs to the console and to logs/game.log.
 */
public final class GameLogger {

    private static final String FORMAT = "%1$tF %1$tT [%4$s] %3$s: %5$s%6$s%n";
    private static final Logger GAME_LOG = Logger.getLogger("com.blockexe");

    private GameLogger() {
    }

    public static void init() {
        System.setProperty("java.util.logging.SimpleFormatter.format", FORMAT);

        try {
            Files.createDirectories(GameConfig.LOGS_DIR);
            FileHandler fileHandler = new FileHandler(GameConfig.LOG_FILE.toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            GAME_LOG.addHandler(fileHandler);
        } catch (IOException e) {
            GAME_LOG.warning("Could not open log file, logging to console only");
        }

        GAME_LOG.info("Game started");
    }

    public static Logger get(Class<?> owner) {
        return Logger.getLogger(owner.getName());
    }
}
