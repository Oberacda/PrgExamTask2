package edu.kit.informatik.matchthree;

import java.util.Set;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;

/**
 *
 */
public class MaximumDeltaMatcher implements Matcher {
    /**
     * @param deltas
     */
    public MaximumDeltaMatcher(Set<Delta> deltas) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Set<Position>> match(Board board, Position initial) throws BoardDimensionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Set<Position>> matchAll(Board board, Set<Position> initial) throws BoardDimensionException {
        throw new UnsupportedOperationException();
    }
}
