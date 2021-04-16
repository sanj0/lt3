package de.sanj0.lt3.input;

import de.edgelord.saltyengine.input.KeyboardInputAdapter;
import de.sanj0.lt3.Board;
import de.sanj0.lt3.ChessScene;
import de.sanj0.lt3.LT3;
import de.sanj0.lt3.Main;
import de.sanj0.lt3.move.Move;

import java.awt.event.KeyEvent;

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
            final Move response = LT3.bestMove(board, owner.getBoardRenderer().getMoveState().getColorToMove());
            board.doMove(response);
            owner.getBoardRenderer().getMoveState().nextTurn();
        } else if (e.getKeyChar() == 'a') {
            owner.setAutoMove(!owner.isAutoMove());
        }
    }
}
