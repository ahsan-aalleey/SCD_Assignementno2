package com.blockexe.fx;

import com.blockexe.config.GameConfig;
import com.blockexe.render.Layout;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A white flash over the rows and columns that were just cleared.
 */
public class ClearFlashEffect extends Effect {

    private final List<Integer> rows;
    private final List<Integer> cols;

    public ClearFlashEffect(List<Integer> rows, List<Integer> cols) {
        super(400);
        this.rows = new ArrayList<>(rows);
        this.cols = new ArrayList<>(cols);
    }

    @Override
    public void draw(Graphics2D g) {
        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f * (1 - getProgress())));
        g.setColor(Color.WHITE);

        for (int row : rows) {
            int y = Layout.BOARD_Y + row * Layout.CELL_SIZE;
            g.fillRoundRect(Layout.BOARD_X, y + 2, Layout.BOARD_PIXELS, Layout.CELL_SIZE - 4, 12, 12);
        }
        for (int col : cols) {
            int x = Layout.BOARD_X + col * Layout.CELL_SIZE;
            g.fillRoundRect(x + 2, Layout.BOARD_Y, Layout.CELL_SIZE - 4, GameConfig.BOARD_SIZE * Layout.CELL_SIZE, 12, 12);
        }
        g.setComposite(original);
    }
}
