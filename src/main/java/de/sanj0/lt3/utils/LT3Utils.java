package de.sanj0.lt3.utils;

import de.edgelord.saltyengine.transform.Vector2f;
import de.sanj0.lt3.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LT3Utils {
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

    public static Vector2f rotatePoint(final Vector2f point, final double theta, final Vector2f anchor) {
        final double s = Math.sin(theta);
        final double c = Math.cos(theta);
        return new Vector2f((float) (c * (point.getX() - anchor.getX()) - s * (point.getY() - anchor.getY()) + anchor.getX()),
                (float) (s * (point.getX() - anchor.getX()) + c * (point.getY() - anchor.getY()) + anchor.getY()));
    }

    public static double[] reverseArray(final double[] arr) {
        final int l = arr.length;
        final double[] rev = new double[l];
        for (int i = 0; i < l / 2; i++) {
            rev[i] = arr[l - 1 - i];
            rev[l - 1 - i] = arr[i];
        }
        return rev;
    }

    public static int safeSubtract(final int a, final int b) {
        final long result = (long) a - (long) b;
        if (result < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else {
            return (int) result;
        }
    }
}
