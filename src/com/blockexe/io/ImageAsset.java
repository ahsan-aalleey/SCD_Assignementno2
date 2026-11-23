package com.blockexe.io;

/**
 * Every image the game uses, with its path inside assets/ui.
 */
public enum ImageAsset {
    BLOCK_BLUE("blocks/blue.png"),
    BLOCK_GREEN("blocks/green.png"),
    BLOCK_ORANGE("blocks/orange.png"),
    BLOCK_PINK("blocks/pink.png"),
    BLOCK_PURPLE("blocks/purple.png"),
    BLOCK_RED("blocks/red.png"),
    BLOCK_YELLOW("blocks/yellow.png"),

    BUTTON_BACK("back/button.png"),
    BUTTON_CLASSIC("classic/button.png"),
    BUTTON_EXIT("exit/button.png"),
    BUTTON_INSTRUCTIONS("instructions/button.png"),
    BUTTON_MENU("menu/button.png"),
    BUTTON_MUTE("mute/button.png"),
    BUTTON_PAUSE("pause/button.png"),
    BUTTON_RESTART("restart/button.png"),
    BUTTON_RESUME("resume/button.png"),
    BUTTON_TIME_ATTACK("time-attack/button.png"),

    ICON_BEST("best/star.png"),
    ICON_GAME_OVER("game-over/cross.png"),
    ICON_PLAY("play/icon.png"),
    ICON_RESTART("restart/icon.png"),
    ICON_WIN("win/check.png"),

    SCORE_BACKGROUND("score/background.png"),
    SLOT_BACKGROUND("slot/background.png");

    private final String path;

    ImageAsset(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
