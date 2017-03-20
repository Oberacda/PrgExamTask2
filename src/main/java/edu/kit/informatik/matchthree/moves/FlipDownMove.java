package edu.kit.informatik.matchthree.moves;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link Move} that allows to switch two tokens that are underneath/above the other one.
 * <p>
 * This move switches the {@link edu.kit.informatik.matchthree.framework.Token Token} at
 * the given {@link Position} with the one directly underneath it.
 * </p>
 * <p>
 * If one of the {@link Position} isn't on the board given for the application,
 * this move isn't applicable.
 * </p>
 * <p>
 * For this move, {@code Move.apply(board)} and {@code Move.reverse().apply(board)} return the same result!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class FlipDownMove implements Move {
    /**
     * The position given to the move.
     */
    private final Position positionA;
    
    /**
     * The position direcly underneath {@link FlipDownMove#positionA}.
     * <p>
     * ({@literal positionA + "(0,1)" -> positionB})
     * </p>
     */
    private final Position positionB;

    /**
     * Creates a new instance of the {@link FlipDownMove Move}.
     * <p>
     * The given position should not be {@code null}.
     * </p>
     *
     * @param position
     *         a not-null position, that is the base for this
     *         move. All other required positions are dependent on this position.
     */
    public FlipDownMove(final Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position is null!");
        }
        this.positionA = position;
        this.positionB = position.plus(new Delta(0, 1));
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
        if (board == null) {
            throw new IllegalArgumentException("Board is null!");
        }
        return board.containsPosition(positionA) && board.containsPosition(positionB);
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
        if (canBeApplied(board)) {
            board.swapTokens(positionA, positionB);
        } else {
            throw new BoardDimensionException("One of the positions is not on board!");
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
        return new FlipDownMove(positionA);
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
        if (board == null) {
            throw new IllegalArgumentException("Board is null!");
        }
        Set<Position> changedPositions = new HashSet<>();
        changedPositions.add(positionA);
        changedPositions.add(positionB);
        if (!changedPositions.stream().allMatch(board::containsPosition)) {
            throw new BoardDimensionException("Position not on board!");
        }
        return changedPositions;
    }
}
