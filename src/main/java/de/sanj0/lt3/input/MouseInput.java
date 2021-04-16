package de.sanj0.lt3.input;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.displaymanager.stage.Stage;
import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.GeneralUtil;
import de.sanj0.lt3.ChessScene;
import de.sanj0.lt3.LT3;
import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.PlayerMoveState;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;
import de.sanj0.lt3.utils.LT3Utils;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MouseInput extends MouseInputAdapter {

    private final ChessScene owner;
    private final Stage stage = Game.getHostAsDisplayManager().getStage();
    private boolean lt3Working = false;

    public MouseInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (!lt3Working) {
            final Vector2f cursor = cursorPosition(e);
            final PlayerMoveState playerMoveState = owner.getBoardRenderer().getMoveState();
            final int piece = PlayerMoveState.hoveredSquare(cursor);

            if (Pieces.color(owner.getBoard().get(piece)) == playerMoveState.getColorToMove()) {
                playerMoveState.setDraggedPieceIndex(piece);
                // replace with legal move generation
                playerMoveState.setLegalMoves(MoveGenerator.generateLegalMoves(piece, owner.getBoard()));
            }
        }
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        if (!lt3Working) {
            final Vector2f cursor = cursorPosition(e);
            final PlayerMoveState playerMoveState = owner.getBoardRenderer().getMoveState();
            final int destination = PlayerMoveState.hoveredSquare(cursor);
            Move m;
            if (playerMoveState.getDraggedPieceIndex() != destination &&
                    (m = LT3Utils.getMoveByDestination(playerMoveState.getLegalMoves(), destination)) != null) {
                // do the move!
                // for now, simply replace piece values
                owner.getBoard().doMove(m);
                playerMoveState.nextTurn();
                if (owner.isAutoMove()) {
                    CompletableFuture.runAsync(() -> {
                        lt3Working = true;
                        final Move response = LT3.bestMove(owner.getBoard(), playerMoveState.getColorToMove());
                        owner.getBoard().doMove(response);
                        playerMoveState.nextTurn();
                        lt3Working = false;
                    });
                }
            }

            owner.getBoardRenderer().getMoveState().setDraggedPieceIndex(-1);
            owner.getBoardRenderer().getMoveState().setLegalMoves(new ArrayList<>());
        }
    }

    private Vector2f cursorPosition(final MouseEvent e) {
        final Vector2f cursorPos = new Vector2f(e.getX(), e.getY());
        final Vector2f imagePos = stage.getImagePosition();
        final float currentScale = stage.getCurrentScale();
        cursorPos.subtract(imagePos.getX(), imagePos.getY());
        cursorPos.divide(currentScale, currentScale);
        cursorPos.setX(GeneralUtil.clamp(cursorPos.getX(), 0, Game.getGameWidth()));
        cursorPos.setY(GeneralUtil.clamp(cursorPos.getY(), 0, Game.getGameHeight()));

        return cursorPos;
    }
}
