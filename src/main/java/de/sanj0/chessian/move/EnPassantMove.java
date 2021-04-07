package de.sanj0.chessian.move;

// en croissant???
public class EnPassantMove extends Move {

    private final int takenPawn;
    public EnPassantMove(final int start, final int end, final int takenPawn) {
        super(start, end);

        this.takenPawn = takenPawn;
    }

    /**
     * Gets {@link #takenPawn}.
     *
     * @return the value of {@link #takenPawn}
     */
    public int getTakenPawn() {
        return takenPawn;
    }
}
