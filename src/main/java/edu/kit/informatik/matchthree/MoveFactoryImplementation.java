package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.framework.interfaces.MoveFactory;
import edu.kit.informatik.matchthree.moves.*;

/**
 * A MoveFactoryImplementation provides {@link Move move's}
 * for {@link edu.kit.informatik.matchthree.framework.interfaces.Board board's}.
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class MoveFactoryImplementation implements MoveFactory {

    /**
     * Creates a new Instance of a {@link MoveFactoryImplementation}.
     */
    public MoveFactoryImplementation() { }

    @Override
    public Move flipRight(Position position) {
        return new FlipRightMove(position);
    }

    @Override
    public Move flipDown(Position position) {
        return new FlipDownMove(position);
    }

    @Override
    public Move rotateSquareClockwise(Position position) {
        return new RotateSquareClockwiseMove(position);
    }

    @Override
    public Move rotateColumnDown(int columnIndex) {
        return new RotateColumnDownMove(columnIndex);
    }

    @Override
    public Move rotateRowRight(int rowIndex) {
        return new RotateRowRightMove(rowIndex);
    }
}
