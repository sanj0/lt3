package de.sanj0.lt3.utils;

import static de.sanj0.lt3.Pieces.*;

// helps with evaluating boards!
public class BoardEvaluationHelper {
    private static final double[] PAWN_EVAL_LIGHT = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0, 5.0,
            1.0, 1.0, 2.0, 3.0, 3.0, 2.0, 1.0, 1.0,
            0.5, 0.5, 1.0, 2.5, 2.5, 1.0, 0.5, 0.5,
            0.0, 0.0, 0.0, 2.0, 2.0, 0.0, 0.0, 0.0,
            0.5, -0.5, -1.0, 0.0, 0.0, -1.0, -0.5, 0.5,
            0.5, 1.0, 1.0, -2.0, -2.0, 1.0, 1.0, 0.5,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
    };
    private static final double[] PAWN_EVAL_DARK = LT3Utils.reverseArray(PAWN_EVAL_LIGHT);
    private static final double[] KNIGHT_EVAL = {
            -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0,
            -4.0, -2.0, 0.0, 0.0, 0.0, 0.0, -2.0, -4.0,
            -3.0, 0.0, 1.0, 1.5, 1.5, 1.0, 0.0, -3.0,
            -3.0, 0.5, 1.5, 2.0, 2.0, 1.5, 0.5, -3.0,
            -3.0, 0.0, 1.5, 2.0, 2.0, 1.5, 0.0, -3.0,
            -3.0, 0.5, 1.0, 1.5, 1.5, 1.0, 0.5, -3.0,
            -4.0, -2.0, 0.0, 0.5, 0.5, 0.0, -2.0, -4.0,
            -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0
    };
    private static final double[] BISHOP_EVAL_LIGHT = {
            -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0,
            -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0,
            -1.0, 0.0, 0.5, 1.0, 1.0, 0.5, 0.0, -1.0,
            -1.0, 0.5, 0.5, 1.0, 1.0, 0.5, 0.5, -1.0,
            -1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, -1.0,
            -1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, -1.0,
            -1.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, -1.0,
            -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0
    };
    private static final double[] BISHOP_EVAL_DARK = LT3Utils.reverseArray(BISHOP_EVAL_LIGHT);
    private static final double[] ROOK_EVAL_LIGHT = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5,
            -0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5,
            -0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5,
            -0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5,
            -0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5,
            -0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.5,
            0.0, 0.0, 0.0, 0.5, 0.5, 0.0, 0.0, 0.0
    };
    private static final double[] ROOK_EVAL_DARK = LT3Utils.reverseArray(ROOK_EVAL_LIGHT);
    private static final double[] QUEEN_EVAL = {
            -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0,
            -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0,
            -1.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0,
            -0.5, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5,
            0.0, 0.0, 0.5, 0.5, 0.5, 0.5, 0.0, -0.5,
            -1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.0, -1.0,
            -1.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, -1.0,
            -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0
    };
    private static final double[] KING_EVAL_LIGHT = {
            -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
            -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
            -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
            -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
            -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0,
            -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0,
            1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0,
            1.0, 2.0, 1.0, 0.0, 0.0, 1.0, 2.0, 1.0
    };
    private static final double[] KING_EVAL_DARK = LT3Utils.reverseArray(KING_EVAL_LIGHT);

    public static double ratePiece(final byte piece, final int pos) {
        if (piece == NONE) {
            return 0;
        }

        final boolean light = color(piece) == LIGHT;
        double evalValue = 0;
        switch (type(piece)) {
            case PAWN:
                evalValue = light ? PAWN_EVAL_LIGHT[pos] : PAWN_EVAL_DARK[pos];
                break;
            case KNIGHT:
                evalValue = KNIGHT_EVAL[pos];
                break;
            case BISHOP:
                evalValue = light ? BISHOP_EVAL_LIGHT[pos] : BISHOP_EVAL_DARK[pos];
                break;
            case ROOK:
                evalValue = light ? ROOK_EVAL_LIGHT[pos] : ROOK_EVAL_DARK[pos];
                break;
            case QUEEN:
                evalValue = QUEEN_EVAL[pos];
                break;
            case KING:
                evalValue = light ? KING_EVAL_LIGHT[pos] : KING_EVAL_DARK[pos];
                break;
        }

        final double absValue = value(piece) + evalValue;
        return light ? absValue : -absValue;
    }

    public static double ratePiecePositionNeutral(final byte piece, final int pos) {
        if (piece == NONE) {
            return 0;
        }

        final boolean light = color(piece) == LIGHT;
        double evalValue = 0;
        switch (type(piece)) {
            case PAWN:
                evalValue = light ? PAWN_EVAL_LIGHT[pos] : PAWN_EVAL_DARK[pos];
                break;
            case KNIGHT:
                evalValue = KNIGHT_EVAL[pos];
                break;
            case BISHOP:
                evalValue = light ? BISHOP_EVAL_LIGHT[pos] : BISHOP_EVAL_DARK[pos];
                break;
            case ROOK:
                evalValue = light ? ROOK_EVAL_LIGHT[pos] : ROOK_EVAL_DARK[pos];
                break;
            case QUEEN:
                evalValue = QUEEN_EVAL[pos];
                break;
            case KING:
                evalValue = light ? KING_EVAL_LIGHT[pos] : KING_EVAL_DARK[pos];
                break;
        }

        return evalValue;
    }
}
