package com.blockexe.audio;

import com.blockexe.config.GameConfig;
import com.blockexe.core.PlaceResult;
import com.blockexe.io.GameLogger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Loads every sound into a Clip once and plays them on request.
 * If a file or the audio device is missing, that sound is skipped and the game keeps running.
 */
public class SoundManager {

    private static final Logger LOG = GameLogger.get(SoundManager.class);

    private final Map<SoundEffect, Clip> clips = new HashMap<>();
    private boolean muted;

    public void loadAll() {
        for (SoundEffect effect : SoundEffect.values()) {
            Clip clip = loadClip(effect);
            if (clip != null) {
                clips.put(effect, clip);
            }
        }
        LOG.info("Loaded " + clips.size() + " of " + SoundEffect.values().length + " sounds");
    }

    public void play(SoundEffect effect) {
        Clip clip = clips.get(effect);
        if (muted || clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    /** Plays the right sound for a placement: clear, place or invalid. */
    public void playPlaceResult(PlaceResult result) {
        if (!result.isPlaced()) {
            play(SoundEffect.INVALID);
        } else if (result.getLinesCleared() > 0) {
            play(SoundEffect.CLEAR);
        } else {
            play(SoundEffect.PLACE);
        }
    }

    public void toggleMute() {
        muted = !muted;
        LOG.info(muted ? "Sound muted" : "Sound unmuted");
    }

    public boolean isMuted() {
        return muted;
    }

    private Clip loadClip(SoundEffect effect) {
        File file = GameConfig.SOUND_DIR.resolve(effect.getFileName()).toFile();
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException e) {
            LOG.warning("Could not load sound " + file);
        } catch (LineUnavailableException | IllegalArgumentException e) {
            LOG.warning("No audio device available for " + file);
        }
        return null;
    }
}
