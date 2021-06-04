package de.sanj0.lt3.input;

import de.edgelord.saltyengine.input.KeyboardInputAdapter;
import de.sanj0.lt3.Board;
import de.sanj0.lt3.ChessScene;
import de.sanj0.lt3.EvaluationBar;
import de.sanj0.lt3.engine.LT3;
import de.sanj0.lt3.Main;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            if (buttonDown == KeyEvent.VK_LEFT) {
                if (owner.getBoard().undo()) {
                    owner.getBoardRenderer().getMoveState().nextTurn();
                }
            } else if (buttonDown == KeyEvent.VK_RIGHT) {
                if (owner.getBoard().redo()) {
                    owner.getBoardRenderer().getMoveState().nextTurn();
                }
            }
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
            EvaluationBar.EVAL = 0;
        } else if (e.getKeyChar() == 'm') {
            // make the AI make the next move
            final Move response = LT3.bestMove(board, owner.getBoardRenderer().getMoveState().getColorToMove(), true);
            board.doMove(response);
            owner.getBoardRenderer().getMoveState().nextTurn();
        } else if (e.getKeyChar() == 'a') {
            owner.setAutoMove(!owner.isAutoMove());
        } else if (e.getKeyChar() == 'i') {
            owner.getBoardRenderer().getMoveState().setBoardInverted(!owner.getBoardRenderer().getMoveState().isBoardInverted());
        } else if (e.getKeyChar() == 'n') {
            // reset position
            owner.getBoardRenderer().getMoveState().setColorToMove(owner.getBoard().getColorToStart());
            final String word = JOptionPane.showInputDialog("enter a word/name");
            CompletableFuture.runAsync(() -> {
                for (final char c : word.toCharArray()) {
                    final List<Move> moves = MoveGenerator.generateAllLegalMoves(owner.getBoard(), owner.getBoardRenderer().getMoveState().getColorToMove());
                    owner.getBoardRenderer().getMoveState().nextTurn();
                    owner.getBoard().doMove(moves.get(c % moves.size()));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            });
        } else if (e.getKeyChar() == 'l') {
            LT3.DEPTH--;
        } else if (e.getKeyChar() == 'h') {
            LT3.DEPTH++;
        } else if (e.getKeyChar() == 'e') {
            System.out.print("evaluating the board...");
            final int depthBefore = LT3.DEPTH;
            LT3.DEPTH--;
            EvaluationBar.EVAL = 0;
            LT3.bestMove(owner.getBoard(), owner.getBoardRenderer().getMoveState().getColorToMove(), false);
            EvaluationBar.appendMoveRating(LT3.lastBestMoveRating, owner.getBoardRenderer().getMoveState().getColorToMove());
            EvaluationBar.grayedOut = false;
            LT3.DEPTH = depthBefore;
            System.out.println(" done!");
        }
    }
}
