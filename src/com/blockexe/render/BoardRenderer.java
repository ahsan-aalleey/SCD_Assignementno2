package com.blockexe.render;

import com.blockexe.config.GameConfig;
import com.blockexe.io.AssetManager;
import com.blockexe.model.BlockColor;
import com.blockexe.model.BlockPiece;
import com.blockexe.model.Board;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

/**
 * Draws the 8 by 8 board and the blocks on it.
 */
public class BoardRenderer {

    private static final Color BOARD_BACKGROUND = new Color(0x141D36);
    private static final Color EMPTY_CELL = new Color(0x2A3A63);
    private static final Color VALID_TINT = new Color(0x3DDC84);
    private static final Color INVALID_TINT = new Color(0xFF4D4D);

    private final AssetManager assets;

    public BoardRenderer(AssetManager assets) {
        this.assets = assets;
    }

    public void draw(Graphics2D g, Board board) {
        int padding = 8;
        g.setColor(BOARD_BACKGROUND);
        g.fillRoundRect(Layout.BOARD_X - padding, Layout.BOARD_Y - padding,
                Layout.BOARD_PIXELS + padding * 2, Layout.BOARD_PIXELS + padding * 2, 20, 20);

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                int x = Layout.BOARD_X + col * Layout.CELL_SIZE;
                int y = Layout.BOARD_Y + row * Layout.CELL_SIZE;
                BlockColor color = board.getCell(row, col);

                if (color == null) {
                    g.setColor(EMPTY_CELL);
                    g.fillRoundRect(x + 3, y + 3, Layout.CELL_SIZE - 6, Layout.CELL_SIZE - 6, 10, 10);
                } else {
                    g.drawImage(assets.getBlockImage(color), x, y, Layout.CELL_SIZE, Layout.CELL_SIZE, null);
                }
            }
        }
    }

    /**
     * Draws where the piece would land, tinted green if the spot is valid and red if not.
     * Cells that fall outside the board are skipped.
     */
    public void drawPreview(Graphics2D g, BlockPiece piece, int row, int col, boolean valid) {
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(valid ? VALID_TINT : INVALID_TINT);

        for (int r = 0; r < piece.getRows(); r++) {
            for (int c = 0; c < piece.getCols(); c++) {
                int boardRow = row + r;
                int boardCol = col + c;
                if (!piece.isFilled(r, c) || boardRow < 0 || boardRow >= GameConfig.BOARD_SIZE
                        || boardCol < 0 || boardCol >= GameConfig.BOARD_SIZE) {
                    continue;
                }
                int x = Layout.BOARD_X + boardCol * Layout.CELL_SIZE;
                int y = Layout.BOARD_Y + boardRow * Layout.CELL_SIZE;
                g.drawImage(assets.getBlockImage(piece.getColor()), x, y, Layout.CELL_SIZE, Layout.CELL_SIZE, null);
                g.fillRoundRect(x + 3, y + 3, Layout.CELL_SIZE - 6, Layout.CELL_SIZE - 6, 10, 10);
            }
        }
        g.setComposite(original);
    }
}
