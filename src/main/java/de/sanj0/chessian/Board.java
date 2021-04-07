package de.sanj0.chessian;

import de.sanj0.chessian.move.CastleMove;
import de.sanj0.chessian.move.Move;
import de.sanj0.chessian.utils.CastleHelper;
import de.sanj0.chessian.utils.ChessianUtils;

import java.util.*;

// a chess board!
public class Board {

    private byte[] data;
    private final byte colorToStart;
    private Map<Byte, List<CastleHelper.Castle>> allowedCastles;

    public Board(final byte[] data, final byte colorToStart, final Map<Byte, List<CastleHelper.Castle>> allowedCastles) {
        if (data.length != 64) {
            throw new IllegalArgumentException("chess board has to have 64 squares!");
        }
        this.data = data;
        this.colorToStart = colorToStart;
        this.allowedCastles = allowedCastles;
    }

    public void doMove(final Move m) {
        final Board afterState = afterMove(m);
        data = afterState.data;
        allowedCastles = afterState.allowedCastles;
    }

    public Board afterMove(final Move m) {
        // promotions, castles etc. here?
        final byte[] newData = Arrays.copyOf(data, data.length);
        final Map<Byte, List<CastleHelper.Castle>> newAllowedCastles = ChessianUtils.copyCastleRightsMap(allowedCastles);
        final byte me = data[m.getStart()];
        final int start = m.getStart();
        newData[start] = Pieces.NONE;
        newData[m.getEnd()] = me;

        // castle and remove rights
        if (m instanceof CastleMove) {
            final Move rookMove = ((CastleMove) m).getRookMove();
            newData[rookMove.getStart()] = Pieces.NONE;
            newData[rookMove.getEnd()] = data[rookMove.getStart()];
            newAllowedCastles.put(Pieces.color(me), new ArrayList<>());
        } else if (Pieces.isKing(me)) {
            newAllowedCastles.put(Pieces.color(me), new ArrayList<>());
        } else if (Pieces.isRook(me)) {
            if (Pieces.color(me) == Pieces.LIGHT) {
                if (start == 63) {
                    newAllowedCastles.get(Pieces.LIGHT).remove(CastleHelper.Castle.KING_SIDE_LIGHT);
                } else if (start == 56) {
                    newAllowedCastles.get(Pieces.LIGHT).remove(CastleHelper.Castle.QUEEN_SIDE_LIGHT);
                }
            } else {
                if (start == 7) {
                    newAllowedCastles.get(Pieces.DARK).remove(CastleHelper.Castle.KING_SIDE_DARK);
                } else if (start == 0) {
                    newAllowedCastles.get(Pieces.DARK).remove(CastleHelper.Castle.QUEEN_SIDE_DARK);
                }
            }
        }

        return new Board(newData, colorToStart, newAllowedCastles);
    }

    // loads a board from the given fen
    public static Board fromFEN(final String fen) {
        return FENParser.parseFEN(fen);
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

    /**
     * Gets {@link #colorToStart}.
     *
     * @return the value of {@link #colorToStart}
     */
    public byte getColorToStart() {
        return colorToStart;
    }

    /**
     * Gets {@link #allowedCastles}.
     *
     * @return the value of {@link #allowedCastles}
     */
    public Map<Byte, List<CastleHelper.Castle>> getAllowedCastles() {
        return allowedCastles;
    }

    /**
     * Sets {@link #allowedCastles}.
     *
     * @param allowedCastles the new value of {@link #allowedCastles}
     */
    public void setAllowedCastles(final Map<Byte, List<CastleHelper.Castle>> allowedCastles) {
        this.allowedCastles = allowedCastles;
    }

    @Override
    public String toString() {
        return "Board{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
