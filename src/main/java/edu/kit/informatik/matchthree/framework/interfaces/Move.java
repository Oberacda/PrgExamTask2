package edu.kit.informatik.matchthree.framework.interfaces;

import java.util.Set;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;

/**
 * A move that can be applied to a {@link Board}.
 * 
 * @author IPD Koziolek
 */
public interface Move {
    /**
     * Determines whether the move can be applied to the given {@link Board} in
     * respect to the dimensions of the board.
     * 
     * @param board
     *            the board to test
     * @return <code>true</code> iff the move can be applied to the given board.
     */
    boolean canBeApplied(Board board);

    /**
     * Applies the move to the given board.
     * 
     * @param board
     *            the board to apply this move to.
     * @throws BoardDimensionException
     *             if the move cannot be applied to the given board
     */
    void apply(Board board);

    /**
     * Returns a new {@link Move} that is the reverse of this move.
     * <p>
     * The notion of "reverse" is explained <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     * 
     * @return a reverse move to this move.
     */
    Move reverse();

    /**
     * Returns all positions on the given board that are affected by this move.
     * 
     * @param board
     *            the board this move is to be applied to
     * @return a set of all positions that are affected by this move
     * @throws BoardDimensionException
     *             if the move cannot be applied to the given board
     */
    Set<Position> getAffectedPositions(Board board);
}
