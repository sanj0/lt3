package de.sanj0.lt3.engine;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.EvaluationBar;
import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;
import de.sanj0.lt3.openings.Opening;
import de.sanj0.lt3.openings.OpeningsManager;
import de.sanj0.lt3.utils.BoardUtils;
import de.sanj0.lt3.utils.LT3Utils;

import java.security.SecureRandom;
import java.util.*;

// huzza - a program that plays chess? Has the world ever seen something like that
// before? I doubt it...
public class LT3 {

    public static int DEPTH = 5;

    public static int lastBestMoveRating = 0;

    private static final Random RNG = new SecureRandom();
    private static final OpeningsManager openings = OpeningsManager.parseDefaultOpenings();

    public static Move bestMove(final Board board, final byte colorToMove, final boolean playOpening) {
        if (playOpening) {
            final List<Opening> availableOpenings = openings.availableOpenings(board);

            if (!board.isCustomPosition() && !availableOpenings.isEmpty()) {
                final Opening opening = availableOpenings.get(RNG.nextInt(availableOpenings.size()));
                System.out.println("    we could be playing the " + opening.getName());
                return opening.getMoves().get(board.getMoveHistory().size());
            }
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
                    if (Math.abs(LT3Utils.safeSubtract(Integer.MAX_VALUE, rating)) <= 200) {
                        System.err.println("    forced check mate after " + m.extendedNotation(board));
                    }
                    maxRating = rating;
                    bestMoves = new ArrayList<>(candidates.size());
                    bestMoves.add(m);
                } else if (rating == maxRating) {
                    bestMoves.add(m);
                }
            }
            lastBestMoveRating = maxRating;
            System.out.println("    choosing from " + bestMoves.size() + " best moves rated " + maxRating + "...");
            System.out.println("    ---");

            return bestMoves.get(RNG.nextInt(bestMoves.size()));
        }
    }

    private static int rateMove(final Move m, final Board board, final byte color, final int depth, final List<Move> plResponses) {
        int rating = m.rating(board, BoardUtils.endgame(board));
        if (depth == 1) {
            return rating;
        }
        final Board after = board.afterMove(m);
        List<Move> responses = new ArrayList<>(plResponses != null ? plResponses.size() : 0);
        final byte enemyColor = Pieces.oppositeColor(color);
        Map<Move, List<Move>> responseResponses = new HashMap<>();
        if (DEPTH > 2 && depth != 2) {
            // massive performance safer to just take pseudo-legal
            // moves in the deepest depth
            responseResponses = MoveGenerator.withoutIllegalMoves(plResponses, responses, enemyColor, after);
        } else {
            responses = MoveGenerator.generateAllPLMoves(after, enemyColor);
        }

        if (responses.isEmpty()) {
            final double eval = board.rateBoard();
            // because we're talking about check(mate), pseudo legal moves are of matter
            final List<Move> wouldBeNextMoves = MoveGenerator.generateAllPLMoves(after, color);
            final byte enemyKing = Pieces.get(Pieces.KING, enemyColor);
            for (final Move move : wouldBeNextMoves) {
                if (board.get(move.getEnd()) == enemyKing) {
                    // actually mate
                    return Integer.MAX_VALUE - ((DEPTH - depth) * 100);
                }
            }
            // stalemate
            if (color == Pieces.LIGHT) {
                if (eval > 0) {
                    return Integer.MIN_VALUE / 2;
                } else {
                    return Integer.MAX_VALUE / 2 - ((DEPTH - depth) * 100);
                }
            } else {
                if (eval < 0) {
                    return Integer.MIN_VALUE / 2;
                } else {
                    return Integer.MAX_VALUE / 2 - ((DEPTH - depth) * 100);
                }
            }
        }

        int bestResponseRating = Integer.MIN_VALUE;
        final int rSize = responses.size();
        final int depthMinusOne = depth - 1;
        for (int i = 0; i < rSize; i++) {
            final Move response = responses.get(i);
            final int responseRating = rateMove(response, after, enemyColor, depthMinusOne, responseResponses.getOrDefault(response, null));
            if (responseRating > bestResponseRating) {
                bestResponseRating = responseRating;
            }
        }

        return LT3Utils.safeSubtract(rating, bestResponseRating);
    }

    public static int rateMove(final Move m, final Board board, final int depth) {
        final byte color = board.get(m.getStart());
        return rateMove(m, board, color, depth,
                MoveGenerator.generateAllPLMoves(board.afterMove(m), Pieces.oppositeColor(color)));
    }
}
