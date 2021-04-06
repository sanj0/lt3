package de.sanj0.chessian.move;

// a move that could be undone and redone - how fancy!
public class FancyMove extends Move {

    private final byte capturedPiece;

    public FancyMove(final int start, final int end, final byte capturedPiece) {
        super(start, end);
        this.capturedPiece = capturedPiece;
    }

    /**
     * Gets {@link #capturedPiece}.
     *
     * @return the value of {@link #capturedPiece}
     */
    public byte getCapturedPiece() {
        return capturedPiece;
    }
}
