package com.blockexe.ui;

import com.blockexe.core.Game;
import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.model.GameMode;
import com.blockexe.render.ScreenLayout;
import com.blockexe.render.TextUtil;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Shows the final result on top of the finished board.
 */
public class GameOverScreen extends BaseScreen {

    private final Screen background;

    public GameOverScreen(Game game, AssetManager assets, Screen background) {
        super(game, assets);
        this.background = background;
    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g);
        drawDim(g);

        String mode = game.getMode() == GameMode.TIME_ATTACK ? "Time Attack" : "Classic";
        drawTitle(g, game.isWon() ? "YOU WIN" : "GAME OVER", mode);
        drawPanel(g, new Rectangle(90, 170, 360, 180));

        ImageAsset icon = game.isWon() ? ImageAsset.ICON_WIN : ImageAsset.ICON_GAME_OVER;
        g.drawImage(assets.getImage(icon), 252, 182, 36, 32, null);

        g.setColor(TEXT);
        g.setFont(assets.getFont(17f));
        TextUtil.drawCentered(g, getReason(), new Rectangle(90, 218, 360, 24));

        g.setFont(assets.getFont(30f));
        TextUtil.drawCentered(g, "SCORE " + game.getScore(), new Rectangle(90, 246, 360, 40));

        g.setColor(SOFT_TEXT);
        g.setFont(assets.getFont(17f));
        TextUtil.drawCentered(g, "BEST " + game.getBestScore(), new Rectangle(90, 288, 360, 24));

        if (game.isNewBest()) {
            g.setColor(HIGHLIGHT);
            TextUtil.drawCentered(g, "NEW BEST!", new Rectangle(90, 316, 360, 24));
        }

        drawButton(g, ScreenLayout.RESTART, ImageAsset.BUTTON_RESTART, "RESTART");
        drawButton(g, ScreenLayout.MENU, ImageAsset.BUTTON_MENU, "MAIN MENU");
    }

    private String getReason() {
        if (game.isWon()) {
            return "TARGET REACHED";
        }
        if (game.getMode() == GameMode.TIME_ATTACK && game.getRemainingSeconds() == 0) {
            return "TIME IS UP";
        }
        return "NO MOVES LEFT";
    }
}
