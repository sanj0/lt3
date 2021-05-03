package de.sanj0.lt3.engine.gametree;

import de.sanj0.lt3.move.Move;

import java.util.List;

public class GameTree {

    private int depth = 0;
    private MoveNode root;

    public GameTree(final int depth, final MoveNode root) {
        this.depth = depth;
        this.root = root;
    }

    public void walk(final Move m) {
        final MoveNode target = root.correspondingChild(m);
        if (target == null) {
            throw new IllegalArgumentException("move " + m.notation() + " not in game tree, cannot walk to!");
        }
        root = target;
    }

    public void walk(final List<Move> moves) {
        for (final Move m : moves) {
            walk(m);
        }
    }
}
