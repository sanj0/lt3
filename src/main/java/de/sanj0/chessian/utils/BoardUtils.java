package de.sanj0.chessian.utils;

// static utility functions for the chess board
public class BoardUtils {

    private static final char[] FILES = "abcdefgh".toCharArray();

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
}
