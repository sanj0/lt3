package de.sanj0.chessian;

import de.edgelord.saltyengine.transform.Vector2f;

import java.util.ArrayList;
import java.util.List;

// states of user input to handle piece dragging
public class MoveState {
    private int draggedPieceIndex = -1;
    private List<Integer> legalDestinationSquares = new ArrayList<>();

    public MoveState() {
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
     * Gets {@link #legalDestinationSquares}.
     *
     * @return the value of {@link #legalDestinationSquares}
     */
    public List<Integer> getLegalDestinationSquares() {
        return legalDestinationSquares;
    }

    /**
     * Sets {@link #legalDestinationSquares}.
     *
     * @param legalDestinationSquares the new value of {@link #legalDestinationSquares}
     */
    public void setLegalDestinationSquares(final List<Integer> legalDestinationSquares) {
        this.legalDestinationSquares = legalDestinationSquares;
    }
}
