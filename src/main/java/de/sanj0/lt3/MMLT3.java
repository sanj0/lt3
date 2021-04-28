package de.sanj0.lt3;

import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;
import de.sanj0.lt3.openings.Opening;
import de.sanj0.lt3.openings.OpeningsManager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MMLT3 {

    private static final int DEPTH = 5;

    private static final Random RNG = new SecureRandom();
    private static final OpeningsManager openings = OpeningsManager.parseDefaultOpenings();

    public static Move bestMove(final Board board, final byte colorToMove) {
        final List<Opening> availableOpenings = openings.availableOpenings(board);

        if (!board.isCustomPosition() && !availableOpenings.isEmpty()) {
            final Opening opening = availableOpenings.get(RNG.nextInt(availableOpenings.size()));
            System.out.println("    we could be playing the " + opening.getName());
            return opening.getMoves().get(board.getMoveHistory().size());
        }
        final boolean maximising = colorToMove == Pieces.LIGHT;
        final byte enemyColor = Pieces.oppositeColor(colorToMove);
        final List<Move> candidates = MoveGenerator.generateAllLegalMoves(board, colorToMove);

        if (candidates.isEmpty()) {
            System.out.println((colorToMove == Pieces.DARK ? "white" : "black") + " won the game!");
            return Move.empty();
        } else {
            double maxRating = Integer.MIN_VALUE;
            List<Move> bestMoves = new ArrayList<>(candidates.size());
            for (final Move m : candidates) {
                final double rating = rateMM(m, board, maximising,
                        Integer.MIN_VALUE, Integer.MAX_VALUE,
                        colorToMove, DEPTH, MoveGenerator.generateAllPLMoves(board.afterMove(m), enemyColor));
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

    private static double rateMM(final Move m, final Board board, final boolean maximising,
                                    double alpha, double beta,
                                    final byte color, final int depth, final List<Move> plResponses) {
        final Board after = board.afterMove(m);
        double rating = after.rateBoard();
        if (depth == 1) {
            return rating;
        }

        final List<Move> responses = new ArrayList<>(plResponses.size());
        final byte enemyColor = Pieces.oppositeColor(color);
        final Map<Move, List<Move>> responseResponses = MoveGenerator.withoutIllegalMoves(plResponses, responses, enemyColor, after);

        if (responses.isEmpty()) {
            return maximising ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        final int depthMinus1 = depth - 1;

        if (maximising) {
            double max = Integer.MIN_VALUE;
            for (final Move r : responses) {
                double rRating = rateMM(r, after, false, alpha, beta, enemyColor, depthMinus1, responseResponses.get(r));
                max = Math.max(max, rRating);
                alpha = Math.max(alpha, rRating);
                if (beta <= alpha) {
                    break;
                }
            }
            return max;
        } else {
            double min = Integer.MAX_VALUE;
            for (final Move r : responses) {
                double rRating = rateMM(r, after, true, alpha, beta, enemyColor, depthMinus1, responseResponses.get(r));
                min = Math.min(min, rRating);
                beta = Math.min(beta, rRating);
                if (beta <= alpha) {
                    break;
                }
            }
            return min;
        }
    }
}
