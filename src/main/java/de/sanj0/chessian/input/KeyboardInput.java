package de.sanj0.chessian.input;

import de.edgelord.saltyengine.input.KeyboardInputAdapter;
import de.sanj0.chessian.Board;
import de.sanj0.chessian.ChessScene;
import de.sanj0.chessian.ai.Chessian;
import de.sanj0.chessian.Main;
import de.sanj0.chessian.move.Move;

import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutionException;

public class KeyboardInput extends KeyboardInputAdapter {

    private final ChessScene owner;
    private int buttonDown = -1;

    public KeyboardInput(final ChessScene owner) {
        this.owner = owner;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        buttonDown = e.getKeyCode();
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        // "key typed" but for non-alphanumeric keys
        if (buttonDown == e.getKeyCode()) {
            buttonDown = -1;
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        final Board board = owner.getBoard();
        if (e.getKeyChar() == 'r') {
            // reset position
            owner.setBoard(Board.fromFEN(Main.INIT_FEN));
            owner.getBoardRenderer().getMoveState().setColorToMove(owner.getBoard().getColorToStart());
        } else if (e.getKeyChar() == 'm') {
            // make the AI make the next move
            Move response = null;
            try {
                response = Chessian.bestMove(board, owner.getBoardRenderer().getMoveState().getColorToMove());
            } catch (ExecutionException | InterruptedException executionException) {
                executionException.printStackTrace();
            }
            board.doMove(response);
            owner.getBoardRenderer().getMoveState().nextTurn();
        } else if (e.getKeyChar() == 'a') {
            owner.setAutoMove(!owner.isAutoMove());
        }
    }
}
