package de.sanj0.chessian;

public class Pieces {

    private static final char[] LETTERS = "_PNBRQK".toCharArray();

    public static final byte NONE = 0;
    public static final byte PAWN = 1;
    public static final byte KNIGHT = 2;
    public static final byte BISHOP = 3;
    public static final byte ROOK = 4;
    public static final byte QUEEN = 5;
    public static final byte KING = 6;

    public static final byte LIGHT = 8;
    public static final byte DARK = 16;

    public static final byte TYPE_MASK = 0b00111;
    public static final byte DARK_MASK = 0b10000;
    public static final byte LIGHT_MASK = 0b01000;
    public static final byte COLOR_MASK = (byte) (LIGHT_MASK | DARK_MASK);

    public static char letter(final byte p) {
        return LETTERS[type(p)];
    }

    /**
     * Casts the given int to a byte and return it
     *
     * @param i an int
     *
     * @return the int casted to a byte
     */
    public static byte b(final int i) {
        return (byte) i;
    }

    public static byte get(final byte type, final byte color) {
        return (byte) (type | color);
    }

    public static byte color(final byte piece) {
        return (byte) (piece & COLOR_MASK);
    }

    public static byte type(final byte piece) {
        return (byte) (piece & TYPE_MASK);
    }

    public static int value(final byte piece) {
        final byte type = type(piece);
        if (type == PAWN) {
            return 1;
        } else if (type == KNIGHT || type == BISHOP) {
            return 3;
        } else if (type == ROOK) {
            return 5;
        } else if (type == QUEEN) {
            return 8;
        } else if (type == KING) {
            return 0;
        } else {
            throw new IllegalArgumentException(piece + " is not a valid piece.");
        }
    }

    public static int valueForRating(final byte piece) {
        return value(piece) * 20;
    }

    public static boolean isLight(final byte piece) {
        return color(piece) == LIGHT;
    }

    public static boolean isDark(final byte piece) {
        return color(piece) == DARK;
    }

    public static boolean isPawn(final byte piece) {
        return type(piece) == PAWN;
    }

    public static boolean isKnight(final byte piece) {
        return type(piece) == KNIGHT;
    }

    public static boolean isBishop(final byte piece) {
        return type(piece) == BISHOP;
    }

    public static boolean isRook(final byte piece) {
        return type(piece) == ROOK;
    }

    public static boolean isQueen(final byte piece) {
        return type(piece) == QUEEN;
    }

    public static boolean isKing(final byte piece) {
        return type(piece) == KING;
    }

    public static byte oppositeColor(final byte myColor) {
        return myColor == DARK ? LIGHT : DARK;
    }
}
