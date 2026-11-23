package com.blockexe.input;

/**
 * Where the selected piece would be placed. Shared by the mouse handler,
 * the keyboard handler and the play screen.
 */
public class PreviewState {

    private boolean dragging;
    private boolean overBoard;
    private int mouseX;
    private int mouseY;
    private int row;
    private int col;

    public void startDrag() {
        dragging = true;
    }

    public void clear() {
        dragging = false;
        overBoard = false;
    }

    public void setMouse(int x, int y, boolean overBoard) {
        this.mouseX = x;
        this.mouseY = y;
        this.overBoard = overBoard;
    }

    public void setCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isOverBoard() {
        return overBoard;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
