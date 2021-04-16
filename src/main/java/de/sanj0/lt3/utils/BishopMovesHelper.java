package de.sanj0.lt3.utils;

import de.sanj0.lt3.Pieces;
import de.sanj0.lt3.move.Move;

import java.util.*;

// helps with bishop moves - how nice!
public class BishopMovesHelper {

    // FIXME: review and rewrite
    public static List<Move> withoutBlockedSquares(final List<Integer> moves, final byte[] board, final int origin, final byte myColor) {
        final List<Move> filteredMoves = new ArrayList<>(moves.size());
        final Set<Integer> blockedDiagonalIDs = new HashSet<>();
        // which square are the respective diagonals blocked from?
        final Map<Integer, Integer> blockedFrom = new HashMap<>();
        // enables us to handle captures the same way as friendly fire -
        // just store the captures to be retained here
        // key is the diagonal ID, value the capture
        final Map<Integer, Move> capturesToRetain = new HashMap<>();
        for (final int m : moves) {
            final byte potentialCapture = board[m];
            if (potentialCapture != Pieces.NONE) {
                final int distance = Math.abs(m - origin);
                final int d = normalizeDiagonal(origin, m);
                blockedDiagonalIDs.add(d);
                if (blockedFrom.getOrDefault(d, 100) > distance) {
                    // new nearer piece that blocks the diagonal
                    blockedFrom.put(d, distance);
                    if (!capturesToRetain.containsKey(d) || Math.abs(capturesToRetain.get(d).getEnd() - origin) > distance) {
                        // capture has to be removed, new one will be added
                        // directly after if the piece if an enemy
                        capturesToRetain.remove(d);
                    }
                    if (Pieces.color(potentialCapture) != myColor) {
                        capturesToRetain.put(d, new Move(origin, m));
                    }
                }
            }
        }

        for (final int m : moves) {
            final int normalizesDiagonal = normalizeDiagonal(origin, m);
            if (!blockedDiagonalIDs.contains(normalizesDiagonal) ||
                    blockedFrom.getOrDefault(normalizesDiagonal, -100) > Math.abs(m - origin)) {
                // diagonal is free, just go ahead!
                filteredMoves.add(new Move(origin, m));
            }
        }

        filteredMoves.addAll(capturesToRetain.values());

        return filteredMoves;
    }

    private static int normalizeDiagonal(final int origin, final int dst) {
        int d = dst - origin;
        if (d % 9 == 0) {
            return d > 0 ? 9 : -9;
        } else if (d % 7 == 0) {
            return d > 0 ? 7 : -7;
        }
        // can never be reached anyways
        return -1;
    }
}
