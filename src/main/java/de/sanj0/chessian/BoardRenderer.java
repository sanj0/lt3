package de.sanj0.chessian;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.graphics.image.SaltyBufferedImage;
import de.edgelord.saltyengine.graphics.image.SaltyImage;
import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.transform.Dimensions;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.chessian.utils.BoardUtils;

import java.awt.*;

// renders the board and the pieces
public class BoardRenderer extends DrawingRoutine {

    public static Color LIGHT_COLOR = new Color(198, 215, 240);
    public static Color DARK_COLOR = new Color(58, 80, 118);

    public static final Dimensions SQUARE_SIZE = new Dimensions(Main.GAME_WIDTH / 8f, Main.GAME_HEIGHT / 8f);
    public static final Dimensions SQUARE_SIZE_DIV2 = new Dimensions(Main.GAME_WIDTH / 16f, Main.GAME_HEIGHT / 16f);
    public static final Vector2f boardOrigin = Vector2f.zero();

    private SaltyImage boardImage;
    private final Board board;
    private final MoveState moveState;

    public BoardRenderer(final Board board) {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);
        this.board = board;
        this.moveState = new MoveState();

        boardImage = new SaltyBufferedImage((int) Game.getGameWidth(), (int) Game.getGameHeight());
        renderBoardImage();
    }

    @Override
    public void draw(final SaltyGraphics g) {
        g.drawImage(boardImage, boardOrigin);

        final byte[] position = board.getData();
        float x = boardOrigin.getX();
        float y = boardOrigin.getY();
        float width = SQUARE_SIZE.getWidth();
        float height = SQUARE_SIZE.getHeight();
        for (int i = 0; i < position.length; i++) {
            final byte piece = position[i];

            // last move

            // skipp dragged piece
            if (i != moveState.getDraggedPieceIndex()) {
                PieceRenderer.drawPiece(g.copy(), piece, new Transform(x, y, width, height));
            }

            // update x and y
            if ((i + 1) % 8 == 0) {
                x = boardOrigin.getX();
                y += height;
            } else {
                x += width;
            }
        }

        // draw marks and arrows
        // and dragged piece
        if (moveState.getDraggedPieceIndex() != -1) {
            final Vector2f cursor = Input.getCursorPosition();
            final Vector2f centre = cursor.subtracted(SQUARE_SIZE_DIV2.toVector2f());
            PieceRenderer.drawPiece(g.copy(), position[moveState.getDraggedPieceIndex()], new Transform(centre, SQUARE_SIZE));
        }
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

    /**
     * Gets {@link #moveState}.
     *
     * @return the value of {@link #moveState}
     */
    public MoveState getMoveState() {
        return moveState;
    }
}
