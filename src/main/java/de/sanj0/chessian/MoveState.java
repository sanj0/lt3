package de.sanj0.chessian;

import de.edgelord.saltyengine.transform.Vector2f;

// states of user input to handle piece dragging
public class MoveState {
    private int draggedPieceIndex = -1;

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
}
