package com.blockexe.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Reads and writes the best score as a single number in a text file.
 */
public class BestScoreStore {

    private static final Logger LOG = GameLogger.get(BestScoreStore.class);

    private final Path file;

    public BestScoreStore(Path file) {
        this.file = file;
    }

    public int load() {
        if (!Files.exists(file)) {
            return 0;
        }
        try {
            String text = Files.readString(file).trim();
            int score = Integer.parseInt(text);
            return Math.max(score, 0);
        } catch (IOException | NumberFormatException e) {
            LOG.warning("Could not read best score from " + file + ", starting from 0");
            return 0;
        }
    }

    public void save(int score) {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            Files.writeString(file, String.valueOf(score));
        } catch (IOException e) {
            LOG.warning("Could not save best score to " + file);
        }
    }
}
