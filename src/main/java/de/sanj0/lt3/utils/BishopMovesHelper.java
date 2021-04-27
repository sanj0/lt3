package de.sanj0.lt3.utils;

import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.move.Move;

import java.util.*;

// helps with bishop moves - how nice!
public class BishopMovesHelper {

    public static final int LIST_INIT_CAPACITY = 13;

    public static List<Move> generatePseudoLegalMoves(final List<Move> mList, final byte[] board, final int origin, final byte myColor) {
        final int originX = BoardUtils.file(origin);
        final int originY = BoardUtils.rank(origin);
        singleRay(mList, board, myColor, origin, originX, originY, 1, 1);
        singleRay(mList, board, myColor, origin, originX, originY, 1, -1);
        singleRay(mList, board, myColor, origin, originX, originY, -1, 1);
        singleRay(mList, board, myColor, origin, originX, originY, -1, -1);

        return mList;
    }

    private static void singleRay(final List<Move> mList, final byte[] board, final byte myColor, final int origin, final int originX, final int originY, final int dx, final int dy) {
        for (int i = 1;;i++) {
            final int x = originX + dx * i;
            final int y = originY + dy * i;

            if (x < 8 && x >= 0 && y < 8 && y >= 0) {
                final int s = BoardUtils.indexFromPosition(x, y);
                final byte p = board[s];
                if (p == Pieces.NONE) {
                    mList.add(new Move(origin, s));
                } else if (Pieces.color(p) == myColor) {
                    return;
                } else {
                    mList.add(new Move(origin, s));
                    return;
                }
            } else {
                return;
            }
        }
    }
}
