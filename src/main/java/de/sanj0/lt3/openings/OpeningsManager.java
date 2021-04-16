package de.sanj0.lt3.openings;

import de.sanj0.lt3.Board;
import de.sanj0.lt3.move.Move;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// manages openings!
public class OpeningsManager {

    public static final String DEFAULT_OPENINGS_PATH = "openings.txt";

    private List<Opening> openings;

    public OpeningsManager(final List<Opening> openings) {
        this.openings = openings;
    }

    public List<Opening> availableOpenings(final Board board) {
        final List<Move> playedMoves = new ArrayList<>(board.getMoveHistory());
        Collections.reverse(playedMoves);
        final List<Opening> availableOpenings = new ArrayList<>(openings.size() / 3); // divide by 3 because there are three major sections in the database currently

        for (final Opening opening : openings) {
            if (opening.startsWith(playedMoves)) {
                availableOpenings.add(opening);
            }
        }

        return availableOpenings;
    }

    public static OpeningsManager parseDefaultOpenings() {
        try {
            return new OpeningsManager(OpeningsParser.parse(DEFAULT_OPENINGS_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets {@link #openings}.
     *
     * @return the value of {@link #openings}
     */
    public List<Opening> getOpenings() {
        return openings;
    }
}
