package com.blockexe.render;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public final class TextUtil {

    private TextUtil() {
    }

    /** Draws text centered inside the rectangle using the current font and color. */
    public static void drawCentered(Graphics2D g, String text, Rectangle area) {
        FontMetrics metrics = g.getFontMetrics();
        int x = area.x + (area.width - metrics.stringWidth(text)) / 2;
        int y = area.y + (area.height - metrics.getHeight()) / 2 + metrics.getAscent();
        g.drawString(text, x, y);
    }

    public static String formatTime(int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
    }
}
