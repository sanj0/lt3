package de.sanj0.chessian;

import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Vector2f;

import java.awt.event.MouseEvent;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MouseInput extends MouseInputAdapter {

    private final ChessScene owner;

    public MouseInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        final MoveState moveState = owner.getBoardRenderer().getMoveState();
        moveState.setDraggedPieceIndex(MoveState.hoveredSquare(cursor));
        // replace with legal move generation
        moveState.setLegalDestinationSquares(IntStream.range(0, 64).boxed().collect(Collectors.toList()));
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        final MoveState moveState = owner.getBoardRenderer().getMoveState();
        final int destination = MoveState.hoveredSquare(cursor);
        if (moveState.getDraggedPieceIndex() != destination &&
                moveState.getLegalDestinationSquares().contains(destination)) {
            // do the move!
            // for now, simply replace piece values
            owner.getBoard().set(destination, owner.getBoard().get(moveState.getDraggedPieceIndex()));
            owner.getBoard().set(moveState.getDraggedPieceIndex(), Pieces.NONE);
        }

        owner.getBoardRenderer().getMoveState().setDraggedPieceIndex(-1);
    }

    private Vector2f cursorPosition(final MouseEvent e) {
        return new Vector2f(e.getX(), e.getY());
    }
}
