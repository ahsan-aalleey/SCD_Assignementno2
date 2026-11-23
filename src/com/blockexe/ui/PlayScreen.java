package com.blockexe.ui;

import com.blockexe.audio.SoundManager;
import com.blockexe.core.Game;
import com.blockexe.fx.EffectManager;
import com.blockexe.input.PreviewState;
import com.blockexe.io.AssetManager;
import com.blockexe.model.BlockPiece;
import com.blockexe.render.BoardRenderer;
import com.blockexe.render.HudRenderer;
import com.blockexe.render.Layout;
import com.blockexe.render.TrayRenderer;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;

/**
 * The gameplay screen: HUD, board, tray, animations and the placement preview.
 */
public class PlayScreen implements Screen {

    private final Game game;
    private final SoundManager sounds;
    private final EffectManager effects;
    private final PreviewState preview;
    private final BoardRenderer boardRenderer;
    private final TrayRenderer trayRenderer;
    private final HudRenderer hudRenderer;

    public PlayScreen(Game game, AssetManager assets, SoundManager sounds, EffectManager effects,
                      PreviewState preview) {
        this.game = game;
        this.sounds = sounds;
        this.effects = effects;
        this.preview = preview;
        this.boardRenderer = new BoardRenderer(assets);
        this.trayRenderer = new TrayRenderer(assets);
        this.hudRenderer = new HudRenderer(assets);
    }

    @Override
    public void draw(Graphics2D g) {
        hudRenderer.draw(g, game, !sounds.isMuted());
        boardRenderer.draw(g, game.getBoard());
        trayRenderer.draw(g, game.getTray(), game.getSelectedSlot(), preview.isDragging());
        effects.draw(g);
        drawPreview(g);
    }

    private void drawPreview(Graphics2D g) {
        BlockPiece piece = game.getTray().getPiece(game.getSelectedSlot());
        if (piece == null) {
            return;
        }

        // Keyboard selection always shows on the board. A mouse drag shows it only over the board.
        if (!preview.isDragging() || preview.isOverBoard()) {
            boolean valid = game.getBoard().canPlace(piece, preview.getRow(), preview.getCol());
            boardRenderer.drawPreview(g, piece, preview.getRow(), preview.getCol(), valid);
        }

        if (preview.isDragging()) {
            int x = preview.getMouseX() - piece.getCols() * Layout.CELL_SIZE / 2;
            int y = preview.getMouseY() - piece.getRows() * Layout.CELL_SIZE / 2;
            Composite original = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            trayRenderer.drawPiece(g, piece, x, y, Layout.CELL_SIZE);
            g.setComposite(original);
        }
    }
}
