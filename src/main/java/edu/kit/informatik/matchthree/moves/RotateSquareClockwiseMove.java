package edu.kit.informatik.matchthree.moves;

import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link Move} that allows to rotate a square of four tokens clockwise or counterclockwise.
 * <p>
 * This move rotates the {@link edu.kit.informatik.matchthree.framework.Token Token} in a square around
 * the given {@link Position} either clockwise or counterclockwise.
 * </p>
 *     If {@literal A} is the given {@link Position}, the positions {@literal B, C, D}
 *     are calculated dependent on A, in the way given below:
 *     <pre>
 *         ----------               ----------
 *         | A  | B  |              | C  | A  |
 *         -----------  -apply->    -----------
 *         | C  | D  |              | D  | B  |
 *         -----------              -----------
 *     </pre>
 * <p>
 * If one of the {@link Position} isn't on the board given for the application,
 * this move isn't applicable.
 * </p>
 * <p>
 * For this move, {@code Move.apply(board)} and {@code Move.reverse().apply(board)} return different results!
 * </p>
 *  <p>
 *     In normal mode the tokens rotate <b>clockwise</b>, in reverse mode the tokens rotate <b>counterclockwise</b>!
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.0
 */
public class RotateSquareClockwiseMove implements Move {

    /**
     * {@link Position} A on the example.
     * <p>
     *     All other positions are dependent on this one!
     * </p>
     */
    private final Position positionA;

    /**
     * {@link Position} B on the example.
     * <p>
     *     Position right next to {@literal positionA}.
     * </p>
     */
    private final Position positionB;

    /**
     * {@link Position} C on the example.
     * <p>
     *     Position underneath {@literal positionA}.
     * </p>
     */
    private final Position positionC;

    /**
     * {@link Position} D on the example.
     * <p>
     *     Position underneath {@literal positionB}.
     * </p>
     */
    private final Position positionD;

    /**
     *  Creates a new instance of the {@link RotateSquareClockwiseMove}.
     * <p>
     * This {@link Move} is in normal mode and not in {@link Move#reverse() reverse mode}.
     * </p>
     * <p>
     *     The parameter positionA should not be {@code null}.
     * </p>
     *
     * @param positionA the position all other positions are dependent on.
     */
    public RotateSquareClockwiseMove(final Position positionA) {
        this(positionA,
                positionA.plus(new Delta(1, 0)),
                positionA.plus(new Delta(0, 1)),
                positionA.plus(new Delta(1, 1)));
    }

    /**
     * Creates a new instance of the {@link RotateSquareClockwiseMove}.
     * <p>
     * This {@link Move} is in normal mode and not in {@link Move#reverse() reverse mode}.
     * </p>
     * <p>
     *     No parameter should not be {@code null}.
     * </p>
     * <p>
     *     No position here depends on another one, this positions can be chosen randomly,
     *     but are treated as if they had the order described above.
     * </p>
     * @param positionA {@link Position} representing position <b>A</b>
     * @param positionB {@link Position} representing position <b>B</b>
     * @param positionC {@link Position} representing position <b>C</b>
     * @param positionD {@link Position} representing position <b>D</b>
     */
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
