package com.blockexe.fx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Floating "+points" text that rises and fades out.
 */
public class ScorePopupEffect extends Effect {

    private static final Color NORMAL_COLOR = Color.WHITE;
    private static final Color BONUS_COLOR = new Color(0xFFD23F);

    private final String text;
    private final int x;
    private final int y;
    private final Font font;
    private final Color color;

    public ScorePopupEffect(int points, boolean bonus, int x, int y, Font font) {
        super(900);
        this.text = "+" + points;
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = bonus ? BONUS_COLOR : NORMAL_COLOR;
    }

    @Override
    public void draw(Graphics2D g) {
        float progress = getProgress();
        int rise = (int) (progress * 50);

        Composite original = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - progress));
        g.setFont(font);
        int width = g.getFontMetrics().stringWidth(text);

        g.setColor(Color.BLACK);
        g.drawString(text, x - width / 2 + 2, y - rise + 2);
        g.setColor(color);
        g.drawString(text, x - width / 2, y - rise);
        g.setComposite(original);
    }
}
