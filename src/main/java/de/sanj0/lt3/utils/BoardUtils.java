package de.sanj0.lt3.utils;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.Pieces;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.sanj0.lt3.Pieces.*;

// static utility functions for the chess board
public class BoardUtils {

    private static final char[] FILES = "abcdefgh".toCharArray();
    private static final int[] CENTRE_DISTANCE = {
            3, 3, 3, 3, 3, 3, 3, 3,
            3, 2, 2, 2, 2, 2, 2, 3,
            3, 2, 1, 1, 1, 1, 2, 3,
            3, 2, 1, 0, 0, 1, 2, 3,
            3, 2, 1, 0, 0, 1, 2, 3,
            3, 2, 1, 1, 1, 1, 2, 3,
            3, 2, 2, 2, 2, 2, 2, 3,
            3, 3, 3, 3, 3, 3, 3, 3
    };
    private static final int MAX_NUM_PIECES = 32;

    private static Map<Byte, List<Integer>> STARTING_POSITIONS = new HashMap<>() {{
        put(Pieces.get(PAWN, LIGHT), Arrays.asList(48, 49, 50, 51, 52, 53, 54, 55));
        put(Pieces.get(KNIGHT, LIGHT), Arrays.asList(57, 62));
        put(Pieces.get(BISHOP, LIGHT), Arrays.asList(58, 61));
        put(Pieces.get(ROOK, LIGHT), Arrays.asList(56, 63));
        put(Pieces.get(QUEEN, LIGHT), Arrays.asList(59));
        put(Pieces.get(KING, LIGHT), Arrays.asList(60));
        // dark
        put(Pieces.get(PAWN, DARK), Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15));
        put(Pieces.get(KNIGHT, DARK), Arrays.asList(1, 6));
        put(Pieces.get(BISHOP, DARK), Arrays.asList(2, 5));
        put(Pieces.get(ROOK, DARK), Arrays.asList(0, 7));
        put(Pieces.get(QUEEN, DARK), Arrays.asList(3));
        put(Pieces.get(KING, DARK), Arrays.asList(4));
    }};

    public static List<Integer> startingPositions(final byte piece) {
        if (piece == NONE) {
            throw new IllegalArgumentException("empty piece has no starting positions!");
        }
        return STARTING_POSITIONS.get(piece);
    }

    public static double endgame(final Board board) {
        int numPieces = 0;
        final byte[] data = board.getData();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != Pieces.NONE) {
                numPieces++;
            }
        }

        return 1 - (double) numPieces / MAX_NUM_PIECES;
    }

    // range: 0 - 3
    public static int distanceFromCentre(final int position) {
        return CENTRE_DISTANCE[position];
    }

    /**
     * Returns the name of the square at the given
     * index in the following form:
     * <br>[file_name][rank]
     * <p>
     * e.g. 0 - a1
     * <br> 28 - e4
     *
     * @param index the index of a square
     * @return the algebraic square notation of the square at the given index
     */
    public static String squareName(final int index) {
        return String.valueOf(fileName(index)) + (rank(index) + 1);
    }

    public static int file(final int index) {
        return index & 7; // mod 8
    }

    public static int rank(final int index) {
        return 7 - (index >> 3);
    }

    /**
     * Returns the name of the file of the given index
     * from a to h.
     *
     * @param index the index of the position in the 64-byte array
     * @return the name of the file of the given index
     */
    public static char fileName(final int index) {
        return FILES[file(index)];
    }

    /**
     * Returns the name of the given file.
     *
     * @param file a file
     * @return the name of the given file
     */
    public static char getFileName(final int file) {
        if (file < 0 || file > 7) {
            throw new IllegalArgumentException("file has to be between 0 and 7 (inclusive)");
        }
        return FILES[file];
    }

    public static int kingPosition(final Board board, final byte myColor) {
        final byte[] data = board.getData();

        for (int i = 0; i < data.length; i++) {
            byte piece = data[i];
            if (Pieces.isKing(piece) && Pieces.color(piece) == myColor) {
                return i;
            }
        }

        // should theoretically never be reached as a move capturing the king
        // should never be legal
        return -1;
    }
}
