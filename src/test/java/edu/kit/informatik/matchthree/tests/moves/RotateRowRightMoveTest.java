package edu.kit.informatik.matchthree.tests.moves;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.moves.RotateColumnDownMove;
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

}