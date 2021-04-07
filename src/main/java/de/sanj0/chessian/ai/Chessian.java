package de.sanj0.chessian.ai;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.move.Move;
import de.sanj0.chessian.move.MoveGenerator;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// huzza - a program that plays chess? Has the world ever seen something like that
// before? I doubt it...
public class Chessian {

    private static final Random RNG = new SecureRandom();
    private static ChessianMoveRater.BestMoves bestOfFirstHalf;
    private static ChessianMoveRater.BestMoves bestOfSecondHalf;

    public static Move bestMove(final Board board, final byte colorToMove) throws ExecutionException, InterruptedException {
        final List<Move> candidates = MoveGenerator.generateAllLegalMoves(board, colorToMove);
        final int numCandidates = candidates.size();

        if (candidates.isEmpty()) {
            System.out.println((colorToMove == Pieces.DARK ? "white" : "black") + " won the game!");
            return Move.empty();
        } else {
            // WARNING: ugly af code
            final CompletableFuture<Void> bm1 = CompletableFuture.runAsync(() -> bestOfFirstHalf = ChessianMoveRater.highestRatedMoves(candidates, 0, numCandidates / 2, board, colorToMove));
            final CompletableFuture<Void> bm2 = CompletableFuture.runAsync(() -> bestOfSecondHalf = ChessianMoveRater.highestRatedMoves(candidates, numCandidates / 2, numCandidates, board, colorToMove));
            bm1.get();
            bm2.get();
            final List<Move> bestMoves = ChessianMoveRater.bestFromBiResults(bestOfFirstHalf, bestOfSecondHalf);
            return bestMoves.get(RNG.nextInt(bestMoves.size()));
        }
    }
}
