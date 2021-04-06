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
    private final Board board;

    public BoardRenderer(final Board board) {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);
        this.board = board;

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
        boolean drawRankOnNextSquare = true;
        g.setFont(g.getFont().deriveFont(15f).deriveFont(Font.BOLD));
        for (int i = 0; i < 64; i++) {
            g.setColor(isLight ? LIGHT_COLOR : DARK_COLOR);
            g.drawRect(x, y, width, height);

            if (drawRankOnNextSquare) {
                g.setColor(isLight ? DARK_COLOR : LIGHT_COLOR);
                g.drawText(BoardUtils.rank(i) + 1, x, y, SaltyGraphics.TextAnchor.TOP_LEFT_CORNER);
                drawRankOnNextSquare = false;
            }
            if (i >= 56) {
                g.setColor(isLight ? DARK_COLOR : LIGHT_COLOR);
                g.drawText(BoardUtils.fileName(i), x + width, y + width, SaltyGraphics.TextAnchor.BOTTOM_RIGHT_CORNER);
            }

            if ((i + 1) % 8 == 0) {
                drawRankOnNextSquare = true;
                x = 0;
                y += height;
            } else {
                x += width;
                isLight = !isLight;
            }
        }
    }

    /**
     * Gets {@link #board}.
     *
     * @return the value of {@link #board}
     */
    public Board getBoard() {
        return board;
    }
}
