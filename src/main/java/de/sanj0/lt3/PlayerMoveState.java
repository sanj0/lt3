package de.sanj0.lt3;

import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.lt3.move.Move;

import java.util.ArrayList;
import java.util.List;

// states of user input to handle piece dragging
public class PlayerMoveState {
    private int draggedPieceIndex = -1;
    private List<Move> legalMoves = new ArrayList<>();
    private byte colorToMove;
    private boolean boardInverted = false;

    public PlayerMoveState(final byte colorToMove) {
        this.colorToMove = colorToMove;
    }

    public void nextTurn() {
        colorToMove = Pieces.oppositeColor(colorToMove);
    }

    /**
     * Returns the square hovered over by the given point
     * using the square size from {@link BoardRenderer#SQUARE_SIZE}
     *
     * @param point a point
     * @return the index of the square hovered over by the given point
     */
    public static int hoveredSquare(final Vector2f point) {
        final int file = (int) Math.floor(point.getX() / BoardRenderer.SQUARE_SIZE.getWidth());
        final int rank = (int) Math.floor(point.getY() / BoardRenderer.SQUARE_SIZE.getHeight());
        return rank * 8 + file;
    }

    /**
     * Gets {@link #draggedPieceIndex}.
     *
     * @return the value of {@link #draggedPieceIndex}
     */
    public int getDraggedPieceIndex() {
        return draggedPieceIndex;
    }

    /**
     * Sets {@link #draggedPieceIndex}.
     *
     * @param draggedPieceIndex the new value of {@link #draggedPieceIndex}
     */
    public void setDraggedPieceIndex(final int draggedPieceIndex) {
        this.draggedPieceIndex = draggedPieceIndex;
    }

    /**
     * Gets {@link #legalMoves}.
     *
     * @return the value of {@link #legalMoves}
     */
    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    /**
     * Sets {@link #legalMoves}.
     *
     * @param legalMoves the new value of {@link #legalMoves}
     */
    public void setLegalMoves(final List<Move> legalMoves) {
        this.legalMoves = legalMoves;
    }

    /**
     * Gets {@link #colorToMove}.
     *
     * @return the value of {@link #colorToMove}
     */
    public byte getColorToMove() {
        return colorToMove;
    }

    /**
     * Sets {@link #colorToMove}.
     *
     * @param colorToMove the new value of {@link #colorToMove}
     */
    public void setColorToMove(final byte colorToMove) {
        this.colorToMove = colorToMove;
    }

    /**
     * Gets {@link #boardInverted}.
     *
     * @return the value of {@link #boardInverted}
     */
    public boolean isBoardInverted() {
        return boardInverted;
    }

    /**
     * Sets {@link #boardInverted}.
     *
     * @param boardInverted the new value of {@link #boardInverted}
     */
    public void setBoardInverted(final boolean boardInverted) {
        this.boardInverted = boardInverted;
    }
}
