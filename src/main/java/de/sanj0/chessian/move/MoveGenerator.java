package de.sanj0.chessian.move;

import de.sanj0.chessian.Board;
import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.utils.BishopMovesHelper;
import de.sanj0.chessian.utils.BoardUtils;
import de.sanj0.chessian.utils.PawnMovesHelper;
import de.sanj0.chessian.utils.RookMovesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.sanj0.chessian.Pieces.*;

// generates moves!
public class MoveGenerator {

    public static List<Move> generateAllLegalMoves(final Board board, final byte color) {
        final List<Move> moves = new ArrayList<>(40);
        final byte[] data = board.getData();

        for (int i = 0; i < data.length; i++) {
            byte piece = data[i];
            if (Pieces.color(piece) == color) {
                moves.addAll(generateLegalMoves(i, board));
            }
        }

        return moves;
    }

    // not to be used in bulks, only for user moves as generated
    // responses are wasted!
    // refer to method withoutIllegalMoves for further info
    public static List<Move> generateLegalMoves(final int myIndex, final Board board) {
        final List<Move> plMoves = generatePseudoLegalMoves(myIndex, board);
        final List<Move> legalMoves = new ArrayList<>(plMoves.size());
        withoutIllegalMoves(plMoves, legalMoves, myIndex, board);

        return legalMoves;
    }

    /**
     * Filters out the moves that would result
     * in a self check and are thus illegal and
     * adds every move that doesn't (i.e. the legal moves)
     * in the given List.
     * Returns a list of all possible, pseudo legal responses to every
     * of the given moves because they are generated to check
     * legality anyway.
     *
     * @param plMoves the list of pseudo legal moves
     * @param legalMoves the list to store the legal moves in - should be initialized
     *                   with the same size as {@code plMoves}
     * @param myIndex the index of the piece the moves are from
     * @param board the board
     * @return a list of all possible responses to every given pseudo-legal move as
     * they are needed to be generated for filtering anyway
     */
    public static Map<Move, List<Move>> withoutIllegalMoves(final List<Move> plMoves, final List<Move> legalMoves, final int myIndex, final Board board) {
        final int plMovesSize = plMoves.size();
        final Map<Move, List<Move>> plResponses = new HashMap<>(plMovesSize);

        final byte myColor = color(board.get(myIndex));
        final byte enemyColor = Pieces.oppositeColor(myColor);
        final int kingBefore = BoardUtils.kingPosition(board, myColor);

        for (int i = 0; i < plMovesSize; i++) {
            final Move m = plMoves.get(i);
            final byte movedPiece = board.get(m.getStart());
            final int king = Pieces.isKing(movedPiece) ? m.getEnd() : kingBefore;
            final Board now = board.afterMove(m);
            final List<Move> plR = generateAllPLMoves(now, enemyColor);
            final int plRSize = plR.size();

            boolean legal = true;
            for (int ii = 0; ii < plRSize; ii++) {
                final Move r = plR.get(ii);
                if (r.getEnd() == king) {
                    legal = false;
                    break;
                }
            }
            if (legal) {
                legalMoves.add(m);
            }
            plResponses.put(m, plR);
        }

        return plResponses;
    }

    public static List<Move> generateAllPLMoves(final Board board, final byte color) {
        final List<Move> moves = new ArrayList<>(40);
        final byte[] data = board.getData();

        for (int i = 0; i < data.length; i++) {
            byte piece = data[i];
            if (Pieces.color(piece) == color) {
                moves.addAll(generatePseudoLegalMoves(i, board));
            }
        }

        return moves;
    }

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
