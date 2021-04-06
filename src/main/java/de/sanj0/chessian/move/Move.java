package de.sanj0.chessian.move;

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
}
