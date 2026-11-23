package com.blockexe.ui;

import java.awt.Graphics2D;

/**
 * One screen of the game, such as the menu or the gameplay screen.
 * GamePanel only knows it has a Screen and calls draw() on it.
 */
public interface Screen {

    void draw(Graphics2D g);
}
