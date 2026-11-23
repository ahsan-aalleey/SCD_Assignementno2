package com.blockexe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds every shape the game can give the player, including the
 * horizontal, vertical and rotated versions of each one.
 */
public final class ShapeCatalog {

    private static final List<int[][]> SHAPES = new ArrayList<>();

    static {
        // Single square
        SHAPES.add(new int[][]{{1}});

        // Straight lines of 2 to 5 cells, horizontal and vertical
        for (int length = 2; length <= 5; length++) {
            int[][] line = new int[1][length];
            for (int i = 0; i < length; i++) {
                line[0][i] = 1;
            }
            SHAPES.add(line);
            SHAPES.add(rotate(line));
        }

        // Squares
        SHAPES.add(new int[][]{{1, 1}, {1, 1}});
        SHAPES.add(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}});

        // Rectangles
        int[][] rectangle = {{1, 1, 1}, {1, 1, 1}};
        SHAPES.add(rectangle);
        SHAPES.add(rotate(rectangle));

        // L, J and T shapes in all 4 rotations
        addAllRotations(new int[][]{{1, 0}, {1, 0}, {1, 1}});
        addAllRotations(new int[][]{{0, 1}, {0, 1}, {1, 1}});
        addAllRotations(new int[][]{{1, 1, 1}, {0, 1, 0}});

        // S and Z shapes, horizontal and vertical
        int[][] s = {{0, 1, 1}, {1, 1, 0}};
        int[][] z = {{1, 1, 0}, {0, 1, 1}};
        SHAPES.add(s);
        SHAPES.add(rotate(s));
        SHAPES.add(z);
        SHAPES.add(rotate(z));
    }

    private ShapeCatalog() {
    }

    public static int getShapeCount() {
        return SHAPES.size();
    }

    public static int[][] getShape(int index) {
        return SHAPES.get(index);
    }

    private static void addAllRotations(int[][] shape) {
        int[][] current = shape;
        for (int i = 0; i < 4; i++) {
            SHAPES.add(current);
            current = rotate(current);
        }
    }

    /** Rotates a shape 90 degrees clockwise. */
    private static int[][] rotate(int[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = shape[r][c];
            }
        }
        return rotated;
    }
}
