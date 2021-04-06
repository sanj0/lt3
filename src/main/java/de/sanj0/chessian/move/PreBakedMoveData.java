package de.sanj0.chessian.move;

import de.sanj0.chessian.utils.ChessianUtils;

import java.util.*;

// contains pre baked move data
// for different pieces
public class PreBakedMoveData {

    protected static final int[][] KNIGHT_MOVES_UPPER_HALF = {
            {0, 10, 17},
            {1, 11, 16, 18},
            {2, 8, 12, 17, 19},
            {3, 9, 13, 18, 20},
            {4, 10, 14, 19, 21},
            {5, 11, 15, 20, 22},
            {6, 12, 21, 23},
            {7, 13, 22},
            {8, 2, 18, 25},
            {9, 3, 19, 24, 26},
            {10, 0, 4, 16, 20, 25, 27},
            {11, 1, 5, 17, 21, 26, 28},
            {12, 2, 6, 18, 22, 27, 29},
            {13, 3, 7, 19, 23, 28, 30},
            {14, 4, 20, 31},
            {15, 5, 21, 30},
            {16, 1, 10, 26, 33},
            {17, 0, 2, 11, 27, 32, 34},
            {18, 3, 8, 12, 24, 28, 33, 35},
            {19, 2, 4, 9, 13, 25, 29, 34, 36},
            {20, 3, 5, 10, 14, 26, 30, 35, 37},
            {21, 4, 6, 11, 15, 27, 31, 36, 38},
            {22, 5, 7, 12, 28, 37, 39},
            {23, 6, 13, 29, 38},
            {24, 9, 18, 34, 41},
            {25, 8, 10, 19, 35, 40, 42},
            {26, 9, 11, 16, 20, 32, 36, 41, 43},
            {27, 10, 12, 17, 21, 33, 37, 42, 44},
            {28, 11, 13, 18, 22, 34, 38, 43, 45},
            {29, 12, 14, 19, 23, 35, 39, 44, 46},
            {30, 13, 15, 20, 36, 45, 47},
            {31, 14, 21, 37, 46}
    };
    protected static final int[][] BISHOP_MOVES_UPPER_HALF = {
            {0, 9, 18, 27, 36, 45, 54, 63},
            {1, 8, 10, 19, 28, 37, 46, 55},
            {2, 9, 11, 16, 20, 29, 38, 47},
            {3, 10, 12, 17, 21, 24, 30, 39},
            {4, 11, 13, 18, 22, 25, 31, 32},
            {5, 12, 14, 19, 23, 26, 33, 40},
            {6, 13, 15, 20, 27, 34, 41, 48},
            {7, 14, 21, 28, 35, 42, 49, 56},
            {8, 1, 17, 26, 35, 44, 53, 62},
            {9, 0, 2, 16, 18, 27, 36, 45, 54, 63},
            {10, 1, 3, 17, 19, 24, 28, 37, 46, 55},
            {11, 2, 4, 18, 20, 25, 29, 32, 38, 47},
            {12, 3, 5, 19, 21, 26, 30, 33, 39, 40},
            {13, 4, 6, 20, 22, 27, 31, 34, 41, 48},
            {14, 5, 7, 21, 23, 28, 35, 42, 49, 56},
            {15, 6, 22, 29, 36, 43, 50, 57},
            {16, 2, 9, 25, 34, 43, 52, 61},
            {17, 3, 8, 10, 24, 26, 35, 44, 53, 62},
            {18, 0, 4, 9, 11, 25, 27, 32, 36, 45, 54, 63},
            {19, 1, 5, 10, 12, 26, 28, 33, 37, 40, 46, 55},
            {20, 2, 6, 11, 13, 27, 29, 34, 38, 41, 47, 48},
            {21, 3, 7, 12, 14, 28, 30, 35, 39, 42, 49, 56},
            {22, 4, 13, 15, 29, 31, 36, 43, 50, 57},
            {23, 5, 14, 30, 37, 44, 51, 58},
            {24, 3, 10, 17, 33, 42, 51, 60},
            {25, 4, 11, 16, 18, 32, 34, 43, 52, 61},
            {26, 5, 8, 12, 17, 19, 33, 35, 40, 44, 53, 62},
            {27, 0, 6, 9, 13, 18, 20, 34, 36, 41, 45, 48, 54, 63},
            {28, 1, 7, 10, 14, 19, 21, 35, 37, 42, 46, 49, 55, 56},
            {29, 2, 11, 15, 20, 22, 36, 38, 43, 47, 50, 57},
            {30, 3, 12, 21, 23, 37, 39, 44, 51, 58},
            {31, 4, 13, 22, 38, 45, 52, 59}
    };
    protected static final int[][] KING_MOVES_UPPER_HALF = {
            {0, 1, 8, 9},
            {1, 0, 2, 8, 9, 10},
            {2, 1, 3, 9, 10, 11},
            {3, 2, 4, 10, 11, 12},
            {4, 3, 5, 11, 12, 13},
            {5, 4, 6, 12, 13, 14},
            {6, 5, 7, 13, 14, 15},
            {7, 6, 14, 15},
            {8, 0, 1, 9, 16, 17},
            {9, 0, 1, 2, 8, 10, 16, 17, 18},
            {10, 1, 2, 3, 9, 11, 17, 18, 19},
            {11, 2, 3, 4, 10, 12, 18, 19, 20},
            {12, 3, 4, 5, 11, 13, 19, 20, 21},
            {13, 4, 5, 6, 12, 14, 20, 21, 22},
            {14, 5, 6, 7, 13, 15, 21, 22, 23},
            {15, 6, 7, 14, 22, 23},
            {16, 8, 9, 17, 24, 25},
            {17, 8, 9, 10, 16, 18, 24, 25, 26},
            {18, 9, 10, 11, 17, 19, 25, 26, 27},
            {19, 10, 11, 12, 18, 20, 26, 27, 28},
            {20, 11, 12, 13, 19, 21, 27, 28, 29},
            {21, 12, 13, 14, 20, 22, 28, 29, 30},
            {22, 13, 14, 15, 21, 23, 29, 30, 31},
            {23, 14, 15, 22, 30, 31},
            {24, 16, 17, 25, 32, 33},
            {25, 16, 17, 18, 24, 26, 32, 33, 34},
            {26, 17, 18, 19, 25, 27, 33, 34, 35},
            {27, 18, 19, 20, 26, 28, 34, 35, 36},
            {28, 19, 20, 21, 27, 29, 35, 36, 37},
            {29, 20, 21, 22, 28, 30, 36, 37, 38},
            {30, 21, 22, 23, 29, 31, 37, 38, 39},
            {31, 22, 23, 30, 38, 39}
    };
    protected static Map<Integer, List<Integer>> preBakedPLKnightDestinations;
    protected static Map<Integer, List<Integer>> preBakedPLBishopDestinations;
    protected static Map<Integer, List<Integer>> preBakedPLKingDestinations;

