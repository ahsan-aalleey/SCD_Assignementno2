package com.blockexe.ui;

import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.render.ScreenLayout;
import com.blockexe.render.TextUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ModeSelectScreen extends BaseScreen {

    public ModeSelectScreen(Game game, AssetManager assets) {
        super(game, assets);
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g, "CHOOSE MODE", "Pick your challenge");

        drawButton(g, ScreenLayout.CLASSIC, ImageAsset.BUTTON_CLASSIC, "CLASSIC");
        drawDescription(g, "PLAY UNTIL NO PIECE FITS", ScreenLayout.CLASSIC);

        drawButton(g, ScreenLayout.TIME_ATTACK, ImageAsset.BUTTON_TIME_ATTACK, "TIME ATTACK");
        String goal = "SCORE " + GameConfig.TIME_ATTACK_TARGET_SCORE + " IN "
                + TextUtil.formatTime(GameConfig.TIME_ATTACK_SECONDS);
        drawDescription(g, goal, ScreenLayout.TIME_ATTACK);

        drawButton(g, ScreenLayout.BACK, ImageAsset.BUTTON_BACK, "BACK");
    }

    private void drawDescription(Graphics2D g, String text, Rectangle button) {
        g.setColor(SOFT_TEXT);
        g.setFont(assets.getFont(15f));
        TextUtil.drawCentered(g, text, new Rectangle(button.x, button.y + button.height + 8, button.width, 24));
    }
}
