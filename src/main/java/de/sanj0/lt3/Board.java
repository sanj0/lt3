package de.sanj0.lt3;

import de.sanj0.lt3.move.CastleMove;
import de.sanj0.lt3.move.EnPassantMove;
import de.sanj0.lt3.move.FancyMove;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.utils.BoardEvaluationHelper;
import de.sanj0.lt3.utils.CastleHelper;
import de.sanj0.lt3.utils.LT3Utils;

import java.util.*;

// a chess board!
public class Board {

    private byte[] data;
    private final byte colorToStart;
    private Map<Byte, List<CastleHelper.Castle>> allowedCastles;
    private int enPassant;
    private Deque<Move> moveHistory;
    private boolean customPosition;

    private Deque<FancyMove> undoStack = new ArrayDeque<>();
    private Deque<FancyMove> redoStack = new ArrayDeque<>();

    public Board(final byte[] data, final byte colorToStart,
                 final Map<Byte, List<CastleHelper.Castle>> allowedCastles, final int enPassant, final Deque<Move> moveHistory, final boolean customPosition) {
        if (data.length != 64) {
            throw new IllegalArgumentException("chess board has to have 64 squares!");
        }
        this.data = data;
        this.colorToStart = colorToStart;
        this.allowedCastles = allowedCastles;
        this.enPassant = enPassant;
        this.moveHistory = moveHistory;
        this.customPosition = customPosition;
    }

    public void doMove(final Move m) {
        if (m.isEmpty()) return;
        System.out.println(m.extendedNotation(this) + " was played...");
        undoStack.push(m.fancify(this));
        if (!m.equals(redoStack.peek())) {
            redoStack.clear();
        }
        final Board afterState = afterMove(m);
        data = afterState.data;
        allowedCastles = afterState.allowedCastles;
        enPassant = afterState.enPassant;
        moveHistory = afterState.moveHistory;
        final double eval = rateBoard();
        System.out.println("eval: " + eval);
    }

    public Board afterMove(final Move m) {
        // promotions, castles etc. here?
        final byte[] newData = Arrays.copyOf(data, data.length);
        final Map<Byte, List<CastleHelper.Castle>> newAllowedCastles = LT3Utils.copyCastleRightsMap(allowedCastles);
        final byte me = data[m.getStart()];
        final int start = m.getStart();
        final int end = m.getEnd();
        int newEnPassant = m.isPawnDoubleAdvance(this) ? Math.min(start, end) + 8 : -1;
        final Deque<Move> newMoveHistory = new ArrayDeque<>(moveHistory);
        newMoveHistory.push(m);
        newData[start] = Pieces.NONE;
        newData[m.getEnd()] = m.isPromotion(this) ? Pieces.get(Pieces.QUEEN, Pieces.color(me)) : me;

        // castle and remove rights
        if (m instanceof CastleMove) {
            final Move rookMove = ((CastleMove) m).getRookMove();
            newData[rookMove.getStart()] = Pieces.NONE;
            newData[rookMove.getEnd()] = data[rookMove.getStart()];
            newAllowedCastles.put(Pieces.color(me), new ArrayList<>());
        } else if (m instanceof EnPassantMove) {
            newData[((EnPassantMove) m).getTakenPawn()] = Pieces.NONE;
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

        return new Board(newData, colorToStart, newAllowedCastles, newEnPassant, newMoveHistory, customPosition);
    }

    public boolean undo() {
        if (!undoStack.isEmpty()) {
            final FancyMove move = undoStack.pop();
            move.undo(this);
            redoStack.push(move);
            moveHistory.pop();
            return true;
        }
        return false;
    }

    public boolean redo() {
        if (!redoStack.isEmpty()) {
            final FancyMove move = redoStack.pop();
            doMove(move);
            undoStack.push(move);
            moveHistory.push(move);
            return true;
        }
        return false;
    }

    public double rateBoard() {
        double evaluation = 0;
        for (int i = 0; i < data.length; i++) {
            byte p = data[i];
            evaluation += BoardEvaluationHelper.ratePiece(p, i);
        }

        return evaluation;
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

    /**
     * Gets {@link #enPassant}.
     *
     * @return the value of {@link #enPassant}
     */
    public int getEnPassant() {
        return enPassant;
    }

    /**
     * Sets {@link #enPassant}.
     *
     * @param enPassant the new value of {@link #enPassant}
     */
    public void setEnPassant(final int enPassant) {
        this.enPassant = enPassant;
    }

    public Move getLastMove() {
        return moveHistory.peek();
    }

    /**
     * Gets {@link #moveHistory}.
     *
     * @return the value of {@link #moveHistory}
     */
    public Deque<Move> getMoveHistory() {
        return moveHistory;
    }

    /**
     * Gets {@link #customPosition}.
     *
     * @return the value of {@link #customPosition}
     */
    public boolean isCustomPosition() {
        return customPosition;
    }

    @Override
    public String toString() {
        return "Board{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
