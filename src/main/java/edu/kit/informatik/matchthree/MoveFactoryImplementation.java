package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.framework.interfaces.MoveFactory;

/**
 *
 */
public class MoveFactoryImplementation implements MoveFactory {
    /**
     * 
     */
    public MoveFactoryImplementation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move flipRight(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move flipDown(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move rotateSquareClockwise(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move rotateColumnDown(int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Move rotateRowRight(int rowIndex) {
        throw new UnsupportedOperationException();
    }

}
