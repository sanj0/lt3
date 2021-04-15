package de.sanj0.chessian.move;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.utils.BoardUtils;

// a move that only stores start and end index
// a more elaborate implementation is needed for
// undoing and redoing moves
public class Move {

    protected int start;
    protected int end;

    public Move(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    // fancifies this move
    public FancyMove fancify(final byte[] board) {
        return new FancyMove(start, end, board[end]);
    }

    public boolean isPromotion(final Board board) {
        final byte me = board.get(start);
        final int promotionRank = Pieces.color(me) == Pieces.LIGHT ? 7 : 0;
        return Pieces.isPawn(me) && BoardUtils.rank(end) == promotionRank;
    }

    public int rating(final Board board) {
        final byte capture = board.get(end);
        final int promotionBonus = isPromotion(board) ?
                Pieces.valueForRating(Pieces.QUEEN) - Pieces.valueForRating(Pieces.PAWN) : 0;
        if (capture == Pieces.NONE) {
            return ratingByPosition(board) + promotionBonus;
        } else {
            return Pieces.valueForRating(capture) + ratingByPosition(board) + promotionBonus;
        }
    }

    private int ratingByPosition(final Board board) {
        // if the piece is developed for the first time
        // it's a plus
        final byte me = board.get(start);
        final int targetFile = BoardUtils.file(end);
        final byte myColor = Pieces.color(me);
        int centrePawnBlock = Pieces.isPawn(me) ? 0 : centrePawnBlock(board, targetFile, myColor);
        int developmentBonus = 0;

        // only development!
        if (BoardUtils.startingPositions(me).contains(start)) {
            if (Pieces.type(me) == Pieces.QUEEN) {
                return Math.max(1, BoardUtils.distanceFromCentre(end)) - 1;
            } else if (Pieces.type(me) == Pieces.PAWN) {
                // better to develop centre pawns
                return Math.min(ratePawnAdvance(board), Pieces.valueForRating(Pieces.PAWN) - 1);
            } else if (Pieces.type(me) == Pieces.KING) {
                // don't develop the king
                // - especially not to the centre of the board
                return -2 + BoardUtils.distanceFromCentre(end);
            } else if (Pieces.type(me) == Pieces.ROOK){
                return BoardUtils.endgame(board) > .2 ? 0 : -1;
            } else {
                return Math.max(2, 4 - BoardUtils.distanceFromCentre(end));
            }
        } else {
            if (!Pieces.isKing(me) || BoardUtils.endgame(board) > .65) {
                return 3 - BoardUtils.distanceFromCentre(end) + centrePawnBlock;
            }
        }
        return developmentBonus + centrePawnBlock;
    }

    private int centrePawnBlock(final Board board, final int targetFile, final byte myColor) {
        final int centrePawnBlockTax = -2;

        if (myColor == Pieces.LIGHT) {
            if (checkFileAndPawn(board, targetFile, 3, 51)
                    || checkFileAndPawn(board, targetFile, 4, 52)) {
                return centrePawnBlockTax;
            }
        } else {
            if (checkFileAndPawn(board, targetFile, 3, 11)
                    || checkFileAndPawn(board, targetFile, 4, 12)) {
                return centrePawnBlockTax;
            }
        }
        return 0;
    }

    private boolean checkFileAndPawn(final Board board, final int targetFile, final int expectedFile, final int pawnPosition) {
        return targetFile == expectedFile && Pieces.isPawn(board.get(pawnPosition));
    }

    private int ratePawnAdvance(final Board board) {
        final int myPosition = start;
        final boolean isCentrePawn = myPosition == 11 || myPosition == 12
                || myPosition == 51 || myPosition == 52;
        int centreModifier = isCentrePawn ? 2 : 1;
        int doubleAdvanceCentreModifier = isCentrePawn && Math.abs(start - end) == 16
                ? 2 : 0;
        double endgame = BoardUtils.endgame(board);
        // we can return the full value of a pawn because it is going to be minned to val - 1 later anyway
        int endgameModifier = endgame > .35 ? (endgame > .65 ? Pieces.valueForRating(Pieces.PAWN) : 0) : -1;

        return centreModifier + endgameModifier + doubleAdvanceCentreModifier;
    }

    public boolean isPawnDoubleAdvance(final Board board) {
        return Pieces.isPawn(board.get(start)) && Math.abs(start - end) == 16;
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

    public String extendedNotation(final Board board) {
        final byte capture = board.get(end);
        final byte me = board.get(start);
        final char myLetter = Pieces.letter(me);

        if (Pieces.isPawn(me)) {
            if (capture != Pieces.NONE) {
                return BoardUtils.fileName(start) + "x" + BoardUtils.squareName(end);
            } else {
                return BoardUtils.squareName(end);
            }
        } else {
            return myLetter + BoardUtils.squareName(start)
                    + (capture != Pieces.NONE ? "x" : "") + BoardUtils.squareName(end);
        }
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
