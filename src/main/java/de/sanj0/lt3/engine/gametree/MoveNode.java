package de.sanj0.lt3.engine.gametree;

import de.sanj0.lt3.move.Move;

import java.util.List;

public class MoveNode {

    private Move move;
    private List<MoveNode> children;
    private boolean rated;
    private int rating;

    public MoveNode(final List<MoveNode> children, final boolean rated, final int rating) {
        this.children = children;
        this.rated = rated;
        this.rating = rating;
    }

    public int rating() {
        return rated ? rating : 0;
    }

    public MoveNode correspondingChild(final Move m) {
        for (final MoveNode mn : children) {
            if (mn.move.equals(m)) {
                return mn;
            }
        }

        return null;
    }

    /**
     * Gets {@link #children}.
     *
     * @return the value of {@link #children}
     */
    public List<MoveNode> getChildren() {
        return children;
    }

    /**
     * Sets {@link #children}.
     *
     * @param children the new value of {@link #children}
     */
    public void setChildren(final List<MoveNode> children) {
        this.children = children;
    }

    /**
     * Gets {@link #rated}.
     *
     * @return the value of {@link #rated}
     */
    public boolean isRated() {
        return rated;
    }

    /**
     * Sets {@link #rated}.
     *
     * @param rated the new value of {@link #rated}
     */
    public void setRated(final boolean rated) {
        this.rated = rated;
    }

    /**
     * Gets {@link #rating}.
     *
     * @return the value of {@link #rating}
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets {@link #rating}.
     *
     * @param rating the new value of {@link #rating}
     */
    public void setRating(final int rating) {
        this.rating = rating;
    }
}
