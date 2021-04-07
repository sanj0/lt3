package de.sanj0.chessian.move;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.utils.BoardUtils;

import java.util.Objects;

// a move that only stores start and end index
// a more elaborate implementation is needed for
// undoing and redoing moves
public class Move {

    private final int start;
    private final int end;

    public Move(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    // fancifies this move
    public FancyMove fancify(final byte[] board) {
        return new FancyMove(start, end, board[end]);
    }

    public int rating(final Board board) {
        final byte capture = board.get(end);
        if (capture == Pieces.NONE) {
            return ratingByPosition(board);
        } else {
            return Pieces.valueForRating(capture) + ratingByPosition(board);
        }
    }

    private int ratingByPosition(final Board board) {
        // if the piece is developed for the first time
        // it's a plus
        final byte me = board.get(start);
        if (BoardUtils.startingPositions(me).contains(start)) {
            if (Pieces.type(me) == Pieces.QUEEN) {
                return Math.min(2, 4 - BoardUtils.distanceFromCentre(end)) - 1;
            } else if (Pieces.type(me) == Pieces.PAWN) {
                // better to develop centre pawns
                return ratePawnAdvance(board);
            } else if (Pieces.type(me) == Pieces.KING) {
                // don't develop the king
                // - especially not to the centre of the board
                return -2 + BoardUtils.distanceFromCentre(end);
            } else if (Pieces.type(me) == Pieces.ROOK){
                return BoardUtils.endgame(board) > .2 ? 0 : -1;
            } else {
                return Math.max(2, 4 - BoardUtils.distanceFromCentre(end));
            }
        }
        return 0;
    }

    private int ratePawnAdvance(final Board board) {
        final int file = BoardUtils.file(start);
        int centreModifier = file > 1 && file < 7 ? (file > 2 && file < 6 ? 2 : 1) : 0;
        int doubleAdvanceCentreModifier = centreModifier > 0 && Math.abs(start - end) == 16
                ? 2 : 0;
        double endgame = BoardUtils.endgame(board);
        int endgameModifier = endgame > .5 ? (endgame > .65 ? 5 : 0) : -1;

        return centreModifier + endgameModifier + doubleAdvanceCentreModifier;
    }

    public static Move empty() {
        return new Move(0, 0);
    }

    public boolean isEmpty() {
        return start == 0 && end == 0;
    }

    /**
     * Gets {@link #start}.
     *
     * @return the value of {@link #start}
     */
    public int getStart() {
        return start;
    }

    /**
     * Gets {@link #end}.
     *
     * @return the value of {@link #end}
     */
    public int getEnd() {
        return end;
    }

    public String notation() {
        // maybe insert x for takes - would require a board as an argument
        return BoardUtils.squareName(start) + BoardUtils.squareName(end);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return start == move.start && end == move.end;
    }

    @Override
    public int hashCode() {
        return start * 31 + end;
    }
}
