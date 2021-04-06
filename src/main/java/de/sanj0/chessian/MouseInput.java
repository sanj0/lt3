package de.sanj0.chessian;

import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Vector2f;

import java.awt.event.MouseEvent;

public class MouseInput extends MouseInputAdapter {

    private final ChessScene owner;

    public MouseInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        owner.getBoardRenderer().getMoveState().setDraggedPieceIndex(MoveState.hoveredSquare(cursor));
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        owner.getBoardRenderer().getMoveState().setDraggedPieceIndex(-1);
    }

    private Vector2f cursorPosition(final MouseEvent e) {
        return new Vector2f(e.getX(), e.getY());
    }
}
