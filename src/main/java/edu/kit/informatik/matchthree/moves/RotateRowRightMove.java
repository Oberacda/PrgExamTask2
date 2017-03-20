package edu.kit.informatik.matchthree.moves;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.*;

/**
 * {@link Move} that allows to rotate all tokens in a row.
 * <p>
 * This move rotates all {@link edu.kit.informatik.matchthree.framework.Token tokens} at
 * the given row either right or left.
 * </p>
 * <p>
 * If the row isn't on the board the move isn't applicable.
 * </p>
 * <p>
 * For this move, {@code Move.apply(board)} and {@code Move.reverse().apply(board)} return different results!
 * </p>
 * <p>
 * In normal mode the tokens rotate <b>right</b>, in reverse mode the tokens rotate <b>left</b>!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class RotateRowRightMove implements Move {


    /**
     * Index of the row that should be rotated.
     */
    private final int rowIndex;

    /**
     * Determines if the rotation should be reverse or not.
     * <p>
     * {@literal true - the row rotates reverse (left)}.
     * </p>
     * <p>
     * {@literal false - the row rotates normal (right)}.
     * </p>
     */
    private final boolean reverse;

    /**
     * Creates a new instance of the {@link RotateRowRightMove}.
     * <p>
     * This {@link Move} is in normal mode and not in {@link Move#reverse() reverse mode}.
     * </p>
     *
     * @param rowIndex
     *         the index of the row that should be rotated.
     */
    public RotateRowRightMove(final int rowIndex) {
        this(rowIndex, false);
    }

    /**
     * Creates a new instance of the {@link RotateRowRightMove}.
     * <p>
     * The mode of this move depends on the reverse parameter.
     * (see: {@link RotateColumnDownMove#reverse}).
     * </p>
     *
     * @param rowIndex
     *         the index of the row that should be rotated.
     * @param reverse
     *         sets the direction of the move, see {@link RotateRowRightMove#reverse}.
     */
    private RotateRowRightMove(final int rowIndex, final boolean reverse) {
        this.rowIndex = rowIndex;
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
        Objects.requireNonNull(board, "Board is null!");
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
            throw new BoardDimensionException("Row not on board!");
        }
        int count = 1;
        if (reverse) {
            count = board.getColumnCount() - 1;
        }
        for (int cnt = 0; cnt < count; cnt++) {
            List<Token> rowTokens = new LinkedList<>();

            rowTokens.add(board.getTokenAt(new Position(board.getColumnCount() - 1, rowIndex)));
            for (int i = 0; i < board.getColumnCount(); i++) {
                rowTokens.add(board.getTokenAt(new Position(i, rowIndex)));
            }

            for (int i = 0; i < board.getColumnCount(); i++) {
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
        return new RotateRowRightMove(rowIndex, true);
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
        Objects.requireNonNull(board, "Board is null!");
        Set<Position> changedPositions = new HashSet<>();
        for (int i = 0; i < board.getColumnCount(); i++) {
            changedPositions.add(new Position(i, rowIndex));
        }
        if (!changedPositions.stream().allMatch(board::containsPosition)) {
            throw new BoardDimensionException("Position not on board!");
        }
        return changedPositions;
    }
}
