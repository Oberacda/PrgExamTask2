package edu.kit.informatik.matchthree.framework;

import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * A functional interface that represents one method ({@link #fill(Board)}) of
 * filling a {@link Board}.
 * 
 * @author IPD Koziolek
 */
@FunctionalInterface
public interface FillingStrategy {

    /**
     * Fills the playing board with {@code Tokens}. After calling this method,
     * {@link Board#getTokenAt(Position)} returns a valid, non-null
     * {@link Token} for every {@link Position} on the board.
     * 
     * @param board
     *            The board to fill with tokens. Must not be {@code null}.
     */
    void fill(Board board);
}