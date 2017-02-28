package edu.kit.informatik.matchthree.framework.interfaces;

import java.util.Set;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;

/**
 * A method of finding matches on a {@linkplain Board board} starting with an
 * initial (set of) {@linkplain Position position(s)}.
 * 
 * @author IPD Koziolek
 */
public interface Matcher {
    /**
     * Returns all matches found on the board starting from the given initial
     * position.
     * 
     * @param board
     *            the board to match on
     * @param initial
     *            the position to start matching from. Must not be null.
     * @return all matches found
     * @throws BoardDimensionException
     *             if the initial position is not contained in the board
     */
    Set<Set<Position>> match(Board board, Position initial);

    /**
     * Returns all matches found on the board starting from the given
     * <strong>set of</strong> initial position.
     * <p>
     * If one of the given positions is not contained in the board, a
     * {@link BoardDimensionException} is thrown.
     *
     * @see #match(Board, Position)
     * 
     * @param board
     *            the board to match on
     * @param initial
     *            the positions to start matching from. Must not be null or
     *            empty.
     * @return all matches found
     * @throws BoardDimensionException
     *             if one of the initial positions is not contained in the board
     */
    Set<Set<Position>> matchAll(Board board, Set<Position> initial);
}
