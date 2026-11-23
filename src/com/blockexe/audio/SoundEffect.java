package com.blockexe.audio;

/**
 * Every sound the game plays, with its file inside assets/sound.
 */
public enum SoundEffect {
    SELECT("select.wav"),
    PLACE("place.wav"),
    CLEAR("clear.wav"),
    INVALID("invalid.wav"),
    GAME_OVER("invalid.wav");

    private final String fileName;

    SoundEffect(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
