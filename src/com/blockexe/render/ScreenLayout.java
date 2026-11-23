package com.blockexe.render;

import java.awt.Rectangle;

/** Shared hit areas for menu and overlay buttons. Drawing and clicking both use these. */
public final class ScreenLayout {

    public static final Rectangle PLAY = new Rectangle(150, 260, 240, 58);
    public static final Rectangle INSTRUCTIONS = new Rectangle(150, 335, 240, 58);
    public static final Rectangle EXIT = new Rectangle(150, 410, 240, 58);
    public static final Rectangle CLASSIC = new Rectangle(110, 240, 320, 58);
    public static final Rectangle TIME_ATTACK = new Rectangle(110, 360, 320, 58);
    public static final Rectangle BACK = new Rectangle(150, 650, 240, 58);
    public static final Rectangle RESUME = new Rectangle(150, 290, 240, 58);
    public static final Rectangle RESTART = new Rectangle(150, 375, 240, 58);
    public static final Rectangle MENU = new Rectangle(150, 450, 240, 58);

    private ScreenLayout() {
    }
}
