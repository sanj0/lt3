package de.sanj0.chessian;

import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.scene.Scene;

// the main scene in which to play chess
public class ChessScene extends Scene {

    private final Board board;
    private final BoardRenderer boardRenderer;

    public ChessScene(final Board board) {
        this.board = board;
        boardRenderer = new BoardRenderer(board);

        System.out.println(board);
    }

    @Override
    public void initialize() {
        Input.addMouseInputHandler(new MouseInput(this));
        addDrawingRoutine(boardRenderer);
    }

    /**
     * Gets {@link #board}.
     *
     * @return the value of {@link #board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets {@link #boardRenderer}.
     *
     * @return the value of {@link #boardRenderer}
     */
    public BoardRenderer getBoardRenderer() {
        return boardRenderer;
    }
}
