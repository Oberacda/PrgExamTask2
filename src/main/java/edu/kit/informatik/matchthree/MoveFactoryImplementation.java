package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.framework.interfaces.MoveFactory;
import edu.kit.informatik.matchthree.moves.*;

/**
 *
 */
public class MoveFactoryImplementation implements MoveFactory {

    @Override
    public Move flipRight(Position position) {
        return new FilpRightMove(position);
    }

    @Override
    public Move flipDown(Position position) {
        return new FilpDownMove(position);
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
