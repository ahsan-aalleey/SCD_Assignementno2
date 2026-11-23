package com.blockexe.fx;

import com.blockexe.core.PlaceResult;
import com.blockexe.io.AssetManager;
import com.blockexe.model.BlockPiece;
import com.blockexe.render.Layout;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Keeps the running effects. It does not care which kind each one is,
 * it just updates and draws them all and removes the finished ones.
 */
public class EffectManager {

    private final List<Effect> effects = new ArrayList<>();
    private final AssetManager assets;

    public EffectManager(AssetManager assets) {
        this.assets = assets;
    }

    /** Starts the right effects after a successful placement. */
    public void onPlace(PlaceResult result, BlockPiece piece, int row, int col) {
        if (!result.isPlaced()) {
            return;
        }
        effects.add(new PlacePopEffect(piece, row, col));
        if (result.getLinesCleared() > 0) {
            effects.add(new ClearFlashEffect(result.getClearedRows(), result.getClearedCols()));
        }

        int centerX = Layout.BOARD_X + (col * Layout.CELL_SIZE) + piece.getCols() * Layout.CELL_SIZE / 2;
        int centerY = Layout.BOARD_Y + (row * Layout.CELL_SIZE) + piece.getRows() * Layout.CELL_SIZE / 2;
        boolean bonus = result.getLinesCleared() > 0;
        effects.add(new ScorePopupEffect(result.getPointsGained(), bonus, centerX, centerY,
                assets.getFont(bonus ? 34f : 24f)));
    }

    public void update(long deltaMillis) {
        Iterator<Effect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            Effect effect = iterator.next();
            effect.update(deltaMillis);
            if (effect.isFinished()) {
                iterator.remove();
            }
        }
    }

    public void draw(Graphics2D g) {
        for (Effect effect : effects) {
            effect.draw(g);
        }
    }

    public void clear() {
        effects.clear();
    }
}
