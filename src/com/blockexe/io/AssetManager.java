package com.blockexe.io;

import com.blockexe.config.GameConfig;
import com.blockexe.model.BlockColor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Loads all images and the font once. If a file is missing, a grey box or
 * a default font is used instead so the game keeps running.
 */
public class AssetManager {

    private static final Logger LOG = GameLogger.get(AssetManager.class);
    private static final File FONT_FILE = GameConfig.UI_DIR.resolve("Font/Kenney Future Narrow.ttf").toFile();

    private final Map<ImageAsset, BufferedImage> images = new HashMap<>();
    private Font baseFont;

    public void loadAll() {
        for (ImageAsset asset : ImageAsset.values()) {
            images.put(asset, loadImage(asset));
        }
        baseFont = loadFont();
        LOG.info("Assets loaded");
    }

    public BufferedImage getImage(ImageAsset asset) {
        return images.get(asset);
    }

    public BufferedImage getBlockImage(BlockColor color) {
        switch (color) {
            case BLUE:
                return getImage(ImageAsset.BLOCK_BLUE);
            case GREEN:
                return getImage(ImageAsset.BLOCK_GREEN);
            case ORANGE:
                return getImage(ImageAsset.BLOCK_ORANGE);
            case PINK:
                return getImage(ImageAsset.BLOCK_PINK);
            case PURPLE:
                return getImage(ImageAsset.BLOCK_PURPLE);
            case RED:
                return getImage(ImageAsset.BLOCK_RED);
            default:
                return getImage(ImageAsset.BLOCK_YELLOW);
        }
    }

    public Font getFont(float size) {
        return baseFont.deriveFont(size);
    }

    private BufferedImage loadImage(ImageAsset asset) {
        File file = GameConfig.UI_DIR.resolve(asset.getPath()).toFile();
        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                return image;
            }
        } catch (IOException e) {
            // handled below
        }
        LOG.warning("Missing image: " + file);
        return createPlaceholder();
    }

    private Font loadFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, FONT_FILE);
        } catch (IOException | FontFormatException e) {
            LOG.warning("Missing font: " + FONT_FILE + ", using default font");
            return new Font(Font.SANS_SERIF, Font.BOLD, 12);
        }
    }

    private BufferedImage createPlaceholder() {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 64, 64);
        g.dispose();
        return image;
    }
}
