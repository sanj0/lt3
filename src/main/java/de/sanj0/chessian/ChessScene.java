package de.sanj0.chessian;

import de.edgelord.saltyengine.scene.Scene;

// the main scene in which to play chess
public class ChessScene extends Scene {

    private final BoardRenderer boardRenderer;

    public ChessScene() {
        boardRenderer = new BoardRenderer();
    }

    @Override
    public void initialize() {
        addDrawingRoutine(boardRenderer);
    }
}
