package edu.kit.informatik.matchthree.framework.interfaces;

import edu.kit.informatik.matchthree.framework.Position;

/**
 * Creates moves with specific semantics.
 * <p>
 * The different moves are explained in detail <a href=
 * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on the
 * task sheet</a>.
 * 
 * @author IPD Koziolek
 */
public interface MoveFactory {
    /**
     * Returns a move that flips the given position with the one on its right.
     * <p>
     * The different moves are explained in detail <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @param position
     *            the position to use as origin for this move
     * @return a new flip right move
     */
    Move flipRight(Position position);

    /**
     * Returns a move that flips the given position with the one below.
     * <p>
     * The different moves are explained in detail <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @param position
     *            the position to use as origin for this move
     * @return a new flip down move
     */
    Move flipDown(Position position);

    /**
     * Returns a move that rotates a 2 by 2 square originating from the given
     * position clockwise.
     * <p>
     * The different moves are explained in detail <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @param position
     *            the position to use as origin for this move
     * @return a new rotate square clockwise move
     */
    Move rotateSquareClockwise(Position position);

    /**
     * Returns a move that rotates the given column down.
     * <p>
     * The different moves are explained in detail <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @param columnIndex
     *            the column to rotate
     * @return a new rotate column down move
     */
    Move rotateColumnDown(int columnIndex);

    /**
     * Returns a move that rotates the given row right.
     * <p>
     * The different moves are explained in detail <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @param rowIndex
     *            the row to rotate
     * @return a new rotate row right move
     */
    Move rotateRowRight(int rowIndex);
}
