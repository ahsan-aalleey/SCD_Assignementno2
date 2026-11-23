package com.blockexe.ui;

import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.render.ScreenLayout;

import java.awt.Graphics2D;

/**
 * Drawn on top of the frozen game, so the player can still see the board.
 */
public class PauseScreen extends BaseScreen {

    private final Screen background;

    public PauseScreen(Game game, AssetManager assets, Screen background) {
        super(game, assets);
        this.background = background;
    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g);
        drawDim(g);
        drawTitle(g, "PAUSED", "Game time is stopped");
        drawButton(g, ScreenLayout.RESUME, ImageAsset.BUTTON_RESUME, "RESUME");
        drawButton(g, ScreenLayout.RESTART, ImageAsset.BUTTON_RESTART, "RESTART");
        drawButton(g, ScreenLayout.MENU, ImageAsset.BUTTON_MENU, "MAIN MENU");
    }
}
