package com.blockexe.app;

import com.blockexe.audio.SoundManager;
import com.blockexe.io.AssetManager;
import com.blockexe.io.GameLogger;

import javax.swing.SwingUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = GameLogger.get(Main.class);

    public static void main(String[] args) {
        GameLogger.init();

        // Log unexpected errors instead of crashing silently
        Thread.setDefaultUncaughtExceptionHandler((thread, e) ->
                LOG.log(Level.SEVERE, "Unexpected error", e));

        SwingUtilities.invokeLater(() -> {
            AssetManager assets = new AssetManager();
            assets.loadAll();
            SoundManager sounds = new SoundManager();
            sounds.loadAll();
            new GameFrame(assets, sounds).setVisible(true);
        });
    }
}
