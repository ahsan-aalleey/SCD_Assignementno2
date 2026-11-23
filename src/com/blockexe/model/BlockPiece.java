package com.blockexe.model;

/**
 * A piece made of connected cells. The shape is a grid where 1 means filled.
 */
public class BlockPiece {

    private final int[][] shape;
    private final BlockColor color;

    public BlockPiece(int[][] shape, BlockColor color) {
        if (shape == null || shape.length == 0 || color == null) {
            throw new IllegalArgumentException("A piece needs a shape and a color");
        }
        this.shape = copy(shape);
        this.color = color;
    }

    public int getRows() {
        return shape.length;
    }

    public int getCols() {
        return shape[0].length;
    }

    public boolean isFilled(int row, int col) {
        return shape[row][col] == 1;
    }

    public int getCellCount() {
        int count = 0;
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getCols(); c++) {
                if (isFilled(r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    public BlockColor getColor() {
        return color;
    }

    private static int[][] copy(int[][] source) {
        int[][] result = new int[source.length][];
        for (int r = 0; r < source.length; r++) {
            result[r] = source[r].clone();
        }
        return result;
    }
}
