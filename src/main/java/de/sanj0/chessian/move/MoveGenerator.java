package de.sanj0.chessian.move;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.utils.BishopMovesHelper;
import de.sanj0.chessian.utils.PawnMovesHelper;
import de.sanj0.chessian.utils.RookMovesHelper;

import java.util.ArrayList;
import java.util.List;

import static de.sanj0.chessian.Pieces.*;

// generates moves!
public class MoveGenerator {

    // moves that could be illegal due to check
    public static List<Move> generatePseudoLegalMoves(final int myIndex, final Board board) {
        final byte me = board.get(myIndex);
        final byte myType = type(me);
        final byte myColor = color(me);

        switch (myType) {
            case PAWN:
                return generatePLPawnMoves(myIndex, board, myColor);
            case KNIGHT:
                return generatePLKnightMoves(myIndex, board, myColor);
            case BISHOP:
                return generatePLBishopMoves(myIndex, board, myColor);
            case ROOK:
                return generatePLRookMoves(myIndex, board, myColor);
            case QUEEN:
                return generatePLQueenMoves(myIndex, board, myColor);
            case KING:
                return generatePLKingMoves(myIndex, board, myColor);
            default:
                // it's most likely an empty piece - should never happen but
                // would not be fatal enough to be worth an exception
                return new ArrayList<>();
        }
    }

    private static List<Move> toMoveListWithoutSelfCaptures(final List<Integer> unfiltered, final Board board, final int me, final byte myColor) {
        final int size = unfiltered.size();
        final List<Move> moves = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final int m = unfiltered.get(i);
            if (color(board.get(m)) != myColor) {
                moves.add(new Move(me, m));
            }
        }

        return moves;
    }

    private static List<Move> generatePLKnightMoves(final int myIndex, final Board board, final byte myColor) {
        return toMoveListWithoutSelfCaptures(PreBakedMoveData.preBakedPLKnightDestinations.get(myIndex), board, myIndex, myColor);
    }

    private static List<Move> generatePLBishopMoves(final int myIndex, final Board board, final byte myColor) {
        return BishopMovesHelper.withoutBlockedSquares(PreBakedMoveData.preBakedPLBishopDestinations.get(myIndex), board.getData(), myIndex, myColor);
    }

    private static List<Move> generatePLKingMoves(final int myIndex, final Board board, final byte myColor) {
        return toMoveListWithoutSelfCaptures(PreBakedMoveData.preBakedPLKingDestinations.get(myIndex), board, myIndex, myColor);
    }

    private static List<Move> generatePLRookMoves(final int myIndex, final Board board, final byte myColor) {
        return RookMovesHelper.generatePseudoLegal(board.getData(), myIndex, myColor);
    }

    private static List<Move> generatePLPawnMoves(final int myIndex, final Board board, final byte myColor) {
        return PawnMovesHelper.pseudoLegalMoves(board.getData(), myIndex, myColor);
    }

    private static List<Move> generatePLQueenMoves(final int myIndex, final Board board, final byte myColor) {
        // normally I would create a separate function but inlining this list merge is not
        // complicated enough to not make it worth the performance increase
        final List<Move> rookMoves = generatePLRookMoves(myIndex, board, myColor);
        final List<Move> bishopMoves = generatePLBishopMoves(myIndex, board, myColor);
        final List<Move> combined = new ArrayList<>(rookMoves.size() + bishopMoves.size());
        combined.addAll(rookMoves);
        combined.addAll(bishopMoves);
        return combined;
    }
}
