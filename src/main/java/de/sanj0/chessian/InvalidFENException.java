package de.sanj0.chessian;

/**
 * An exception thrown when a given
 * FEN notation is not valid
 */
public class InvalidFENException extends RuntimeException {
    public InvalidFENException(final String FEN) {
        super("Invalid FEN notation: \"" + FEN + "\"");
    }
}
