package de.sanj0.lt3;

/**
 * An exception thrown when a given
 * FEN notation is not valid
 */
public class InvalidFENException extends RuntimeException {
    public InvalidFENException(final String part, final String FEN) {
        super("Invalid FEN notation in part " + part + ": \"" + FEN + "\"");
    }
}
