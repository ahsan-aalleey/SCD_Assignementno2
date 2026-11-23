package com.blockexe.ui;

import com.blockexe.audio.SoundEffect;
import com.blockexe.audio.SoundManager;
import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.fx.EffectManager;
import com.blockexe.input.KeyboardHandler;
import com.blockexe.input.MouseHandler;
import com.blockexe.input.PreviewState;
import com.blockexe.io.AssetManager;
import com.blockexe.model.GameState;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Runs the game loop. A Swing Timer calls actionPerformed about 60 times a second:
 * it updates the game and the animations, then asks Swing to repaint. paintComponent only draws.
 */
public class GamePanel extends JPanel implements ActionListener {

    private static final Color BACKGROUND = new Color(0x1E2A4A);

    private final Game game;
    private final SoundManager sounds;
    private final EffectManager effects;
    private final Timer timer;
    private final PreviewState preview = new PreviewState();
    private final PlayScreen playScreen;
    private final MenuScreen menuScreen;
    private final ModeSelectScreen modeSelectScreen;
    private final InstructionsScreen instructionsScreen;
    private final PauseScreen pauseScreen;
    private final GameOverScreen gameOverScreen;
    private long lastFrameTime;
    private GameState lastState;
    private int lastGameNumber;

    public GamePanel(Game game, AssetManager assets, SoundManager sounds) {
        this.game = game;
        this.sounds = sounds;
        this.effects = new EffectManager(assets);
        this.playScreen = new PlayScreen(game, assets, sounds, effects, preview);
        this.menuScreen = new MenuScreen(game, assets);
        this.modeSelectScreen = new ModeSelectScreen(game, assets);
        this.instructionsScreen = new InstructionsScreen(game, assets);
        this.pauseScreen = new PauseScreen(game, assets, playScreen);
        this.gameOverScreen = new GameOverScreen(game, assets, playScreen);
        this.timer = new Timer(GameConfig.FRAME_DELAY_MS, this);
        this.lastState = game.getState();
        this.lastGameNumber = game.getGamesStarted();

        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setBackground(BACKGROUND);
        setDoubleBuffered(true);
        setFocusable(true);

        MouseHandler mouse = new MouseHandler(game, preview, sounds, effects, this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addKeyListener(new KeyboardHandler(game, preview, sounds, effects));
    }

    public void start() {
        lastFrameTime = System.currentTimeMillis();
        timer.start();
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.currentTimeMillis();
        long delta = now - lastFrameTime;
        lastFrameTime = now;

        game.update(delta);

        // Animations freeze while paused, but finish playing out behind the game over screen
        if (game.getState() != GameState.PAUSED) {
            effects.update(delta);
        }
        if (game.getState() != GameState.PLAYING) {
            preview.clear();
        }

        // A new game started, so old animations should not carry over
        if (game.getGamesStarted() != lastGameNumber) {
            effects.clear();
            lastGameNumber = game.getGamesStarted();
        }

        // The game can end from a placement or from the timer, so check for it here
        if (game.getState() == GameState.GAME_OVER && lastState != GameState.GAME_OVER) {
            sounds.play(SoundEffect.GAME_OVER);
        }
        lastState = game.getState();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        getCurrentScreen().draw(g2);
    }

    private Screen getCurrentScreen() {
        switch (game.getState()) {
            case MODE_SELECT:
                return modeSelectScreen;
            case INSTRUCTIONS:
                return instructionsScreen;
            case PLAYING:
                return playScreen;
            case PAUSED:
                return pauseScreen;
            case GAME_OVER:
                return gameOverScreen;
            default:
                return menuScreen;
        }
    }
}
