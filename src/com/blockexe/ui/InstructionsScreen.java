package com.blockexe.ui;

import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.render.ScreenLayout;
import com.blockexe.render.TextUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class InstructionsScreen extends BaseScreen {

    private static final int TEXT_X = 70;

    public InstructionsScreen(Game game, AssetManager assets) {
        super(game, assets);
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g, "HOW TO PLAY", "Fill lines, clear space, score points");
        drawPanel(g, new Rectangle(45, 170, 450, 405));

        int y = 205;
        y = drawSection(g, y, "GOAL",
                "Place pieces on the 8x8 board.",
                "Fill a row or column to clear it.",
                "Clear several lines at once for a bonus.");
        y = drawSection(g, y, "MODES",
                "Classic: play until no piece fits.",
                "Time Attack: " + GameConfig.TIME_ATTACK_TARGET_SCORE + " points in "
                        + TextUtil.formatTime(GameConfig.TIME_ATTACK_SECONDS) + ".");
        y = drawSection(g, y, "MOUSE",
                "Drag a piece onto the board and release.",
                "Green preview is valid, red is blocked.");
        drawSection(g, y, "KEYBOARD",
                "1 2 3 select    Arrows move",
                "Enter or Space place    Esc cancel",
                "P pause    R restart    M mute");

        drawButton(g, ScreenLayout.BACK, ImageAsset.BUTTON_BACK, "BACK");
    }

    /** Draws a yellow heading and its lines. Returns the y position for the next section. */
    private int drawSection(Graphics2D g, int y, String heading, String... lines) {
        g.setColor(HIGHLIGHT);
        g.setFont(assets.getFont(17f));
        g.drawString(heading, TEXT_X, y);
        y += 26;

        g.setColor(TEXT);
        g.setFont(assets.getFont(13f));
        for (String line : lines) {
            g.drawString(line, TEXT_X, y);
            y += 22;
        }
        return y + 14;
    }
}
