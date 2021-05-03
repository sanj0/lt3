package de.sanj0.lt3.engine;

/**
 * Controls how strong the engine should play.
 */
public class StrengthControl {

    /**
     * Strength controlled by timeout
     * or by foresight depth?
     */
    private final StrengthControlMode controlMode;

    /**
     * Either time in ms or
     * depth in half-turns
     */
    private final int value;

    public StrengthControl(final StrengthControlMode controlMode, final int value) {
        this.controlMode = controlMode;
        this.value = value;
    }

    public static StrengthControl timeout(final int ms) {
        return new StrengthControl(StrengthControlMode.TIME, ms);
    }

    public StrengthControl depth(final int depth) {
        return new StrengthControl(StrengthControlMode.DEPTH, depth);
    }

    /**
     * Should the engine be done
     * and play the current best move?
     *
     * @param startTime the time in epoch when the engine started thinking
     * @param currDepth the current depth of foresight
     * @return <code>true</code> if the engine should be done and therefore play
     * the current best move
     */
    public boolean finish(final long startTime, final int currDepth) {
        switch (controlMode) {
            case TIME: return System.currentTimeMillis() - startTime >= value;
            case DEPTH: return currDepth >= value;
            default: return true;
        }
    }

    /**
     * Gets {@link #controlMode}.
     *
     * @return the value of {@link #controlMode}
     */
    public StrengthControlMode getControlMode() {
        return controlMode;
    }

    /**
     * Gets {@link #value}.
     *
     * @return the value of {@link #value}
     */
    public int getValue() {
        return value;
    }

    public enum StrengthControlMode {
        TIME,
        DEPTH
    }
}
