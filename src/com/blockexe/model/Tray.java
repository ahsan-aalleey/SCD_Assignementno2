package com.blockexe.model;

import com.blockexe.config.GameConfig;

/**
 * The three slots below the board. An empty slot is null.
 */
public class Tray {

    private final BlockPiece[] slots = new BlockPiece[GameConfig.TRAY_SLOTS];

    public void refill(PieceGenerator generator) {
        for (int i = 0; i < slots.length; i++) {
            slots[i] = generator.nextPiece();
        }
    }

    public BlockPiece getPiece(int slot) {
        if (slot < 0 || slot >= slots.length) {
            return null;
        }
        return slots[slot];
    }

    public void removePiece(int slot) {
        if (slot >= 0 && slot < slots.length) {
            slots[slot] = null;
        }
    }

    public boolean isEmpty() {
        for (BlockPiece piece : slots) {
            if (piece != null) {
                return false;
            }
        }
        return true;
    }

    public int getSlotCount() {
        return slots.length;
    }

    public void clear() {
        for (int i = 0; i < slots.length; i++) {
            slots[i] = null;
        }
    }
}
