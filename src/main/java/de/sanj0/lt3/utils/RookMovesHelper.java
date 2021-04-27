package de.sanj0.lt3.utils;

import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.move.Move;

import java.util.List;

// helps with generating rook moves - how nice!
public class RookMovesHelper {

    public static int LIST_INIT_CAPACITY = 14;

    public static int RAY_OFFSET_LEFT = -1;
    public static int RAY_OFFSET_RIGHT = +1;
    public static int RAY_OFFSET_UP = -8;
    public static int RAY_OFFSET_DOWN = +8;


    //FIXME: review and rewrite
    public static List<Move> generatePseudoLegal(final List<Move> moves, final byte[] board, final int origin, final byte myColor) {
        final int rank = origin / 8;
        // the index at which the rank starts
        final int rankMin = rank * 8;
        // the index at which the rank end
        final int rankMax = rankMin + 7;
        final int file = origin - rank * 8;
        final int fileMax = 56 + file;

        singleRay(moves, board, origin, rankMin, rankMax, myColor, RAY_OFFSET_LEFT);
        singleRay(moves, board, origin, rankMin, rankMax, myColor, RAY_OFFSET_RIGHT);
        singleRay(moves, board, origin, file, fileMax, myColor, RAY_OFFSET_DOWN);
        singleRay(moves, board, origin, file, fileMax, myColor, RAY_OFFSET_UP);
        return moves;
    }

    public static void singleRay(final List<Move> mList, final byte[] board, final int origin, final int min, final int max, final byte myColor, final int rayOffset) {
        int i = origin + rayOffset;
        while (i <= max && i >= min) {
            final byte tSquarePiece = board[i];
            if (tSquarePiece == Pieces.NONE) {
                mList.add(new Move(origin, i));
            } else if (Pieces.color(tSquarePiece) == myColor) {
                break;
            } else {
                mList.add(new Move(origin, i));
                break;
            }
            i += rayOffset;
        }
    }
}
