package de.sanj0.chessian;

import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.scene.Scene;
import de.sanj0.chessian.input.KeyboardInput;
import de.sanj0.chessian.input.MouseInput;

// the main scene in which to play chess
public class ChessScene extends Scene {

    private Board board;
    private final BoardRenderer boardRenderer;
    private boolean autoMove = true;

    public ChessScene(final Board board) {
        this.board = board;
        boardRenderer = new BoardRenderer(board);

        System.out.println(board);
    }

    @Override
    public void initialize() {
        Input.addMouseInputHandler(new MouseInput(this));
        Input.addKeyboardInputHandler(new KeyboardInput(this));
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
     * Sets {@link #board}.
     *
     * @param board the new value of {@link #board}
     */
    public void setBoard(final Board board) {
        this.board = board;
        boardRenderer.setBoard(board);
    }

    /**
     * Gets {@link #boardRenderer}.
     *
     * @return the value of {@link #boardRenderer}
     */
    public BoardRenderer getBoardRenderer() {
        return boardRenderer;
    }

    /**
     * Gets {@link #autoMove}.
     *
     * @return the value of {@link #autoMove}
     */
    public boolean isAutoMove() {
        return autoMove;
    }

    /**
     * Sets {@link #autoMove}.
     *
     * @param autoMove the new value of {@link #autoMove}
     */
    public void setAutoMove(final boolean autoMove) {
        this.autoMove = autoMove;
    }
}
