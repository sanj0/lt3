package de.sanj0.lt3.openings;

import de.edgelord.saltyengine.utils.SaltySystem;
import de.sanj0.lt3.move.Move;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// parses the openings.txt file
// syntax:
//     NAME::a-b c-d e-f [...]
//     where NAME is the name of the opening
//           and x-y are moves
//     every line that doesn't contain exactly one "::" is treated as a comment and thus ignored
public class OpeningsParser {

    private static final String NAME_AND_MOVES_SEPARATOR = "::";
    private static final String MOVE_SEPARATOR           = " ";
    private static final String COORDINATE_SEPARATOR     = "-";

    public static List<Opening> parse(final String path) throws IOException {
        final File file = SaltySystem.defaultResource.getFileResource(path);
        final List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        final List<Opening> openings = new ArrayList<>(lines.size());

        for (final String line : lines) {
            String name;
            final List<Move> moves = new ArrayList<>(5);
            final String[] parts = line.split(NAME_AND_MOVES_SEPARATOR);
            if (parts.length != 2)
                continue;
            name = parts[0];
            final String[] moveStrings = parts[1].split(MOVE_SEPARATOR);
            for (final String m : moveStrings) {
                moves.add(parseMove(m));
            }
            openings.add(new Opening(name, moves));
        }

        return openings;
    }

    private static Move parseMove(final String m) {
        final String[] parts = m.split(COORDINATE_SEPARATOR);
        return new Move(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
    }
}
