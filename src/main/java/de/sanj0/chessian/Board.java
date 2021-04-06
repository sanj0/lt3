package de.sanj0.chessian;

import java.util.Arrays;

// a chess board!
public class Board {

    private byte[] data;

    public Board(final byte[] data) {
        if (data.length != 64) {
            throw new IllegalArgumentException("chess board has to have 64 squares!");
        }
        this.data = data;
    }

    // loads a board from the given fen
    public static Board fromFEN(final String fen) {
        return new Board(FENParser.parseFEN(fen));
    }

    /**
     * Gets the piece at the given index.
     *
     * @param index an index
     * @return the piece at the given index
     */
    public byte get(final int index) {
        return data[index];
    }

    /**
     * Sets the piece at the given index to the given value
     * and returns the former value.
     *
     * @param index an index in the board array
     * @param piece a piece to be placed at the index
     * @return the former piece at the given index
     */
    public byte set(final int index, final byte piece) {
        final byte former = data[index];
        data[index] = piece;
        return former;
    }

    /**
     * Gets {@link #data}.
     *
     * @return the value of {@link #data}
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets {@link #data}.
     *
     * @param data the new value of {@link #data}
     */
    public void setData(final byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Board{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
