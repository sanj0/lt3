package de.sanj0.chessian;

import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.chessian.move.MoveGenerator;
import de.sanj0.chessian.utils.ChessianUtils;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseInput extends MouseInputAdapter {

    private final ChessScene owner;

    public MouseInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        final PlayerMoveState playerMoveState = owner.getBoardRenderer().getMoveState();
        final int piece = PlayerMoveState.hoveredSquare(cursor);

        if (Pieces.color(owner.getBoard().get(piece)) == playerMoveState.getColorToMove()) {
            playerMoveState.setDraggedPieceIndex(piece);
            // replace with legal move generation
            playerMoveState.setLegalDestinationSquares(
                    ChessianUtils.movesToDestinationList(MoveGenerator.generatePseudoLegalMoves(piece, owner.getBoard())));
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        final Vector2f cursor = cursorPosition(e);
        final PlayerMoveState playerMoveState = owner.getBoardRenderer().getMoveState();
        final int destination = PlayerMoveState.hoveredSquare(cursor);
        if (playerMoveState.getDraggedPieceIndex() != destination &&
                playerMoveState.getLegalDestinationSquares().contains(destination)) {
            // do the move!
            // for now, simply replace piece values
            owner.getBoard().set(destination, owner.getBoard().get(playerMoveState.getDraggedPieceIndex()));
            owner.getBoard().set(playerMoveState.getDraggedPieceIndex(), Pieces.NONE);
            playerMoveState.nextTurn();
        }

        owner.getBoardRenderer().getMoveState().setDraggedPieceIndex(-1);
        owner.getBoardRenderer().getMoveState().setLegalDestinationSquares(new ArrayList<>());
    }

    private Vector2f cursorPosition(final MouseEvent e) {
        return new Vector2f(e.getX(), e.getY());
    }
}
