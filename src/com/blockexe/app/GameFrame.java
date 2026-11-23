package com.blockexe.app;

import com.blockexe.audio.SoundManager;
import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.ui.GamePanel;

import javax.swing.JFrame;

/**
 * The main window. Holds the game panel.
 */
public class GameFrame extends JFrame {

    public GameFrame(AssetManager assets, SoundManager sounds) {
        super(GameConfig.GAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(assets.getImage(ImageAsset.BLOCK_BLUE));

        Game game = new Game();
        GamePanel panel = new GamePanel(game, assets, sounds);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);

        panel.start();
    }
}
