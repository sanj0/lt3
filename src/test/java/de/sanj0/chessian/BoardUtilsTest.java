package de.sanj0.chessian;

import static org.junit.jupiter.api.Assertions.*;

class BoardUtilsTest {

    @org.junit.jupiter.api.Test
    void squareName() {
        assertEquals("a8", BoardUtils.squareName(0));
        assertEquals("e5", BoardUtils.squareName(28));
    }

    @org.junit.jupiter.api.Test
    void file() {
        assertEquals(2, BoardUtils.file(10));
        assertEquals(2, BoardUtils.file(26));
        assertEquals(2, BoardUtils.file(50));
        assertEquals(6, BoardUtils.file(46));
        assertEquals(6, BoardUtils.file(54));
        assertEquals(0, BoardUtils.file(0));
        assertEquals(7, BoardUtils.file(7));
        assertEquals(0, BoardUtils.file(8));
        assertEquals(7, BoardUtils.file(15));
    }

    @org.junit.jupiter.api.Test
    void rank() {
        assertEquals(7, BoardUtils.rank(0));
        assertEquals(7, BoardUtils.rank(1));
        assertEquals(7, BoardUtils.rank(7));
        assertEquals(0, BoardUtils.rank(56));
        assertEquals(0, BoardUtils.rank(57));
        assertEquals(0, BoardUtils.rank(63));
        assertEquals(4, BoardUtils.rank(26));
        assertEquals(4, BoardUtils.rank(27));
    }

    @org.junit.jupiter.api.Test
    void fileName() {
        assertEquals('a', BoardUtils.fileName(56));
        assertEquals('a', BoardUtils.fileName(48));
        assertEquals('a', BoardUtils.fileName(0));
        assertEquals('a', BoardUtils.fileName(8));
        assertEquals('h', BoardUtils.fileName(63));
        assertEquals('h', BoardUtils.fileName(55));
        assertEquals('h', BoardUtils.fileName(7));
        assertEquals('h', BoardUtils.fileName(15));
        assertEquals('c', BoardUtils.fileName(42));
        assertEquals('c', BoardUtils.fileName(2));
    }

    @org.junit.jupiter.api.Test
    void getFileName() {
        assertEquals('a', BoardUtils.getFileName(0));
        assertEquals('b', BoardUtils.getFileName(1));
        assertEquals('c', BoardUtils.getFileName(2));
        assertEquals('d', BoardUtils.getFileName(3));
        assertEquals('e', BoardUtils.getFileName(4));
        assertEquals('f', BoardUtils.getFileName(5));
        assertEquals('g', BoardUtils.getFileName(6));
        assertEquals('h', BoardUtils.getFileName(7));
    }
}