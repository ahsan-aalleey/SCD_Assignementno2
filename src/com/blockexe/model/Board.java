package com.blockexe.model;

import com.blockexe.config.GameConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * The 8 by 8 grid. Each cell holds the color of its block, or null if empty.
 */
public class Board {

    private final int size;
    private final BlockColor[][] cells;

    public Board() {
        this.size = GameConfig.BOARD_SIZE;
        this.cells = new BlockColor[size][size];
    }

    public int getSize() {
        return size;
    }

    public BlockColor getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /** True if every filled cell of the piece lands inside the board on an empty cell. */
    public boolean canPlace(BlockPiece piece, int row, int col) {
        for (int r = 0; r < piece.getRows(); r++) {
            for (int c = 0; c < piece.getCols(); c++) {
                if (!piece.isFilled(r, c)) {
                    continue;
                }
                int boardRow = row + r;
                int boardCol = col + c;
                if (!isInside(boardRow, boardCol) || cells[boardRow][boardCol] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean place(BlockPiece piece, int row, int col) {
        if (!canPlace(piece, row, col)) {
            return false;
        }
        for (int r = 0; r < piece.getRows(); r++) {
            for (int c = 0; c < piece.getCols(); c++) {
                if (piece.isFilled(r, c)) {
                    cells[row + r][col + c] = piece.getColor();
                }
            }
        }
        return true;
    }

    public boolean canFitAnywhere(BlockPiece piece) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (canPlace(piece, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> findFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            boolean full = true;
            for (int col = 0; col < size; col++) {
                if (cells[row][col] == null) {
                    full = false;
                    break;
                }
            }
            if (full) {
                fullRows.add(row);
            }
        }
        return fullRows;
    }

    public List<Integer> findFullColumns() {
        List<Integer> fullCols = new ArrayList<>();
        for (int col = 0; col < size; col++) {
            boolean full = true;
            for (int row = 0; row < size; row++) {
                if (cells[row][col] == null) {
                    full = false;
                    break;
                }
            }
            if (full) {
                fullCols.add(col);
            }
        }
        return fullCols;
    }

    /**
     * Clears the given rows and columns together and returns how many cells were removed.
     * A cell where a row and a column cross is only counted once, because it is
     * already empty when the second line reaches it.
     */
    public int clearLines(List<Integer> rows, List<Integer> cols) {
        int cleared = 0;
        for (int row : rows) {
            for (int col = 0; col < size; col++) {
                if (cells[row][col] != null) {
                    cells[row][col] = null;
                    cleared++;
                }
            }
        }
        for (int col : cols) {
            for (int row = 0; row < size; row++) {
                if (cells[row][col] != null) {
                    cells[row][col] = null;
                    cleared++;
                }
            }
        }
        return cleared;
    }

    public void reset() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = null;
            }
        }
    }
}
