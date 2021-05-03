package de.sanj0.lt3.engine;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.move.Move;
import de.sanj0.lt3.move.MoveGenerator;

import java.util.List;

public class MoveRateResult {
    private int result;
    private final byte colorToMax;
    private List<Move> responsesAtLastDepth;
    private List<Move> plResponseResponses;

    public MoveRateResult(final int result, final byte colorToMax, final List<Move> responses) {
        this.result = result;
        this.colorToMax = colorToMax;
        this.responsesAtLastDepth = responses;
    }

    public void exploreFurther(final LT3Engine engine, final Board board,
                               final byte colorToMove) {
       // final List<Move> responsesAtNewDepth = MoveGenerator.
    }

    /**
     * Gets {@link #result}.
     *
     * @return the value of {@link #result}
     */
    public int getResult() {
        return result;
    }

    /**
     * Gets {@link #colorToMax}.
     *
     * @return the value of {@link #colorToMax}
     */
    public byte getColorToMax() {
        return colorToMax;
    }

    /**
     * Gets {@link #responsesAtLastDepth}.
     *
     * @return the value of {@link #responsesAtLastDepth}
     */
    public List<Move> getResponsesAtLastDepth() {
        return responsesAtLastDepth;
    }
}
