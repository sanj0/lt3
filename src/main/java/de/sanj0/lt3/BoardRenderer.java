package de.sanj0.lt3;

import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.graphics.image.SaltyBufferedImage;
import de.edgelord.saltyengine.graphics.image.SaltyImage;
import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.transform.Dimensions;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.utils.BoardUtils;
import de.sanj0.lt3.utils.LT3Utils;

import java.awt.*;

// renders the board and the pieces
public class BoardRenderer extends DrawingRoutine {

    public static Color LIGHT_COLOR = new Color(198, 215, 240);
    public static Color DARK_COLOR = new Color(58, 80, 118);
    public static Color LEGAL_MOVES_COLOR = new Color(252, 77, 108, 127);
    public static Color LAST_MOVE_COLOR = new Color(66, 134, 56, 127);

    public static final Dimensions SQUARE_SIZE = new Dimensions(100, 100);
    public static final Dimensions SQUARE_SIZE_DIV2 = new Dimensions(50, 50);
    private static final float EN_PASSANT_INDICATOR_SIZE = Main.GAME_WIDTH / 32f;
    public static final Vector2f boardOrigin = new Vector2f(50, 0);
    public static final Transform boardTransform = new Transform(boardOrigin, new Dimensions(SQUARE_SIZE).multiply(8, 8));

    // FIXME: file and rank indicators invert with the board
    private SaltyImage boardImage;
    private Board board;
    private final PlayerMoveState playerMoveState;

    public BoardRenderer(final Board board) {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);
        this.board = board;
        this.playerMoveState = new PlayerMoveState(board.getColorToStart());

        boardImage = new SaltyBufferedImage(boardTransform.getWidthAsInt(), boardTransform.getHeightAsInt());
        renderBoardImage();
    }

    @Override
    public void draw(final SaltyGraphics g) {
        if (playerMoveState.isBoardInverted()) {
            g.setRotation(180, boardTransform.getCentre());
        }
        g.drawImage(boardImage, boardOrigin);

        // draw square marks

        g.setColor(LEGAL_MOVES_COLOR);
        for (final Move legalMove : playerMoveState.getLegalMoves()) {
            final int dst = legalMove.getEnd();
            drawSquare(dst, g);
        }

        Move lastMove;
        if ((lastMove = board.getLastMove()) != null) {
            g.setColor(LAST_MOVE_COLOR);
            drawSquare(lastMove.getStart(), g);
            drawSquare(lastMove.getEnd(), g);
        }

        // draw en passant
        int enPassant;
        if ((enPassant = board.getEnPassant()) != -1) {
            g.setColor(LEGAL_MOVES_COLOR);
            final int file = enPassant / 8;
            final int rank = enPassant - file * 8;
            g.drawOval(boardOrigin.getX() + rank * SQUARE_SIZE.getWidth() + EN_PASSANT_INDICATOR_SIZE * 1.5f,
                    boardOrigin.getY() + file * SQUARE_SIZE.getHeight() + EN_PASSANT_INDICATOR_SIZE * 1.5f,
                    EN_PASSANT_INDICATOR_SIZE, EN_PASSANT_INDICATOR_SIZE);
        }

        final byte[] position = board.getData();
        float x = boardOrigin.getX();
        float y = boardOrigin.getY();
        float width = SQUARE_SIZE.getWidth();
        float height = SQUARE_SIZE.getHeight();
        for (int i = 0; i < position.length; i++) {
            final byte piece = position[i];
            // skipp dragged piece
            if (i != playerMoveState.getDraggedPieceIndex()) {
                PieceRenderer.drawPiece(g.copy(), piece, new Transform(x, y, width, height), playerMoveState.isBoardInverted());
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
        if (playerMoveState.getDraggedPieceIndex() != -1) {
            final Vector2f cursor = Input.getCursorPosition();
            Vector2f centre = cursor.subtracted(SQUARE_SIZE_DIV2.toVector2f());
            if (playerMoveState.isBoardInverted()) {
                centre.add(SQUARE_SIZE.toVector2f());
                centre = LT3Utils.rotatePoint(centre, Math.PI, boardTransform.getCentre());
            }
            PieceRenderer.drawPiece(g.copy(), position[playerMoveState.getDraggedPieceIndex()], new Transform(centre, SQUARE_SIZE), playerMoveState.isBoardInverted());
        }
    }

    private void drawSquare(final int square, final SaltyGraphics g) {
        final int file = square / 8;
        final int rank = square - file * 8;
        g.drawRect(boardOrigin.getX() + rank * SQUARE_SIZE.getWidth(),
                boardOrigin.getY() + file * SQUARE_SIZE.getHeight(),
                SQUARE_SIZE.getWidth(), SQUARE_SIZE.getHeight());
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
     * Sets {@link #board}.
     *
     * @param board the new value of {@link #board}
     */
    public void setBoard(final Board board) {
        this.board = board;
    }

    /**
     * Gets {@link #playerMoveState}.
     *
     * @return the value of {@link #playerMoveState}
     */
    public PlayerMoveState getMoveState() {
        return playerMoveState;
    }
}
