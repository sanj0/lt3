package de.sanj0.lt3;

import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;
import de.sanj0.lt3.openings.Opening;
import de.sanj0.lt3.openings.OpeningsManager;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class MinimaxLT3 {

    public static final int DEPTH = 3;

    private static final Random RNG = new SecureRandom();
    private static final OpeningsManager openings = OpeningsManager.parseDefaultOpenings();

    public static Move getBestMove(final Board board, final PlayerMoveState moveState) {
        final List<Opening> availableOpenings = openings.availableOpenings(board);

        if (!board.isCustomPosition() && !availableOpenings.isEmpty()) {
            final Opening opening = availableOpenings.get(RNG.nextInt(availableOpenings.size()));
            System.out.println("    we could be playing the " + opening.getName());
            return opening.getMoves().get(board.getMoveHistory().size());
        }

        final Move move = minimaxRoot(DEPTH, board, moveState, true);
        if (move.isEmpty()) {
            System.out.println((moveState.getColorToMove() == Pieces.DARK ? "light" : "dark") + " won the game!");
        }

        return move;
    }

    private static Move minimaxRoot(final int depth, final Board board, final PlayerMoveState mState, final boolean isMaximisingPlayer) {
        final List<Move> moves = MoveGenerator.generateAllLegalMoves(board, mState.getColorToMove());

        double bestMoveRating = Integer.MIN_VALUE;
        Move bestMove = Move.empty();

        for (final Move m : moves) {
            final Board after = board.afterMove(m);
            final double rating = miniMax(depth, after, Pieces.oppositeColor(mState.getColorToMove()), Integer.MIN_VALUE, Integer.MAX_VALUE, !isMaximisingPlayer);
            System.out.println("    " + m.extendedNotation(board) + " is rated " + rating);
            if (rating >= bestMoveRating) {
                bestMoveRating = rating;
                bestMove = m;
            }
        }

        return bestMove;
    }

    private static double miniMax(final int depth, final Board board, final byte colorToMove,
                               double alpha, double beta, final boolean isMaximisingPlayer) {
        final double rating = board.rateBoard();

        if (depth == 0) {
            return -rating;
        }

        final List<Move> moves = MoveGenerator.generateAllLegalMoves(board, colorToMove);

        double bestMoveRating;
        if (isMaximisingPlayer) {
            bestMoveRating = Integer.MIN_VALUE;
            for (final Move m : moves) {
                bestMoveRating = Math.max(bestMoveRating, miniMax(depth - 1, board.afterMove(m), Pieces.oppositeColor(colorToMove), alpha, beta, !isMaximisingPlayer));
                alpha = Math.max(alpha, bestMoveRating);
                if (beta <= alpha) {
                    return bestMoveRating;
                }
            }
        } else {
            bestMoveRating = Integer.MAX_VALUE;
            for (final Move m : moves) {
                bestMoveRating = Math.min(bestMoveRating, miniMax(depth - 1, board.afterMove(m), Pieces.oppositeColor(colorToMove), alpha, beta, !isMaximisingPlayer));
                beta = Math.min(beta, bestMoveRating);
                if (beta <= alpha) {
                    return bestMoveRating;
                }
            }
        }
        return bestMoveRating;
    }
}
