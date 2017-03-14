package edu.kit.informatik.matchthree.moves;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author David Oberacker
 */
public class RotateColumnDownMove implements Move {

    private final int coloumnIndex;
    private final int direction;

    public RotateColumnDownMove(final int coloumnIndex) {
        this(coloumnIndex, 1);
    }

    private RotateColumnDownMove(final int coloumnIndex, final int direction) {
        this.coloumnIndex = coloumnIndex;
        this.direction = direction;
    }

    /**
     * Determines whether the move can be applied to the given {@link Board} in
     * respect to the dimensions of the board.
     *
     * @param board
     *         the board to test
     *
     * @return <code>true</code> iff the move can be applied to the given board.
     */
    @Override
    public boolean canBeApplied(final Board board) {
        return board.getColumnCount() - 1 >= coloumnIndex;
    }

    /**
     * Applies the move to the given board.
     *
     * @param board
     *         the board to apply this move to.
     *
     * @throws BoardDimensionException
     *         if the move cannot be applied to the given board
     */
    @Override
    public void apply(final Board board) throws BoardDimensionException {
        if (!canBeApplied(board)) {
            throw new BoardDimensionException("column not on board!");
        }
        int count;
        if (direction >= 0) {
            count = 1;
        } else {
            count = board.getRowCount() -  1;
        }
        for (int cnt = 0; cnt < count; cnt++) {
            List<Token> columnTokens = new LinkedList<>();
            for (int i = 0; i < board.getRowCount(); i++) {
                columnTokens.add(board.getTokenAt(new Position(coloumnIndex, i)));
            }

            Token temp = columnTokens.get(columnTokens.size() - 1);
            for (int i = columnTokens.size() - 1; i > 0; i--) {
                columnTokens.set(i, columnTokens.get(i - 1));
            }
            columnTokens.set(0, temp);

            for (int i = 0; i < board.getRowCount(); i++) {
                board.setTokenAt(new Position(coloumnIndex, i), columnTokens.get(i));
            }
        }
    }

    /**
     * Returns a new {@link Move} that is the reverse of this move.
     * <p>
     * The notion of "reverse" is explained <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">on
     * the task sheet</a>.
     *
     * @return a reverse move to this move.
     */
    @Override
    public Move reverse() {
        return new RotateColumnDownMove(coloumnIndex, -1);
    }

    /**
     * Returns all positions on the given board that are affected by this move.
     *
     * @param board
     *         the board this move is to be applied to
     *
     * @return a set of all positions that are affected by this move
     *
     * @throws BoardDimensionException
     *         if the move cannot be applied to the given board
     */
    @Override
    public Set<Position> getAffectedPositions(final Board board) {
        Set<Position> changedPositions = new HashSet<>();
        for (int i = 0; i < board.getColumnCount() - 1; i++) {
            changedPositions.add(new Position(coloumnIndex, i));
        }
        return changedPositions;
    }
}
