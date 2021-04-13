package de.sanj0.chessian.openings;

import de.sanj0.chessian.move.Move;

import java.util.List;

public class Opening {

    private String name;
    private List<Move> moves;

    public Opening(final String name, final List<Move> moves) {
        this.name = name;
        this.moves = moves;
    }

    /**
     * Does this Opening sequence start
     * with the given moves (and has at least 1
     * additional move)?
     *
     * @param openingMoves a move sequence
     * @return true if the given move sequence is smaller in size than the sequence
     * described by this Opening and is equal to the start of it
     */
    public boolean startsWith(final List<Move> openingMoves) {
        int givenSize = openingMoves.size();
        int ourSize = moves.size();

        if (givenSize >= ourSize) return false;
        for(int i = 0; i < givenSize; i++) {
            if (!openingMoves.get(i).equals(moves.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets {@link #name}.
     *
     * @return the value of {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets {@link #name}.
     *
     * @param name the new value of {@link #name}
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets {@link #moves}.
     *
     * @return the value of {@link #moves}
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Sets {@link #moves}.
     *
     * @param moves the new value of {@link #moves}
     */
    public void setMoves(final List<Move> moves) {
        this.moves = moves;
    }
}
