package de.sanj0.chessian.utils;

import de.sanj0.chessian.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChessianUtils {
    // O(n)
    // throws IllegalArgumentException when from-to range is illogical
    public static List<Integer> intArrayToList(final int[] ints, final int from, final int to) {
        final int arrayLength = ints.length;
        if (from < 0 || to < from || to > arrayLength) {
            throw new IllegalArgumentException("from-to range must be logical");
        }
        final List<Integer> list = new ArrayList<>(arrayLength);
        for (int i = from; i < to; i++) {
            list.add(ints[i]);
        }

        return list;
    }

    public static Map<Byte, List<CastleHelper.Castle>> copyCastleRightsMap(final Map<Byte, List<CastleHelper.Castle>> src) {
        final Map<Byte, List<CastleHelper.Castle>> dst = new HashMap<>(src.size());

        for (final Entry<Byte, List<CastleHelper.Castle>> e : src.entrySet()) {
            dst.put(e.getKey(), new ArrayList<>(e.getValue()));
        }

        return dst;
    }

    public static Move getMoveByDestination(final List<Move> moves, final int dst) {
        final int size = moves.size();

        for (int i = 0; i < size; i++) {
            final Move m = moves.get(i);
            if (m.getEnd() == dst) {
                return m;
            }
        }

        return null;
    }
}
