package edu.kit.informatik.matchthree.tests.test.moves;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.moves.RotateRowRightMove;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class RotateRowRightMoveTest {
    @Test
    public void apply() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move row = new RotateRowRightMove(0);
        row.apply(board);
        assertThat(board.toTokenString(), is("+*A;Y**;+*="));
    }

    @Test
    public void reverse() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move row = new RotateRowRightMove(0);
        row.apply(board);
        assertThat(board.toTokenString(), is("+*A;Y**;+*="));
        row.reverse().apply(board);
        assertThat(board.toTokenString(), is("*A+;Y**;+*="));
    }

    @Test(expected = NullPointerException.class)
    public void applyExceptionTest1() throws Exception {
        Move coloumn = new RotateRowRightMove(0);
        coloumn.apply(null);
    }

    @Test(expected = BoardDimensionException.class)
    public void applyExceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move coloumn = new RotateRowRightMove(3);
        coloumn.apply(board);
    }

    @Test(expected = BoardDimensionException.class)
    public void applyExceptionTest3() throws Exception {
        Board board = new MatchThreeBoard(Token.set("*A+=Y"), "*A+;Y**;+*=");
        Move coloumn = new RotateRowRightMove(-1);
        coloumn.apply(board);
    }
    @Test(expected = NullPointerException.class)
    public void getAffectedPositionsExceptionTest1() throws Exception {
        Move coloumn = new RotateRowRightMove(0);
        coloumn.getAffectedPositions(null);
    }
}