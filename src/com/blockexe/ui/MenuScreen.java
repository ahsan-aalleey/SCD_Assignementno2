package com.blockexe.ui;

import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.model.BlockColor;
import com.blockexe.render.ScreenLayout;
import com.blockexe.render.TextUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MenuScreen extends BaseScreen {

    public MenuScreen(Game game, AssetManager assets) {
        super(game, assets);
    }

    @Override
    public void draw(Graphics2D g) {
        drawBackground(g);
        drawTitle(g, GameConfig.GAME_TITLE, "The Block Burst Challenge");
        drawBlockRow(g);

        drawButton(g, ScreenLayout.PLAY, ImageAsset.BUTTON_CLASSIC, "PLAY");
        drawButton(g, ScreenLayout.INSTRUCTIONS, ImageAsset.BUTTON_INSTRUCTIONS, "INSTRUCTIONS");
        drawButton(g, ScreenLayout.EXIT, ImageAsset.BUTTON_EXIT, "EXIT");

        g.drawImage(assets.getImage(ImageAsset.ICON_BEST), 190, 520, 32, 30, null);
        g.setColor(TEXT);
        g.setFont(assets.getFont(22f));
        g.drawString("BEST " + game.getBestScore(), 232, 545);

        g.setColor(SOFT_TEXT);
        g.setFont(assets.getFont(13f));
        TextUtil.drawCentered(g, "MOUSE OR KEYBOARD   M TO MUTE", new Rectangle(0, 760, GameConfig.WINDOW_WIDTH, 30));
    }

    /** A row of one block in every color, as decoration under the title. */
    private void drawBlockRow(Graphics2D g) {
        BlockColor[] colors = BlockColor.values();
        int size = 40;
        int startX = (GameConfig.WINDOW_WIDTH - colors.length * size) / 2;
        for (int i = 0; i < colors.length; i++) {
            g.drawImage(assets.getBlockImage(colors[i]), startX + i * size, 175, size, size, null);
        }
    }
}
