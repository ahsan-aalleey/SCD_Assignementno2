package com.blockexe.render;

import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.model.GameMode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Draws the score, best score, time, pause button and sound status.
 */
public class HudRenderer {

    private static final Color TEXT = Color.WHITE;
    private static final Color DARK_TEXT = new Color(0x3A3A50);
    private static final Color WARNING = new Color(0xFF5A5A);
    private static final int LOW_TIME_SECONDS = 10;

    private final AssetManager assets;

    public HudRenderer(AssetManager assets) {
        this.assets = assets;
    }

    public void draw(Graphics2D g, Game game, boolean soundOn) {
        drawButton(g, ImageAsset.BUTTON_PAUSE, Layout.PAUSE_BUTTON, "PAUSE", TEXT);
        drawButton(g, ImageAsset.BUTTON_MUTE, Layout.SOUND_BUTTON, soundOn ? "SOUND ON" : "MUTED", DARK_TEXT);

        Rectangle box = Layout.SCORE_BOX;
        g.drawImage(assets.getImage(ImageAsset.SCORE_BACKGROUND), box.x, box.y, box.width, box.height, null);
        g.setColor(DARK_TEXT);
        g.setFont(assets.getFont(34f));
        TextUtil.drawCentered(g, String.valueOf(game.getScore()), box);

        g.setFont(assets.getFont(20f));
        g.setColor(TEXT);
        g.drawImage(assets.getImage(ImageAsset.ICON_BEST), 20, 94, 28, 26, null);
        g.drawString("BEST " + game.getBestScore(), 56, 115);

        String timeText;
        if (game.getMode() == GameMode.TIME_ATTACK) {
            timeText = TextUtil.formatTime(game.getRemainingSeconds()) + "  GOAL " + GameConfig.TIME_ATTACK_TARGET_SCORE;
            if (game.getRemainingSeconds() <= LOW_TIME_SECONDS) {
                g.setColor(WARNING);
            }
        } else {
            timeText = "TIME " + TextUtil.formatTime(game.getElapsedSeconds());
        }
        int textWidth = g.getFontMetrics().stringWidth(timeText);
        g.drawString(timeText, GameConfig.WINDOW_WIDTH - 20 - textWidth, 115);
    }

    private void drawButton(Graphics2D g, ImageAsset image, Rectangle area, String label, Color textColor) {
        g.drawImage(assets.getImage(image), area.x, area.y, area.width, area.height, null);
        g.setColor(textColor);
        g.setFont(assets.getFont(18f));
        TextUtil.drawCentered(g, label, area);
    }
}
