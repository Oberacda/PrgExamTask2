package edu.kit.informatik.matchthree.moves;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.HashSet;
import java.util.Set;

/**
 * @author David Oberacker
 */
public class RotateSquareClockwiseMove implements Move {

    private final Position positionA;
    private final Position positionB;
    private final Position positionC;
    private final Position positionD;

    public RotateSquareClockwiseMove(final Position positionA) {
        this(positionA,
                positionA.plus(new Delta(1, 0)),
                positionA.plus(new Delta(0, 1)),
                positionA.plus(new Delta(1, 1)));
    }

    private RotateSquareClockwiseMove(final Position positionA,
                                      final Position positionB,
                                      final Position positionC,
                                      final Position positionD) {
        this.positionA = positionA;
        this.positionB = positionB;
        this.positionC = positionC;
        this.positionD = positionD;
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
        return board.containsPosition(positionA)
                && board.containsPosition(positionB)
                && board.containsPosition(positionC)
                && board.containsPosition(positionD);
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
            throw new BoardDimensionException("position not on board!");
        }
        board.swapTokens(positionA, positionB);
        board.swapTokens(positionC, positionD);
        board.swapTokens(positionA, positionD);
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
        return new RotateSquareClockwiseMove(positionB, positionA, positionD, positionC);
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
        changedPositions.add(positionA);
        changedPositions.add(positionB);
        changedPositions.add(positionC);
        changedPositions.add(positionD);
        return changedPositions;
    }
}
