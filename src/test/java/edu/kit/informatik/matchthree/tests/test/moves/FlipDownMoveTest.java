package edu.kit.informatik.matchthree.tests.test.moves;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.moves.FlipDownMove;
import org.junit.Test;

/**
 * @author David Oberacker
 */
public class FlipDownMoveTest {
    @Test
    public void canBeApplied() throws Exception {

    }

    @Test
    public void apply() throws Exception {

    }

    @Test
    public void reverse() throws Exception {

    }

    @Test
    public void getAffectedPositions() throws Exception {

    }

    @Test(expected = BoardDimensionException.class)
    public void applyExceptionTest1() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move rotate = new FlipDownMove(new Position(3,0));
        rotate.apply(board);
    }

    @Test(expected = BoardDimensionException.class)
    public void applyExceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move rotate = new FlipDownMove(new Position(0,-1));
        rotate.apply(board);
    }

    @Test(expected = NullPointerException.class)
    public void applyExceptionTest3() throws Exception {
        Move rotate = new FlipDownMove(new Position(1,0));
        rotate.apply(null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorTest1() throws Exception {
        Move rotate = new FlipDownMove(null);
    }
    @Test(expected = NullPointerException.class)
    public void getAffectedPositionsTest1() throws Exception {
        Move rotate = new FlipDownMove(new Position(1,0));
        rotate.getAffectedPositions(null);
    }
}