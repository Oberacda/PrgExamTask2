package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;
import javafx.geometry.Pos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author David Oberacker
 */
public class MultiMatcher implements Matcher {

    private final Matcher A;
    private final Matcher B;

    public MultiMatcher(final Matcher A, final Matcher B) {
        this.A = A;
        this.B = B;
    }

    /**
     * Returns all matches found on the board starting from the given initial
     * position.
     *
     * @param board
     *         the board to match on
     * @param initial
     *         the position to start matching from. Must not be null.
     *
     * @return all matches found
     *
     * @throws BoardDimensionException
     *         if the initial position is not contained in the board
     */
    @Override
    public Set<Set<Position>> match(final Board board, final Position initial)
            throws BoardDimensionException {
        Set<Set<Position>> result = new HashSet<>();
        result.addAll(A.match(board, initial));
        result.addAll(B.match(board, initial));
        return result;
    }

    /**
     * Returns all matches found on the board starting from the given
     * <strong>set of</strong> initial position.
     * <p>
     * If one of the given positions is not contained in the board, a
     * {@link BoardDimensionException} is thrown.
     *
     * @param board
     *         the board to match on
     * @param initial
     *         the positions to start matching from. Must not be null or
     *         empty.
     *
     * @return all matches found
     *
     * @throws BoardDimensionException
     *         if one of the initial positions is not contained in the board
     * @see #match(Board, Position)
     */
    @Override
    public Set<Set<Position>> matchAll(final Board board, final Set<Position> initial) {
        Set<Set<Position>> matches = new HashSet<>();
        for (Position p : initial) {
            matches.addAll(this.match(board, p));
        }
        return matches;
    }
}
