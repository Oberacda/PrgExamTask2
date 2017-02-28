package edu.kit.informatik.matchthree.framework;

import java.util.Objects;

/**
 * A vector between two {@link Position Positions}.
 * 
 * @author IPD Koziolek
 */
public final class Delta {
    /**
     * The <em>x</em>-component of the vector.
     */
    public final int dx;
    /**
     * The <em>y</em>-component of the vector.
     */
    public final int dy;

    /**
     * Creates a new {@link Delta} with the given <em>x</em>- and
     * <em>y</em>-components.
     * 
     * @param dx
     *            the <em>x</em>-component of the vector to create.
     * @param dy
     *            the <em>y</em>-component of the vector to create.
     */
    public Delta(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Convenience method that returns a {@link Delta} with the given
     * <em>x</em>- and <em>y</em>-components.
     * <p>
     * {@code dx} and {@code dy} must not both be 0.
     * 
     * @param dx
     *            the <em>x</em>-component of the vector to create.
     * @param dy
     *            the <em>y</em>-component of the vector to create.
     * @return a new {@link Delta}.
     */
    public static Delta dxy(int dx, int dy) {
        return new Delta(dx, dy);
    }

    @Override
    public String toString() {
        return "d(" + dx + ", " + dy + ")";
    }

    /**
     * Returns a new {@link Delta} with components scaled by the given factor.
     * 
     * @param factor
     *            the factor to scale both components by
     * @return a new {@link Delta} scaled by the given factor
     * @throws ArithmeticException
     *             if the delta cannot be scaled by the given factor without an
     *             integer overflow.
     */
    public Delta scale(int factor) {
        int newDx = Math.multiplyExact(dx, factor);
        int newDy = Math.multiplyExact(dy, factor);
        return dxy(newDx, newDy);
    }

    /**
     * Returns a new {@link Delta} by adding the given delta to this delta.
     * 
     * @param other
     *            the {@link Delta} to add to this. Must not be null.
     * @return a new {@link Delta} which is the sum of both deltas.
     * @throws ArithmeticException
     *             if the delta cannot be added to the given delta without an
     *             integer overflow.
     */
    public Delta add(Delta other) {
        Objects.requireNonNull(other, "Delta to add must not be null.");

        int newDx = Math.addExact(dx, other.dx);
        int newDy = Math.addExact(dy, other.dy);
        return dxy(newDx, newDy);
    }

    /**
     * Returns a new {@link Delta} which is the negation of this delta.
     * 
     * @return a new {@link Delta} which is the negation of this delta.
     * @throws ArithmeticException
     *             if the delta cannot be negated without an integer overflow.
     *             This is the case if one or both of the components is
     *             {@link Integer#MIN_VALUE}.
     */
    public Delta negate() {
        return scale(-1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Delta))
            return false;
        Delta other = (Delta) obj;
        if (dx != other.dx)
            return false;
        if (dy != other.dy)
            return false;
        return true;
    }

}
