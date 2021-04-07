package de.sanj0.chessian.ai;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.move.Move;
import de.sanj0.chessian.move.MoveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// rates moves for Chessian
public class ChessianMoveRater {

    public static int DEPTH = 4;

    public static BestMoves highestRatedMoves(final List<Move> candidates, final int fromIndex, final int toIndexExclusive, final Board board, final byte colorToMove) {
        final byte enemyColor = Pieces.oppositeColor(colorToMove);

        int maxRating = Integer.MIN_VALUE;
        List<Move> bestMoves = new ArrayList<>(candidates.size());

        for (int i = fromIndex; i < toIndexExclusive; i++) {
            final Move m = candidates.get(i);
            final int rating = rateMove(m, board, colorToMove, DEPTH, MoveGenerator.generateAllPLMoves(board.afterMove(m), enemyColor));
            System.out.println(m.notation() + " > rating = " + rating);
            if (rating > maxRating) {
                maxRating = rating;
                bestMoves = new ArrayList<>(candidates.size());
                bestMoves.add(m);
            } else if (rating == maxRating) {
                bestMoves.add(m);
            }
        }

        return new BestMoves(bestMoves, maxRating);
    }

    public static List<Move> bestFromBiResults(final BestMoves bm1, final BestMoves bm2) {
        if (bm1.rating > bm2.rating) {
            return bm1.bestMoves;
        } else if (bm1.rating < bm2.rating) {
            return bm2.bestMoves;
        } else {
            final List<Move> all = new ArrayList<>(bm1.bestMoves.size() + bm2.bestMoves.size());
            all.addAll(bm1.bestMoves);
            all.addAll(bm2.bestMoves);
            return all;
        }
    }

    private static int rateMove(final Move m, final Board board, final byte color, final int depth, final List<Move> plResponses) {
        int rating = m.rating(board);
        if (depth == 0) {
            return rating;
        }
        final Board after = board.afterMove(m);
        final List<Move> responses = new ArrayList<>(plResponses.size());
        final Map<Move, List<Move>> responseResponses = MoveGenerator.withoutIllegalMoves(plResponses, responses, m.getStart(), after);
        final byte enemyColor = Pieces.oppositeColor(color);

        if (responses.isEmpty()) {
            if ((DEPTH - depth) % 2 == 1) {
                // we would get mated
                return Integer.MIN_VALUE + 1;
            } else {
                // stalemate - make bad if we aren't behind
                // we can calculate that using material odds only
                // but it won't be the "right" decision all the time
                return Integer.MAX_VALUE;
            }
        }

        int bestResponseRating = Integer.MIN_VALUE;
        final int rSize = responses.size();
        final int depthMinusOne = depth - 1;
        for (int i = 0; i < rSize; i++) {
            final Move response = responses.get(i);
            final int responseRating = rateMove(response, after, enemyColor, depthMinusOne, responseResponses.get(response));
            if (responseRating > bestResponseRating) {
                bestResponseRating = responseRating;
            }
        }

        return rating - bestResponseRating;
    }

    public static class BestMoves {
        private List<Move> bestMoves;
        private int rating;

        public BestMoves(final List<Move> bestMoves, final int rating) {
            this.bestMoves = bestMoves;
            this.rating = rating;
        }

        /**
         * Gets {@link #bestMoves}.
         *
         * @return the value of {@link #bestMoves}
         */
        public List<Move> getBestMoves() {
            return bestMoves;
        }

        /**
         * Sets {@link #bestMoves}.
         *
         * @param bestMoves the new value of {@link #bestMoves}
         */
        public void setBestMoves(final List<Move> bestMoves) {
            this.bestMoves = bestMoves;
        }

        /**
         * Gets {@link #rating}.
         *
         * @return the value of {@link #rating}
         */
        public int getRating() {
            return rating;
        }

        /**
         * Sets {@link #rating}.
         *
         * @param rating the new value of {@link #rating}
         */
        public void setRating(final int rating) {
            this.rating = rating;
        }
    }
}
