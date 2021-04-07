package de.sanj0.chessian;

import de.sanj0.chessian.utils.CastleHelper;

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

    public static final String LC_WHITE = "w";
    public static final String LC_BLACK = "b";

    private static final String PART_POSITION = "position";
    private static final String PART_COLOR = "color";

    /**
     * Pareses the given FEN position
     * into a board position stored in
     * an array length 64
     *
     * @param FEN a FEN position
     * @return the board position as a byte array length 64 as
     * specified by the given FEN string
     */
    public static Board parseFEN(final String FEN) {
        final String[] parts = FEN.split(" ");
        final String positionString = parts[0];
        final String startingColor = parts.length >= 2 ? parts[1] : LC_WHITE;

        final byte[] data = new byte[64];
        int pointer = 0;
        byte colorToStart;

        for (final char c : positionString.toCharArray()) {
            if (pointer >= 64) {
                throw new InvalidFENException(PART_POSITION, positionString);
            }
            if (Character.isLetter(c)) {
                // piece
                data[pointer] = pieceFromFEN(c);
                pointer++;
            } else if (Character.isDigit(c)) {
                // pointer advance
                pointer += Character.getNumericValue(c);
            }
        }

        if (pointer != 64) {
            throw new InvalidFENException(PART_POSITION, positionString);
        }

        if (startingColor.equals(LC_WHITE)) {
            colorToStart = LIGHT;
        } else if (startingColor.equals(LC_BLACK)) {
            colorToStart = DARK;
        } else {
            throw new InvalidFENException(PART_COLOR, startingColor);
        }

        //TODO: parse castles and en passant from FEN
        return new Board(data, colorToStart, CastleHelper.ALL_CASTLES, -1);
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
