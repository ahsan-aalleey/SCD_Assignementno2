package com.blockexe.model;

import java.util.Random;

/**
 * Creates random pieces with a random shape and color.
 */
public class PieceGenerator {

    private final Random random;

    public PieceGenerator() {
        this.random = new Random();
    }

    public PieceGenerator(long seed) {
        this.random = new Random(seed);
    }

    public BlockPiece nextPiece() {
        int[][] shape = ShapeCatalog.getShape(random.nextInt(ShapeCatalog.getShapeCount()));
        BlockColor[] colors = BlockColor.values();
        BlockColor color = colors[random.nextInt(colors.length)];
        return new BlockPiece(shape, color);
    }
}
