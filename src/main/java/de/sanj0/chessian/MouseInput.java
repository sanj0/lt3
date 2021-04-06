package de.sanj0.chessian;

import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.chessian.move.MoveGenerator;
import de.sanj0.chessian.utils.ChessianUtils;

import java.awt.event.MouseEvent;

public class MouseInput extends MouseInputAdapter {

    private final ChessScene owner;

    public MouseInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        final MoveState moveState = owner.getBoardRenderer().getMoveState();
        final int piece = MoveState.hoveredSquare(cursor);
        moveState.setDraggedPieceIndex(piece);
        // replace with legal move generation
        moveState.setLegalDestinationSquares(
                ChessianUtils.movesToDestinationList(MoveGenerator.generatePseudoLegalMoves(piece, owner.getBoard())));
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
