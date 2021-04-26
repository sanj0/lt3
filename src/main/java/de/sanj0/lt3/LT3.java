package de.sanj0.lt3;

import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;
import de.sanj0.lt3.openings.Opening;
import de.sanj0.lt3.openings.OpeningsManager;
import de.sanj0.lt3.utils.LT3Utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

// huzza - a program that plays chess? Has the world ever seen something like that
// before? I doubt it...
public class LT3 {

    private static int DEPTH = 3;

    private static final Random RNG = new SecureRandom();
    private static final OpeningsManager openings = OpeningsManager.parseDefaultOpenings();

    public static Move bestMove(final Board board, final byte colorToMove) {
        final List<Opening> availableOpenings = openings.availableOpenings(board);

        if (!board.isCustomPosition() && !availableOpenings.isEmpty()) {
            final Opening opening = availableOpenings.get(RNG.nextInt(availableOpenings.size()));
            System.out.println("    we could be playing the " + opening.getName());
            return opening.getMoves().get(board.getMoveHistory().size());
        }

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
                System.out.println("    " + m.extendedNotation(board) + " > rated " + rating);
                if (rating > maxRating) {
                    maxRating = rating;
                    bestMoves = new ArrayList<>(candidates.size());
                    bestMoves.add(m);
                } else if (rating == maxRating) {
                    bestMoves.add(m);
                }
            }
            System.out.println("    choosing a from " + bestMoves.size() + " best moves...");
            System.out.println("    ---");
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
        final byte enemyColor = Pieces.oppositeColor(color);
        final Map<Move, List<Move>> responseResponses = MoveGenerator.withoutIllegalMoves(plResponses, responses, enemyColor, after);

        if (responses.isEmpty()) {
            return Integer.MAX_VALUE;
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

        return LT3Utils.safeSubtract(rating, bestResponseRating);
    }
}
