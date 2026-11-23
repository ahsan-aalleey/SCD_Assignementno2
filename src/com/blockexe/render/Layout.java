package com.blockexe.render;

import com.blockexe.config.GameConfig;

import java.awt.Rectangle;

/**
 * Screen positions of the board, tray and HUD. Rendering and input both use these
 * so that what you see and what you click always match.
 */
public final class Layout {

    public static final int CELL_SIZE = GameConfig.CELL_SIZE;
    public static final int BOARD_PIXELS = GameConfig.BOARD_SIZE * CELL_SIZE;
    public static final int BOARD_X = (GameConfig.WINDOW_WIDTH - BOARD_PIXELS) / 2;
    public static final int BOARD_Y = 140;

    public static final int TRAY_X = 20;
    public static final int TRAY_Y = 610;
    public static final int TRAY_SLOT_WIDTH = 160;
    public static final int TRAY_SLOT_HEIGHT = 190;
    public static final int TRAY_GAP = 10;
    public static final int TRAY_CELL_SIZE = 26;

    public static final Rectangle PAUSE_BUTTON = new Rectangle(20, 28, 130, 48);
    public static final Rectangle SCORE_BOX = new Rectangle(174, 20, 192, 64);
    public static final Rectangle SOUND_BUTTON = new Rectangle(390, 28, 130, 48);

    private Layout() {
    }

    public static Rectangle getSlotBounds(int slot) {
        int x = TRAY_X + slot * (TRAY_SLOT_WIDTH + TRAY_GAP);
        return new Rectangle(x, TRAY_Y, TRAY_SLOT_WIDTH, TRAY_SLOT_HEIGHT);
    }
}
