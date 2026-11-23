package com.blockexe.render;

import com.blockexe.io.AssetManager;
import com.blockexe.io.ImageAsset;
import com.blockexe.model.BlockPiece;
import com.blockexe.model.Tray;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Draws the three tray slots and the pieces inside them.
 */
public class TrayRenderer {

    private static final Color SELECTED_BORDER = new Color(0xFFD23F);

    private final AssetManager assets;

    public TrayRenderer(AssetManager assets) {
        this.assets = assets;
    }

    /** If hideSelected is true, the selected piece is not drawn because it is being dragged. */
    public void draw(Graphics2D g, Tray tray, int selectedSlot, boolean hideSelected) {
        BufferedImage slotImage = assets.getImage(ImageAsset.SLOT_BACKGROUND);

        for (int i = 0; i < tray.getSlotCount(); i++) {
            Rectangle slot = Layout.getSlotBounds(i);
            g.drawImage(slotImage, slot.x, slot.y, slot.width, slot.height, null);

            if (i == selectedSlot) {
                g.setColor(SELECTED_BORDER);
                g.setStroke(new BasicStroke(4));
                g.drawRoundRect(slot.x, slot.y, slot.width, slot.height, 16, 16);
            }

            g.setColor(Color.GRAY);
            g.setFont(assets.getFont(16f));
            g.drawString(String.valueOf(i + 1), slot.x + 10, slot.y + 22);

            BlockPiece piece = tray.getPiece(i);
            boolean dragged = hideSelected && i == selectedSlot;
            if (piece != null && !dragged) {
                drawPieceCentered(g, piece, slot);
            }
        }
    }

    private void drawPieceCentered(Graphics2D g, BlockPiece piece, Rectangle slot) {
        int size = Layout.TRAY_CELL_SIZE;
        int startX = slot.x + (slot.width - piece.getCols() * size) / 2;
        int startY = slot.y + (slot.height - piece.getRows() * size) / 2;
        drawPiece(g, piece, startX, startY, size);
    }

    /** Draws a piece with its top-left corner at (x, y). Also used for the drag preview later. */
    public void drawPiece(Graphics2D g, BlockPiece piece, int x, int y, int cellSize) {
        BufferedImage image = assets.getBlockImage(piece.getColor());
        for (int r = 0; r < piece.getRows(); r++) {
            for (int c = 0; c < piece.getCols(); c++) {
                if (piece.isFilled(r, c)) {
                    g.drawImage(image, x + c * cellSize, y + r * cellSize, cellSize, cellSize, null);
                }
            }
        }
    }
}
