package de.sanj0.chessian.move;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.utils.BoardUtils;

import java.util.Objects;

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

    @Override
    public String notation() {
        return BoardUtils.fileName(start) + "x" + BoardUtils.squareName(end) + " e.p.";
    }

    @Override
    public String extendedNotation(final Board board) {
        return notation();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnPassantMove that = (EnPassantMove) o;
        return takenPawn == that.takenPawn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), takenPawn);
    }
}
