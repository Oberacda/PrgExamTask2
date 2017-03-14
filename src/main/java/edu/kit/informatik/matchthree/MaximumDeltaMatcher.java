package edu.kit.informatik.matchthree;

import java.util.*;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.DeterministicStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;
import org.omg.PortableServer.POA;

/**
 *
 */
public class MaximumDeltaMatcher implements Matcher {

    private final Set<Delta> deltas;

    /**
     * @param deltas
     */
    public MaximumDeltaMatcher(Set<Delta> deltas) {
        this.deltas = deltas;
    }

    @Override
    public Set<Set<Position>> match(Board board, Position initial) throws BoardDimensionException {
        Set<Set<Position>> matches = new HashSet<>();
        Set<Position> deltaPositions = new HashSet<>();
        Set<Position> alreadyCheckedPositions = new HashSet<>();
        deltaPositions.add(initial);
        Iterator<Position> positionIterator = deltaPositions.iterator();
        Set<Position> deltaP = new HashSet<>();
        while (positionIterator.hasNext()) {
            Position p = positionIterator.next();
            alreadyCheckedPositions.add(p);
            Token tokenP = board.getTokenAt(p);
            for (Delta d : this.deltas) {
                Position x = p.plus(d);
                if (board.containsPosition(x) && board.getTokenAt(x).equals(tokenP)) {
                    deltaPositions.add(x);
                    deltaP.add(x);
                }
                Position y = p.plus(d.scale(-1));
                if (board.containsPosition(y)&& board.getTokenAt(y).equals(tokenP)) {
                    deltaPositions.add(y);
                    deltaP.add(y);
                }
            }
            deltaPositions.removeAll(alreadyCheckedPositions);
            positionIterator = deltaPositions.iterator();
        }
        matches.add(deltaP);
        return matches;
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