    static {
        preBakedPLKnightDestinations = initPLDstMap(KNIGHT_MOVES_UPPER_HALF);
        preBakedPLBishopDestinations = initPLDstMap(BISHOP_MOVES_UPPER_HALF);
        preBakedPLKingDestinations = initPLDstMap(KING_MOVES_UPPER_HALF);
    }

    private static Map<Integer, List<Integer>> initPLDstMap(final int[][] movesUpperHalf) {
        final Map<Integer, List<Integer>> map = new HashMap<>();
        for (final int[] moves : movesUpperHalf) {
            final int origin = moves[0];
            final List<Integer> dst = ChessianUtils.intArrayToList(moves, 1, moves.length);
            // might be useful to have the lists in descending order
            Collections.reverse(dst);
            map.put(origin, dst);
            // put mirrored move set for "lower" half of the board
            final int mirroredOrigin = 63 - origin;
            map.put(mirroredOrigin, mirrorMoves(origin, mirroredOrigin, dst));
        }

        return map;
    }

    private static List<Integer> mirrorMoves(final int origin, final int mirroredOrigin, final List<Integer> dst) {
        final List<Integer> mirrorDst = new ArrayList<>(dst.size());
        for (final int move : dst) {
            mirrorDst.add(origin - move + mirroredOrigin);
        }
        return mirrorDst;
    }
}
