package de.sanj0.chessian.move;

import de.sanj0.chessian.utils.CastleHelper;

// a castle move!
public class CastleMove extends Move {

    private Move rookMove;
    private final CastleHelper.Castle castle;

    public CastleMove(final CastleHelper.Castle castle) {
        super(-1, -1);
        this.castle = castle;

        switch (castle) {
            case KING_SIDE_LIGHT:
                start = 60;
                end = 62;
                rookMove = new Move(63, 61);
                break;
            case QUEEN_SIDE_LIGHT:
                start = 60;
                end = 58;
                rookMove = new Move(56, 59);
                break;
            case KING_SIDE_DARK:
                start = 4;
                end = 6;
                rookMove = new Move(7, 5);
                break;
            case QUEEN_SIDE_DARK:
                start = 4;
                end = 2;
                rookMove = new Move(0, 3);
                break;
        }
    }

    /**
     * Gets {@link #rookMove}.
     *
     * @return the value of {@link #rookMove}
     */
    public Move getRookMove() {
        return rookMove;
    }

    @Override
    public String notation() {
        return castle == CastleHelper.Castle.KING_SIDE_LIGHT || castle == CastleHelper.Castle.KING_SIDE_DARK
                ? "O-O" : "O-O-O";
    }
}
