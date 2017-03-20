package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.MatcherInitializationException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A MaximumDeltaMatcher is a {@link Matcher}, which finds its matches
 * with {@link Delta Delta's}.
 * <p>
 * A match is defined as a {@link Position}, which can be reached from a
 * previous match with a specified {@link Delta} and has the same {@link Token}
 * assigned, on the board, as the initial match.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.2
 */
public class MaximumDeltaMatcher implements Matcher {

    /**
     * The deltas to find the the new matches from existing ones.
     * <p>
     *     Only contains {@link Delta} which aren't {@literal null} or {@literal "(0,0)"}.
     * </p>
     */
    private final Set<Delta> deltas;

    /**
     * Creates a new {@link MaximumDeltaMatcher}, with the specified
     * {@link Delta delta's}.
     *
     * @param deltas
     *         the deltas that should be used to find the new matches.
     *         <p>
     *         This parameter should not contain the delta {@literal "(0,0)" or be "null"}.
     *         </p>
     *
     * @throws MatcherInitializationException
     *         If the parameter contains no valid deltas
     *         or is either {@literal null} or contatins the delta {@literal "(0,0)"}.
     */
    public MaximumDeltaMatcher(Set<Delta> deltas) throws MatcherInitializationException {
        Objects.requireNonNull(deltas, "Set of deltas is  null!");
        if (deltas.size() < 1) {
            throw new MatcherInitializationException("Set of deltas contains no deltas!");
        }
        if (deltas.contains(null) || deltas.contains(new Delta(0,0))) {
            throw new MatcherInitializationException("Set of deltas contains invalid deltas(null or (0,0))!");
        }
        this.deltas = deltas;
    }

    @Override
    public Set<Set<Position>> match(final Board board, final Position initial) throws BoardDimensionException {
        Objects.requireNonNull(board, "Board is null!");
        if (! board.containsPosition(initial)) {
            throw new BoardDimensionException(String.format("the position \"%s\" isn't on the board!"
                    , initial.toString()));
        }
        Set<Set<Position>> result = new HashSet<>();
        Token tokenType = board.getTokenAt(initial);
        if (tokenType == null) {
            result.add(new HashSet<>());
            return result;
        }

        Set<Position> matchedPositions = new HashSet<>();
        Set<Position> newMatchedPositions = new HashSet<>();
        matchedPositions.add(initial);

        do {
            matchedPositions.addAll(newMatchedPositions);
            newMatchedPositions.addAll(matchedPositions);
            for (Delta delta : this.deltas) {
                for (Position position : matchedPositions) {
                    Position plusDelta = position.plus(delta);
                    Position minusDelta = position.plus(delta.scale(-1));
                    if (board.containsPosition(plusDelta) && tokenType.equals(board.getTokenAt(plusDelta))) {
                        newMatchedPositions.add(plusDelta);
                    }
                    if (board.containsPosition(minusDelta) && tokenType.equals(board.getTokenAt(minusDelta))) {
                        newMatchedPositions.add(minusDelta);
                    }
                }
            }
        } while (! matchedPositions.equals(newMatchedPositions));


        result.add(matchedPositions);
        return result;
    }

    @Override
    public Set<Set<Position>> matchAll(Board board, Set<Position> initial) throws BoardDimensionException {
        Set<Set<Position>> matches = new HashSet<>();
        for (Position p : initial) {
            matches.addAll(this.match(board, p));
        }
        return matches;
    }
}
