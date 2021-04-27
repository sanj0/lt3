package de.sanj0.lt3.utils;

import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.move.Move;

import java.util.ArrayList;
import java.util.List;

// helps with generating pawn moves - how nice
public class PawnMovesHelper {

    public static List<Move> pseudoLegalMoves(final byte[] board, final int position, final byte myColor) {
        final List<Move> moves = new ArrayList<>(5);
        boolean isSingleAdvancePossible;
        if (myColor == Pieces.DARK) {
            isSingleAdvancePossible = board[position + 8] == Pieces.NONE;
            // normal +1 extend if the square is empty and in bounds
            addIfEmptyAndInBounds(moves, board, position, position + 8);
            // capture left if not on a rank, opposite color piece and in bounds
            if (position % 8 != 0) {
                addIfColorAndInBounds(moves, board, position, position + 7, Pieces.LIGHT);
            }
            // capture right if not on a rank, opposite color piece and in bounds
            if ((position + 1) % 8 != 0) {
                addIfColorAndInBounds(moves, board, position, position + 9, Pieces.LIGHT);
            }
            // long advance from starting position
            if (position >= 8 && position <= 15 && isSingleAdvancePossible) {
                addIfEmptyAndInBounds(moves, board, position, position + 16);
            }
        } else if (myColor == Pieces.LIGHT) {
            isSingleAdvancePossible = board[position - 8] == Pieces.NONE;
            // normal +1 extend if the square is empty and in bounds
            addIfEmptyAndInBounds(moves, board, position, position - 8);
            // capture left if not on a rank, opposite color piece and in bounds
            if (position % 8 != 0) {
                addIfColorAndInBounds(moves, board, position, position - 9, Pieces.DARK);
            }
            // capture right if not on a rank, opposite color piece and in bounds
            if ((position + 1) % 8 != 0) {
                addIfColorAndInBounds(moves, board, position, position - 7, Pieces.DARK);
            }
            // long advance from starting position
            if (position >= 48 && position <= 55 && isSingleAdvancePossible) {
                addIfEmptyAndInBounds(moves, board, position, position - 16);
            }
        }

        return moves;
    }

    private static void addIfEmptyAndInBounds(final List<Move> moves, final byte[] board, final int start, final int dst) {
        if (dst <= 63 && dst >= 0 && board[dst] == Pieces.NONE) {
            moves.add(new Move(start, dst));
        }
    }

    private static void addIfColorAndInBounds(final List<Move> moves, final byte[] board, final int start, final int dst, final byte color) {
        if (dst <= 63 && dst >= 0 && Pieces.color(board[dst]) == color) {
            moves.add(new Move(start, dst));
        }
    }
}
