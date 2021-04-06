package de.sanj0.chessian.utils;

import de.sanj0.chessian.Pieces;
import de.sanj0.chessian.move.Move;

import java.util.ArrayList;
import java.util.List;

// helps with generating rook moves - how nice!
public class RookMovesHelper {

    //FIXME: review and rewrite
    public static List<Move> generatePseudoLegal(final byte[] board, final int origin, final byte myColor) {
        final List<Move> moves = new ArrayList<>();
        final int rank = origin / 8;
        // the index at which the rank starts
        final int rankMin = rank * 8;
        // the index at which the rank end
        final int rankMax = rankMin + 7;
        final int file = origin - rank * 8;
        final int fileMin = file;
        final int fileMax = 56 + file;

        addLine(moves, rankMin, rankMax, origin, board, -1, 1, myColor);
        addLine(moves, fileMin, fileMax, origin, board, -8, 8, myColor);
        return moves;
    }

    private static void addLine(final List<Move> moves, final int min, final int max,
                                final int origin, final byte[] board, final int offset1,
                                final int offset2, final byte myColor) {
        // always iterate 7 times
        int squareIndex = origin + offset1;
        // start with all squares on the left
        int offsetForNextSquare = offset1;
        for (int i = 0; i < 8; i++) {
            if (squareIndex < 0 || squareIndex > 63) {
                if (offsetForNextSquare == offset2) break;
                else {
                    offsetForNextSquare = offset2;
                    squareIndex = origin + offset2;
                }
            }
            if (squareIndex < min) {
                offsetForNextSquare = offset2;
                squareIndex = origin + offset2;
                continue;
            } else if (squareIndex > max) {
                // all horizontal moves are done
                break;
            }
            final byte piece = board[squareIndex];
            if (piece != Pieces.NONE) {
                if (Pieces.color(piece) != myColor) {
                    moves.add(new Move(origin, squareIndex));
                }
                if (offsetForNextSquare == offset1) {
                    offsetForNextSquare = offset2;
                    squareIndex = origin + offset2;
                    continue;
                } else {
                    break;
                }
            }
            moves.add(new Move(origin, squareIndex));
            squareIndex += offsetForNextSquare;
        }
    }
}
