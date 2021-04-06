package de.sanj0.chessian;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.graphics.image.SaltyBufferedImage;
import de.edgelord.saltyengine.graphics.image.SaltyImage;
import de.edgelord.saltyengine.transform.Dimensions;
import de.edgelord.saltyengine.transform.Vector2f;

import java.awt.*;

// renders the board and the pieces
public class BoardRenderer extends DrawingRoutine {

    public static Color LIGHT_COLOR = new Color(98, 111, 123);
    public static Color DARK_COLOR = new Color(16, 28, 43);
    public static final Dimensions SQUARE_SIZE = new Dimensions(Main.GAME_WIDTH / 8f, Main.GAME_HEIGHT / 8f);
    public static final Vector2f boardOrigin = Vector2f.zero();

    private SaltyImage boardImage;

    public BoardRenderer() {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);

        boardImage = new SaltyBufferedImage((int) Game.getGameWidth(), (int) Game.getGameHeight());
        renderBoardImage();
    }

    @Override
    public void draw(final SaltyGraphics saltyGraphics) {
        saltyGraphics.drawImage(boardImage, boardOrigin);
    }

    private void renderBoardImage() {
        final SaltyGraphics g = boardImage.getGraphics();
        float x = 0;
        float y = 0;
        float width = SQUARE_SIZE.getWidth();
        float height = SQUARE_SIZE.getHeight();
        boolean isLight = true;
        for (int i = 0; i < 64; i++) {
            g.setColor(isLight ? LIGHT_COLOR : DARK_COLOR);
            g.drawRect(x, y, width, height);

            if ((i + 1) % 8 == 0) {
                x = 0;
                y += height;
            } else {
                x += width;
                isLight = !isLight;
            }
        }
    }
}
