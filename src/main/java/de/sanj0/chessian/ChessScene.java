package de.sanj0.chessian;

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
        addDrawingRoutine(boardRenderer);
    }
}
