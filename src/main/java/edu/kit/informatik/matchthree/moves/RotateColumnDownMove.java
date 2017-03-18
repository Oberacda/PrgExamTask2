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
 * {@link Move} that allows to rotate all tokens in a column.
 * <p>
 * This move rotates all {@link edu.kit.informatik.matchthree.framework.Token tokens} at
 * the given column either up or down.
 * </p>
 * <p>
 * If the column isn't on the board the move isn't applicable.
 * </p>
 * <p>
 * For this move, {@code Move.apply(board)} and {@code Move.reverse().apply(board)} return different results!
 *
 * </p>
 * <p>
 *     In normal mode the tokens rotate <b>down</b>, in reverse mode the tokens rotate <b>up</b>!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class RotateColumnDownMove implements Move {

    /**
     * Index of the column that should be rotated.
     */
    private final int coloumnIndex;

    /**
     * Determines if the rotation should be reverse or not.
     * <p>
     * {@literal true - the column rotates reverse (up)}.
     * </p>
     * <p>
     * {@literal false - the column rotates normal (down)}.
     * </p>
     */
    private final boolean reverse;

    /**
     * Creates a new instance of the {@link RotateColumnDownMove}.
     * <p>
     * This {@link Move} is in normal mode and not in {@link Move#reverse() reverse mode}.
     * </p>
     *
     * @param coloumnIndex
     *         the index of the column that should be rotated.
     */
    public RotateColumnDownMove(final int coloumnIndex) {
        this(coloumnIndex, false);
    }

    /**
     * Creates a new instance of the {@link RotateColumnDownMove}.
     * <p>
     * The mode of this move depends on the reverse parameter.
     * (see: {@link RotateColumnDownMove#reverse}).
     * </p>
     *
     * @param coloumnIndex
     *         the index of the column that should be rotated.
     * @param reverse
     *         sets the direction of the move, see {@link RotateColumnDownMove#reverse}.
     */
    private RotateColumnDownMove(final int coloumnIndex, final boolean reverse) {
        this.coloumnIndex = coloumnIndex;
        this.reverse = reverse;
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
        int count = 1;
        if (reverse) {
            count = board.getRowCount() - 1;
        }
        for (int cnt = 0; cnt < count; cnt++) {
            List<Token> columnTokens = new LinkedList<>();

            columnTokens.add(board.getTokenAt(new Position(coloumnIndex, board.getRowCount() - 1)));
            for (int i = 0; i < board.getRowCount() - 1; i++) {
                columnTokens.add(board.getTokenAt(new Position(coloumnIndex, i)));
            }

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
        return new RotateColumnDownMove(coloumnIndex, true);
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
        for (int i = 0; i < board.getRowCount() - 1; i++) {
            changedPositions.add(new Position(coloumnIndex, i));
        }
        return changedPositions;
    }
}
