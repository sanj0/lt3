package de.sanj0.lt3.move;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.utils.CastleHelper;

import java.util.List;
import java.util.Map;

// a move that could be undone and redone - how fancy!
public class FancyMove extends Move {

    // state before the move
    private final byte capturedPiece;
    private final int enPassant;
    private final Map<Byte, List<CastleHelper.Castle>> castleRights;

    public FancyMove(final int start, final int end, final byte capturedPiece,
                     final int enPassant, final Map<Byte, List<CastleHelper.Castle>> castleRights) {
        super(start, end);
        this.capturedPiece = capturedPiece;
        this.enPassant = enPassant;
        this.castleRights = castleRights;
    }

    public void undo(final Board board) {
        board.set(start, board.get(end));
        board.set(end, capturedPiece);
        board.setEnPassant(enPassant);
        board.setAllowedCastles(castleRights);
    }

    /**
     * Gets {@link #capturedPiece}.
     *
     * @return the value of {@link #capturedPiece}
     */
    public byte getCapturedPiece() {
        return capturedPiece;
    }
}
