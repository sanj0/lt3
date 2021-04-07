package de.sanj0.chessian;

import de.sanj0.chessian.move.Move;
import de.sanj0.chessian.move.MoveGenerator;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

// huzza - a program that plays chess? Has the world ever seen something like that
// before? I doubt it...
public class Chessian {

    private static int DEPTH = 3;

    private static final Random RNG = new SecureRandom();

    public static Move bestMove(final Board board, final byte colorToMove) {
        final List<Move> candidates = MoveGenerator.generateAllLegalMoves(board, colorToMove);
        final byte enemyColor = Pieces.oppositeColor(colorToMove);

        if (candidates.isEmpty()) {
            System.out.println((colorToMove == Pieces.DARK ? "white" : "black") + " won the game!");
            return Move.empty();
        } else {
            int maxRating = Integer.MIN_VALUE;
            List<Move> bestMoves = new ArrayList<>(candidates.size());
            for (final Move m : candidates) {
                final int rating = rateMove(m, board, colorToMove, DEPTH, MoveGenerator.generateAllPLMoves(board.afterMove(m), enemyColor));
                System.out.println(m.notation() + " > rated " + rating);
                if (rating > maxRating) {
                    maxRating = rating;
                    bestMoves = new ArrayList<>(candidates.size());
                    bestMoves.add(m);
                } else if (rating == maxRating) {
                    bestMoves.add(m);
                }
            }
            System.out.println("choosing a random move out of " + bestMoves.size() + " best moves...");
            System.out.println("---");
            return bestMoves.get(RNG.nextInt(bestMoves.size()));
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
}
