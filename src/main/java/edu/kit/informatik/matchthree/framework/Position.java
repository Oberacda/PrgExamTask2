package edu.kit.informatik.matchthree.framework;

import java.util.Objects;

/**
 * A position.
 * <p>
 * A position has a <em>x</em>- and a <em>y</em>-coordinate. Two positions are
 * equal if both their coordinates are equal.
 * 
 * @author IPD Koziolek
 */
public final class Position {
    /**
     * The <em>x</em>-coordinate of the position.
     */
    public final int x;
    /**
     * The <em>y</em>-coordinate of the position.
     */
    public final int y;

    /**
     * Creates a new position with the given coordinates.
     * 
     * @param x
     *            the <em>x</em>-coordinate of the new position.
     * @param y
     *            the <em>y</em>-coordinate of the new position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    /**
     * Returns the distance in the <em>x</em>-coordinate between this and {@code
     * other}. Is always greater than or equal to zero.
     * 
     * @param other
     *            the position to compare to. Must not be {@code null}.
     * @return the distance in the <em>x</em>-coordinate
     */
    public int getDistanceX(Position other) {
        Objects.requireNonNull(other, "Position to calculate distance to must not be null");
        return (int) Math.abs((long) other.x - (long) this.x);
    }

    /**
     * Returns the distance in the <em>y</em>-coordinate between this and
     * {@code other}. Is always greater than or equal to zero.
     * 
     * @param other
     *            the position to compare to. Must not be {@code null}.
     * @return the distance in the <em>y</em>-coordinate
     */
    public int getDistanceY(Position other) {
        Objects.requireNonNull(other, "Position to calculate distance to must not be null");
        return (int) Math.abs((long) other.y - (long) this.y);
    }

    /**
     * Convenience method that returns a {@link Position} with the given
     * coordinates.
     * 
     * @param x
     *            the <em>x</em>-coordinate of the new position.
     * @param y
     *            the <em>y</em>-coordinate of the new position.
     * @return a {@link Position} with the given coordinates.
     */
    public static Position at(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns a new position that is shifted by the given deltas.
     * 
     * @param dx
     *            the delta in <em>x</em>-direction
     * @param dy
     *            the delta in <em>y</em>-direction
     * @return a new position that is shifted by the given deltas.
     * @throws ArithmeticException
     *             if the given delta cannot be added to the position without an
     *             integer overflow.
     */
    public Position plus(int dx, int dy) {
        int newX = Math.addExact(x, dx);
        int newY = Math.addExact(y, dy);
        return Position.at(newX, newY);
    }

    /**
     * Returns a new position that is shifted by the given delta.
     * 
     * @param delta
     *            the delta to shift by. Must not be null.
     * @return a new position that is shifted by the given delta.
     * @throws ArithmeticException
     *             if the given delta cannot be added to the position without an
     *             integer overflow.
     */
    public Position plus(Delta delta) {
        Objects.requireNonNull(delta, "Delta to shift by must not be null");
        return this.plus(delta.dx, delta.dy);
    }

    /**
     * Calculates the delta to the given position.
     * 
     * @param other
     *            the position to calculate the delta to
     * @return a delta to the given position
     */
    public Delta deltaTo(Position other) {
        Objects.requireNonNull(other, "Position to calculate delta to must not be null");

        int newX = Math.subtractExact(other.x, x);
        int newY = Math.subtractExact(other.y, y);
        return Delta.dxy(newX, newY);
    }
}
