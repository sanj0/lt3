package de.sanj0.lt3.utils;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.Pieces;

import java.util.*;

// helps with castles but is not an architect
public class CastleHelper {

    public static final Map<Byte, List<Castle>> ALL_CASTLES = new HashMap<>() {{
        put(Pieces.LIGHT, new ArrayList<>(Arrays.asList(Castle.KING_SIDE_LIGHT, Castle.QUEEN_SIDE_LIGHT)));
        put(Pieces.DARK, new ArrayList<>(Arrays.asList(Castle.KING_SIDE_DARK, Castle.QUEEN_SIDE_DARK)));
    }};

    public static boolean requiredSquaresEmpty(final Castle castle, final Board board) {
        switch (castle) {
            case KING_SIDE_LIGHT:
                return board.get(61) == Pieces.NONE && board.get(62) == Pieces.NONE;
            case QUEEN_SIDE_LIGHT:
                return board.get(59) == Pieces.NONE && board.get(58) == Pieces.NONE
                        && board.get(57) == Pieces.NONE;
            case KING_SIDE_DARK:
                return board.get(5) == Pieces.NONE && board.get(6) == Pieces.NONE;
            case QUEEN_SIDE_DARK:
                return board.get(3) == Pieces.NONE && board.get(2) == Pieces.NONE
                        && board.get(1) == Pieces.NONE;
        }
        // never happens anyway
        return false;
    }

    public enum Castle {
        KING_SIDE_LIGHT,
        QUEEN_SIDE_LIGHT,
        KING_SIDE_DARK,
        QUEEN_SIDE_DARK
    }
}
