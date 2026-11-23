package com.blockexe.input;

import com.blockexe.audio.SoundEffect;
import com.blockexe.audio.SoundManager;
import com.blockexe.core.Game;
import com.blockexe.core.PlaceResult;
import com.blockexe.fx.EffectManager;
import com.blockexe.model.BlockPiece;
import com.blockexe.model.GameMode;
import com.blockexe.model.GameState;
import com.blockexe.render.Layout;
import com.blockexe.render.ScreenLayout;
import com.blockexe.ui.GamePanel;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles menu button clicks and dragging pieces from the tray onto the board.
 */
public class MouseHandler extends MouseAdapter {

    private final Game game;
    private final PreviewState preview;
    private final SoundManager sounds;
    private final EffectManager effects;
    private final GamePanel panel;

    public MouseHandler(Game game, PreviewState preview, SoundManager sounds, EffectManager effects,
                        GamePanel panel) {
        this.game = game;
        this.preview = preview;
        this.sounds = sounds;
        this.effects = effects;
        this.panel = panel;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        Point point = event.getPoint();
        if (game.getState() == GameState.PLAYING) {
            handleGameplayPress(point);
        } else {
            GameState before = game.getState();
            handleScreenClick(point);
            if (game.getState() != before) {
                sounds.play(SoundEffect.SELECT);
            }
        }
        panel.requestFocusInWindow();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (preview.isDragging()) {
            updateDrag(event.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (!preview.isDragging()) {
            return;
        }
        if (preview.isOverBoard()) {
            BlockPiece piece = game.getTray().getPiece(game.getSelectedSlot());
            PlaceResult result = game.tryPlace(game.getSelectedSlot(), preview.getRow(), preview.getCol());
            sounds.playPlaceResult(result);
            effects.onPlace(result, piece, preview.getRow(), preview.getCol());
        }
        // A valid drop already removed the piece. An invalid drop sends it back to the tray.
        game.clearSelection();
        preview.clear();
    }

    private void handleGameplayPress(Point point) {
        if (Layout.PAUSE_BUTTON.contains(point)) {
            game.togglePause();
            return;
        }
        if (Layout.SOUND_BUTTON.contains(point)) {
            sounds.toggleMute();
            return;
        }
        for (int slot = 0; slot < game.getTray().getSlotCount(); slot++) {
            if (Layout.getSlotBounds(slot).contains(point) && game.selectPiece(slot)) {
                sounds.play(SoundEffect.SELECT);
                preview.startDrag();
                updateDrag(point);
                return;
            }
        }
    }

    /** Keeps the piece centered under the cursor and works out which board cell it snaps to. */
    private void updateDrag(Point point) {
        BlockPiece piece = game.getTray().getPiece(game.getSelectedSlot());
        if (piece == null) {
            return;
        }
        int pieceLeft = point.x - piece.getCols() * Layout.CELL_SIZE / 2;
        int pieceTop = point.y - piece.getRows() * Layout.CELL_SIZE / 2;
        int col = Math.round((pieceLeft - Layout.BOARD_X) / (float) Layout.CELL_SIZE);
        int row = Math.round((pieceTop - Layout.BOARD_Y) / (float) Layout.CELL_SIZE);

        preview.setMouse(point.x, point.y, isOverBoard(point));
        preview.setCell(row, col);
    }

    private boolean isOverBoard(Point point) {
        return point.x >= Layout.BOARD_X && point.x < Layout.BOARD_X + Layout.BOARD_PIXELS
                && point.y >= Layout.BOARD_Y && point.y < Layout.BOARD_Y + Layout.BOARD_PIXELS;
    }

    private void handleScreenClick(Point point) {
        switch (game.getState()) {
            case MENU:
                if (ScreenLayout.PLAY.contains(point)) {
                    game.openModeSelect();
                } else if (ScreenLayout.INSTRUCTIONS.contains(point)) {
                    game.openInstructions();
                } else if (ScreenLayout.EXIT.contains(point)) {
                    System.exit(0);
                }
                break;
            case MODE_SELECT:
                if (ScreenLayout.CLASSIC.contains(point)) {
                    game.startNewGame(GameMode.CLASSIC);
                } else if (ScreenLayout.TIME_ATTACK.contains(point)) {
                    game.startNewGame(GameMode.TIME_ATTACK);
                } else if (ScreenLayout.BACK.contains(point)) {
                    game.openMenu();
                }
                break;
            case INSTRUCTIONS:
                if (ScreenLayout.BACK.contains(point)) {
                    game.openMenu();
                }
                break;
            case PAUSED:
                if (ScreenLayout.RESUME.contains(point)) {
                    game.togglePause();
                } else if (ScreenLayout.RESTART.contains(point)) {
                    game.restart();
                } else if (ScreenLayout.MENU.contains(point)) {
                    game.openMenu();
                }
                break;
            case GAME_OVER:
                if (ScreenLayout.RESTART.contains(point)) {
                    game.restart();
                } else if (ScreenLayout.MENU.contains(point)) {
                    game.openMenu();
                }
                break;
            default:
                break;
        }
    }
}
