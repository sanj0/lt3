package de.sanj0.chessian;

import static de.sanj0.chessian.Pieces.*;

// parses FENS!
public class FENParser {

    public static final String STARTING_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    public static final char LC_PAWN = 'p';
    public static final char LC_KNIGHT = 'n';
    public static final char LC_BISHOP = 'b';
    public static final char LC_ROOK = 'r';
    public static final char LC_QUEEN = 'q';
    public static final char LC_KING = 'k';

    /**
     * Pareses the given FEN position
     * into a board position stored in
     * an array length 64
     *
     * @param FEN a FEN position
     * @return the board position as a byte array length 64 as
     * specified by the given FEN string
     */
    public static byte[] parseFEN(final String FEN) {
        final String positionString = FEN.split(" ")[0];
        final byte[] board = new byte[64];
        int pointer = 0;

        for (final char c : positionString.toCharArray()) {
            if (pointer >= 64) {
                throw new InvalidFENException(positionString);
            }
            if (Character.isLetter(c)) {
                // piece
                board[pointer] = pieceFromFEN(c);
                pointer++;
            } else if (Character.isDigit(c)) {
                // pointer advance
                pointer += Character.getNumericValue(c);
            }
        }

        if (pointer != 64) {
            throw new InvalidFENException(positionString);
        }

        return board;
    }

    public static byte pieceFromFEN(final char piece) {
        final byte color = Character.isLowerCase(piece) ? DARK : LIGHT;
        final char p = Character.toLowerCase(piece);
        byte type;
        switch (p) {
            case LC_PAWN:
                type = PAWN;
                break;
            case LC_KNIGHT:
                type = KNIGHT;
                break;
            case LC_BISHOP:
                type = BISHOP;
                break;
            case LC_ROOK:
                type = ROOK;
                break;
            case LC_QUEEN:
                type = QUEEN;
                break;
            case LC_KING:
                type = KING;
                break;
            default:
                throw new IllegalArgumentException(piece + " is not a valid piece in FEN!");
        }

        // assignment by compound operator to
        // avoid the need to cast from int
        return get(type, color);
    }
}
