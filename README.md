# Block.exe

A desktop block-placement puzzle game built with Java Swing and Java 2D. Drag pieces onto an 8×8 board and clear full rows and columns to score points.

## Requirements

- JDK 21

## Running

Open the project in IntelliJ IDEA and run `com.blockexe.app.Main`. The working directory must be the project root so the game can find `assets/`, `data/` and `logs/`.

From a terminal at the project root:

```bash
javac -d out/production/block-exe $(find src -name "*.java")
java -cp out/production/block-exe com.blockexe.app.Main
```

## How to play

You get three random pieces in the tray. Place them on the board one at a time. When a row or column is completely full, it is cleared. A cell where a cleared row and column cross is only cleared once. When all three pieces are used, you get three new ones.

A piece cannot overlap other blocks or hang off the board. An invalid placement does nothing and the piece stays in the tray.

### Modes

| Mode | Goal | Ends when |
|---|---|---|
| Classic | Score as high as you can | No remaining piece fits anywhere |
| Time Attack | Reach 500 points in 3:00 | You reach 500 (win), time runs out or no piece fits (lose) |

The Time Attack target and duration are constants in `GameConfig`.

### Scoring

| Action | Points |
|---|---|
| Placing a piece | 1 per cell |
| Clearing lines | lines × lines × 10 (1 line = 10, 2 = 40, 3 = 90) |

The best score is saved to `data/best_score.txt`.

## Controls

Click **Play** and choose a mode from the main menu.

**Mouse**

- Drag a piece from a tray slot onto the board. The preview turns green on a valid spot and red on an invalid one.
- Release to place it. An invalid drop sends the piece back to the tray.
- The PAUSE and SOUND buttons at the top work during play.

**Keyboard**

| Key | Action |
|---|---|
| `1`, `2`, `3` | Select a tray piece |
| Arrow keys | Move the preview |
| `Enter` or `Space` | Place the piece |
| `Esc` | Cancel the selection |
| `P` | Pause or resume |
| `R` | Restart |
| `M` | Mute or unmute |

The pause and game-over screens also have Resume, Restart and Main Menu buttons.

## Features

- Main menu, mode select, instructions, pause and game over screens
- Placement preview that follows the mouse or keyboard, tinted green or red
- Three animations: placement pop, row and column clear flash, floating score popup
- Sound effects for select, place, clear, invalid placement and game over, with a mute toggle
- Pause stops both input and game time, and resume continues without skipping time
- Missing images, fonts, sounds or a broken best-score file are logged and replaced with fallbacks, so the game never crashes over a file

## Project structure

| Package | Classes | Responsibility |
|---|---|---|
| `app` | `Main`, `GameFrame` | Starts the game and opens the window |
| `config` | `GameConfig` | All tunable constants |
| `model` | `Board`, `BlockPiece`, `ShapeCatalog`, `PieceGenerator`, `Tray`, `GameClock`, `BlockColor`, `GameMode`, `GameState` | Game data |
| `core` | `Game`, `ScoreManager`, `PlaceResult` | Game rules and scoring |
| `ui` | `GamePanel`, `Screen`, `BaseScreen`, `PlayScreen`, `MenuScreen`, `ModeSelectScreen`, `InstructionsScreen`, `PauseScreen`, `GameOverScreen` | Game loop and screens |
| `render` | `BoardRenderer`, `TrayRenderer`, `HudRenderer`, `Layout`, `ScreenLayout`, `TextUtil` | Drawing the board, tray and HUD |
| `input` | `MouseHandler`, `KeyboardHandler`, `PreviewState` | Mouse and keyboard input |
| `fx` | `Effect`, `PlacePopEffect`, `ClearFlashEffect`, `ScorePopupEffect`, `EffectManager` | Animations |
| `audio` | `SoundManager`, `SoundEffect` | Sound effects |
| `io` | `AssetManager`, `ImageAsset`, `BestScoreStore`, `GameLogger` | Loading files, saving the best score, logging |

### Object oriented design

- **Inheritance and polymorphism:** `Effect` is an abstract class with three subclasses. `EffectManager` keeps a `List<Effect>` and calls `update()` and `draw()` without knowing which kind each one is. Every screen implements the `Screen` interface, and `GamePanel` draws whichever screen matches the current state.
- **Enums:** `GameMode`, `GameState`, `BlockColor`, `SoundEffect` and `ImageAsset`.
- **Encapsulation:** fields are private, and constructors keep objects valid. For example, `BlockPiece` rejects a missing shape and copies its array.
- **Separation:** rules live in `core` and `model`, drawing in `render`, `ui` and `fx`, and input in `input`. Game rules are updated in the Swing `Timer` callback, never in `paintComponent()`.

## Files and logs

| Path | Contents |
|---|---|
| `assets/ui/` | Images and font |
| `assets/sound/` | WAV sound effects |
| `data/best_score.txt` | Best score (created at runtime) |
| `logs/game.log` | Log file (created at runtime) |

## Credits

UI Pack and Interface Sounds by [Kenney](https://www.kenney.nl), CC0 licensed. See `assets/ui/License.txt` and `assets/sound/License.txt`.
