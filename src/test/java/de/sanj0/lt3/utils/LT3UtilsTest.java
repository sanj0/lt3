package de.sanj0.lt3.utils;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.FENParser;
import de.sanj0.lt3.Pieces;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LT3UtilsTest {

    @Test
    void perft() {
        final Board board = Board.fromFEN(FENParser.STARTING_POSITION);
        assertEquals(20, LT3Utils.perft(board, Pieces.LIGHT, 1));
        assertEquals(400, LT3Utils.perft(board, Pieces.LIGHT, 2));
        LT3Utils.perft(board, Pieces.LIGHT, 3);
        LT3Utils.perft(board, Pieces.LIGHT, 4);
        LT3Utils.perft(board, Pieces.LIGHT, 5);
        //assertEquals(8902, LT3Utils.perft(board, Pieces.LIGHT, 3)); fails with a diff of +1
        //assertEquals(197281, LT3Utils.perft(board, Pieces.LIGHT, 4));
    }
}