package com.blockexe.input;

import com.blockexe.audio.SoundEffect;
import com.blockexe.audio.SoundManager;
import com.blockexe.config.GameConfig;
import com.blockexe.core.Game;
import com.blockexe.core.PlaceResult;
import com.blockexe.fx.EffectManager;
import com.blockexe.model.BlockPiece;
import com.blockexe.model.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keyboard controls: 1, 2, 3 select a piece, arrows move it, Enter or Space places it,
 * Esc cancels. P pauses, R restarts and M mutes.
 */
public class KeyboardHandler extends KeyAdapter {

    private final Game game;
    private final PreviewState preview;
    private final SoundManager sounds;
    private final EffectManager effects;

    public KeyboardHandler(Game game, PreviewState preview, SoundManager sounds, EffectManager effects) {
        this.game = game;
        this.preview = preview;
        this.sounds = sounds;
        this.effects = effects;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        GameState state = game.getState();

        if (key == KeyEvent.VK_M) {
            sounds.toggleMute();
        } else if (key == KeyEvent.VK_P) {
            game.togglePause();
        } else if (key == KeyEvent.VK_R) {
            if (state == GameState.PLAYING || state == GameState.PAUSED || state == GameState.GAME_OVER) {
                preview.clear();
                game.restart();
            }
        } else if (state == GameState.PLAYING && !preview.isDragging()) {
            handleGameplayKey(key);
        }
    }

    private void handleGameplayKey(int key) {
        switch (key) {
            case KeyEvent.VK_1:
            case KeyEvent.VK_2:
            case KeyEvent.VK_3:
                selectPiece(key - KeyEvent.VK_1);
                break;
            case KeyEvent.VK_LEFT:
                move(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                move(0, 1);
                break;
            case KeyEvent.VK_UP:
                move(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                move(1, 0);
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                place();
                break;
            case KeyEvent.VK_ESCAPE:
                game.clearSelection();
                break;
            default:
                break;
        }
    }

    private void selectPiece(int slot) {
        if (game.selectPiece(slot)) {
            sounds.play(SoundEffect.SELECT);
            BlockPiece piece = getSelectedPiece();
            int row = (GameConfig.BOARD_SIZE - piece.getRows()) / 2;
            int col = (GameConfig.BOARD_SIZE - piece.getCols()) / 2;
            preview.setCell(row, col);
        }
    }

    /** Moves the preview one cell, keeping the whole piece on the board. */
    private void move(int rowChange, int colChange) {
        BlockPiece piece = getSelectedPiece();
        if (piece == null) {
            return;
        }
        int maxRow = GameConfig.BOARD_SIZE - piece.getRows();
        int maxCol = GameConfig.BOARD_SIZE - piece.getCols();
        int row = Math.max(0, Math.min(maxRow, preview.getRow() + rowChange));
        int col = Math.max(0, Math.min(maxCol, preview.getCol() + colChange));
        preview.setCell(row, col);
    }

    /** An invalid spot keeps the piece selected so the player can move it somewhere else. */
    private void place() {
        BlockPiece piece = getSelectedPiece();
        if (piece != null) {
            PlaceResult result = game.tryPlace(game.getSelectedSlot(), preview.getRow(), preview.getCol());
            sounds.playPlaceResult(result);
            effects.onPlace(result, piece, preview.getRow(), preview.getCol());
        }
    }

    private BlockPiece getSelectedPiece() {
        return game.getTray().getPiece(game.getSelectedSlot());
    }
}
