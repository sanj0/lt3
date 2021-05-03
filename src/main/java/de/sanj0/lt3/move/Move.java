package de.sanj0.lt3.move;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.utils.BoardEvaluationHelper;
import de.sanj0.lt3.utils.BoardUtils;
import de.sanj0.lt3.utils.LT3Utils;

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
    public FancyMove fancify(final Board board) {
        return new FancyMove(start, end, board.get(end), board.getEnPassant(), LT3Utils.copyCastleRightsMap(board.getAllowedCastles()));
    }

    public boolean isPromotion(final Board board) {
        final byte me = board.get(start);
        final int promotionRank = Pieces.color(me) == Pieces.LIGHT ? 7 : 0;
        return Pieces.isPawn(me) && BoardUtils.rank(end) == promotionRank;
    }

    public int rating(final Board board, final double endgame) {
        final byte capture = board.get(end);
        final int promotionBonus = isPromotion(board) ?
                Pieces.value(Pieces.QUEEN) : 0;
        if (capture == Pieces.NONE) {
            return ratingByPosition(board, endgame) + promotionBonus;
        } else {
            return Pieces.value(capture) + ratingByPosition(board, endgame) + promotionBonus;
        }
    }

    private int ratingByPosition(final Board board, final double endgame) {
        // if the piece is developed for the first time
        // it's a plus
        final byte me = board.get(start);
        final boolean isPawn = Pieces.isPawn(me);
        final int targetFile = BoardUtils.file(end);
        final byte myColor = Pieces.color(me);
        int centrePawnBlock = isPawn ? 0 : centrePawnBlock(board, targetFile, myColor);
        int developmentBonus;

        if (BoardUtils.startingPositions(me).contains(start)) {
            developmentBonus = (int) Math.round(2.5 * (BoardEvaluationHelper.ratePiecePositionNeutral(me, end) - BoardEvaluationHelper.ratePiecePositionNeutral(me, start)));
        } else {
            final double eval = board.rateBoard();
            final boolean winning = myColor == Pieces.LIGHT ? eval > 0 : eval < 0;
            if (endgame > .4) {
                if (isPawn) {
                    return 5;
                } else if (Pieces.isKing(me)) {
                    if (winning) {
                        final int enemyKing = BoardUtils.kingPosition(board, Pieces.oppositeColor(myColor));
                        return Math.abs(enemyKing - start) > Math.abs(enemyKing - end) ? 2 : -2;
                    } else {
                        return 5 - BoardUtils.distanceFromCentre(end);
                    }
                }
            }
            developmentBonus = (int) Math.round(2 * (BoardEvaluationHelper.ratePiecePositionNeutral(me, end) - BoardEvaluationHelper.ratePiecePositionNeutral(me, start)));
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
