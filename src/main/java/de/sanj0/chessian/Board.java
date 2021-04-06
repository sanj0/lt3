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
