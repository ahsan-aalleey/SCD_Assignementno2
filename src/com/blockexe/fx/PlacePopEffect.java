package com.blockexe.fx;

import com.blockexe.model.BlockPiece;
import com.blockexe.render.Layout;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

/**
 * A white outline that grows out of each placed cell and fades away.
 */
public class PlacePopEffect extends Effect {

    private final BlockPiece piece;
    private final int row;
    private final int col;

    public PlacePopEffect(BlockPiece piece, int row, int col) {
        super(300);
        this.piece = piece;
        this.row = row;
        this.col = col;
    }

    @Override
    public void draw(Graphics2D g) {
        float progress = getProgress();
        int grow = (int) (progress * 8);

        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - progress));
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));

        for (int r = 0; r < piece.getRows(); r++) {
            for (int c = 0; c < piece.getCols(); c++) {
                if (piece.isFilled(r, c)) {
                    int x = Layout.BOARD_X + (col + c) * Layout.CELL_SIZE;
                    int y = Layout.BOARD_Y + (row + r) * Layout.CELL_SIZE;
                    g.drawRoundRect(x + 4 - grow, y + 4 - grow,
                            Layout.CELL_SIZE - 8 + grow * 2, Layout.CELL_SIZE - 8 + grow * 2, 12, 12);
                }
            }
        }
        g.setComposite(original);
    }
}
