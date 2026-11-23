package com.blockexe.ui;

import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.render.TextUtil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Shared drawing helpers for the menu, instructions, pause and game over screens.
 */
abstract class BaseScreen implements Screen {

    protected static final Color BACKGROUND = new Color(0x1E2A4A);
    protected static final Color PANEL = new Color(0x141D36);
    protected static final Color TEXT = Color.WHITE;
    protected static final Color DARK_TEXT = new Color(0x3A3A50);
    protected static final Color HIGHLIGHT = new Color(0xFFD23F);
    protected static final Color SOFT_TEXT = new Color(0xA8B3D1);

    protected final Game game;
    protected final AssetManager assets;

    BaseScreen(Game game, AssetManager assets) {
        this.game = game;
        this.assets = assets;
    }

    protected void drawBackground(Graphics2D g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    /** Darkens whatever is already drawn, used by the pause and game over overlays. */
    protected void drawDim(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
    }

    protected void drawTitle(Graphics2D g, String title, String subtitle) {
        g.setColor(TEXT);
        g.setFont(assets.getFont(42f));
        TextUtil.drawCentered(g, title, new Rectangle(30, 55, 480, 60));
        g.setFont(assets.getFont(18f));
        g.setColor(SOFT_TEXT);
        TextUtil.drawCentered(g, subtitle, new Rectangle(30, 120, 480, 35));
    }

    protected void drawButton(Graphics2D g, Rectangle area, ImageAsset image, String label) {
        g.drawImage(assets.getImage(image), area.x, area.y, area.width, area.height, null);
        g.setColor(isLightButton(image) ? DARK_TEXT : TEXT);
        g.setFont(assets.getFont(20f));
        TextUtil.drawCentered(g, label, area);
    }

    protected void drawPanel(Graphics2D g, Rectangle area) {
        g.setColor(PANEL);
        g.fillRoundRect(area.x, area.y, area.width, area.height, 20, 20);
    }

    /** White and yellow buttons need dark text to be readable. */
    private boolean isLightButton(ImageAsset image) {
        return image == ImageAsset.BUTTON_BACK || image == ImageAsset.BUTTON_MENU
                || image == ImageAsset.BUTTON_MUTE || image == ImageAsset.BUTTON_TIME_ATTACK;
    }
}
