package de.sanj0.chessian.utils;

import de.sanj0.chessian.move.Move;

import java.util.ArrayList;
import java.util.List;

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

    // keeps the order
    public static List<Integer> movesToDestinationList(final List<Move> moves) {
        final int size = moves.size();
        final List<Integer> destinationList = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            destinationList.add(moves.get(i).getEnd());
        }

        return destinationList;
    }
}
