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
public class RotateRowRightMove implements Move {

    private final int rowIndex;
    private final int direction;

    public RotateRowRightMove(final int rowIndex) {
        this(rowIndex, 1);
    }

    private RotateRowRightMove(final int rowIndex, final int direction) {
        this.rowIndex = rowIndex;
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
        return board.getRowCount() - 1 >= rowIndex;
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
            throw new BoardDimensionException("row not on board!");
        }
        int count;
        if (direction >= 0) {
            count = 1;
        } else {
            count = board.getRowCount() -  1;
        }
        for (int cnt = 0; cnt < count; cnt++) {
            List<Token> rowTokens = new LinkedList<>();
            for (int i = 0; i < board.getRowCount(); i++) {
                rowTokens.add(board.getTokenAt(new Position(i, rowIndex)));
            }

            Token temp = rowTokens.get(rowTokens.size() - 1);
            for (int i = rowTokens.size() - 1; i > 0; i--) {
                rowTokens.set(i, rowTokens.get(i - 1));
            }
            rowTokens.set(0, temp);

            for (int i = 0; i < board.getRowCount(); i++) {
                board.setTokenAt(new Position(i, rowIndex), rowTokens.get(i));
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
        return new RotateRowRightMove(rowIndex, -1);
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
            changedPositions.add(new Position(i, rowIndex));
        }
        return changedPositions;
    }
}
