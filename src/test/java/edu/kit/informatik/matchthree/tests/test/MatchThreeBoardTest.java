package edu.kit.informatik.matchthree.tests.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import edu.kit.informatik.matchthree.framework.*;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.exceptions.NoFillingStrategyException;
import edu.kit.informatik.matchthree.framework.exceptions.TokenStringParseException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

import java.util.HashSet;
import java.util.Set;

/**
 * An exemplary test for {@link Board}.
 * <p>
 * Tests whether the constructor of {@link MatchThreeBoard} creates an instance
 * that returns correct values for:
 * <ul>
 * <li>{@link Board#getColumnCount()},</li>
 * <li>{@link Board#getRowCount()},</li>
 * <li>{@link Board#getAllValidTokens()}.</li>
 * </ul>
 * 
 * @author IPD Koziolek
 */
public class MatchThreeBoardTest {
    Board b1 = new MatchThreeBoard(Token.set("A*+=Y"), 3,3);
    Board b2 = new MatchThreeBoard(Token.set("A*+=Y"), 3,3);
    Board b3 = new MatchThreeBoard(Token.set("A*Y"), 6,4);
    Board b4 = new MatchThreeBoard(Token.set("A*+=Y"), 2,8);


    @Before
    public void setUp() throws Exception {
        b1.setFillingStrategy(new RandomStrategy());
        DeterministicStrategy deterministicStrategy
                = new DeterministicStrategy(Token.iterator("A*A*A*A*A*A")
                , Token.iterator("Y*A*A*Y*A*Y")
                , Token.iterator("A*A*A*A*A*A")
                , Token.iterator("Y*A*A*Y*A*Y")
                , Token.iterator("A*A*A*A*A*A")
                , Token.iterator("Y*A*A*Y*A*Y"));
        b3.setFillingStrategy(deterministicStrategy);
        b4.setFillingStrategy(new RandomStrategy());

        b3.fillWithTokens();
        b4.fillWithTokens();
    }

    @Test
    public void getAllValidTokens1() throws Exception {
        assertThat(b1.getAllValidTokens(),is(Token.set("A*+=Y")));
        assertThat(b2.getAllValidTokens(),is(Token.set("A*+=Y")));
        assertThat(b3.getAllValidTokens(),is(Token.set("A*Y")));
        assertThat(b4.getAllValidTokens(),is(Token.set("A*+=Y")));
    }

    @Test
    public void getColumnCount1() throws Exception {
        assertThat(b1.getColumnCount(),is(3));
        assertThat(b2.getColumnCount(),is(3));
        assertThat(b3.getColumnCount(),is(6));
        assertThat(b4.getColumnCount(),is(2));
    }

    @Test
    public void getRowCount1() throws Exception {
        assertThat(b1.getRowCount(),is(3));
        assertThat(b2.getRowCount(),is(3));
        assertThat(b3.getRowCount(),is(4));
        assertThat(b4.getRowCount(),is(8));
    }

    @Test
    public void getTokenAt1() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("Y")));
        assertThat(b3.getTokenAt(new Position(2,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(3,3)), is(new Token("Y")));
        assertThat(b3.getTokenAt(new Position(4,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(5,3)), is(new Token("Y")));

    }

    @Test
    public void setTokenAt1() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("A")));
        b3.setTokenAt(new Position(0,3), new Token("Y"));
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("Y")));
    }

    @Test
    public void containsPosition1() throws Exception {
        assertTrue(b1.containsPosition(new Position(0,0)));
        assertTrue(b1.containsPosition(new Position(2,2)));
        assertFalse(b1.containsPosition(new Position(3,2)));
        assertFalse(b1.containsPosition(new Position(2,3)));
        assertFalse(b1.containsPosition(new Position(-1,3)));
    }

    @Test(expected = NullPointerException.class)
    public void containsPosition2() throws Exception {
        b1.containsPosition(null);
    }

    @Test
    public void moveTokensToBottom1() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), "ABAB;    ; A B");
        Set<Position> changedPositions = new HashSet<>();
        changedPositions.add(new Position(0,0));
        changedPositions.add(new Position(1,0));
        changedPositions.add(new Position(2,0));
        changedPositions.add(new Position(3,0));
        changedPositions.add(new Position(1,1));
        changedPositions.add(new Position(3,1));
        changedPositions.add(new Position(0,2));
        changedPositions.add(new Position(2,2));
        assertThat(board.moveTokensToBottom(), is(changedPositions));
        assertEquals("    ; B B;AAAB", board.toTokenString());
    }

    @Test
    public void swapTokens1() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("Y")));
        b3.swapTokens(new Position(0,3), new Position(1,3));
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("Y")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("A")));
    }

    @Test(expected = BoardDimensionException.class)
    public void swapTokens2() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("Y")));
        b3.swapTokens(new Position(0,9), new Position(1,3));
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("Y")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("A")));
    }

    @Test(expected = NullPointerException.class)
    public void swapTokens3() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("Y")));
        b3.swapTokens(null, new Position(1,3));
        assertThat(b3.getTokenAt(new Position(0,3)), is(new Token("Y")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("A")));
    }

    @Test
    public void removeTokensAt1() throws Exception {
        assertThat(b3.getTokenAt(new Position(0,1)), is(new Token("A")));
        assertThat(b3.getTokenAt(new Position(1,3)), is(new Token("Y")));
        Set<Position> positionSet = new HashSet<>();
        positionSet.add(new Position(0,1));
        positionSet.add(new Position(1,3));
        b3.removeTokensAt(positionSet);
        assertNull(b3.getTokenAt(new Position(0,1)));
        assertNull(b3.getTokenAt(new Position(1,3)));
    }

    @Test
    public void getColumnCount() throws Exception {

    }

    @Test
    public void getRowCount() throws Exception {

    }

    @Test
    public void getTokenAt() throws Exception {

    }

    @Test
    public void setTokenAt() throws Exception {

    }

    @Test
    public void containsPosition() throws Exception {

    }

    @Test
    public void moveTokensToBottom() throws Exception {
        Board board = new MatchThreeBoard(Token.set("A+*Y"), "A AA;++  ; *A*;Y  Y");

        assertEquals(4, board.getColumnCount());
        assertEquals(4, board.getRowCount());
        assertEquals(Token.set("A+*Y"), board.getAllValidTokens());
        System.out.println(board.moveTokensToBottom());
        System.out.println(board);
    }

    @Test
    public void swapTokens() throws Exception {

    }

    @Test
    public void removeTokensAt() throws Exception {

    }

    @Test
    public void setFillingStrategy() throws Exception {

    }

    @Test
    public void fillWithTokens() throws Exception {
        Board board = new MatchThreeBoard(Token.set("A+*Y"), 5,5);
        FillingStrategy strategy = new RandomStrategy();
        board.setFillingStrategy(strategy);
        board.fillWithTokens();
        System.out.println(board);
        board = new MatchThreeBoard(Token.set("A+*Y"), 4,4);
        DeterministicStrategy detStrategy = new DeterministicStrategy(Token.iterator("A+*Y"));
        detStrategy.setTokenIteratorForColumn(0, Token.iterator("A+*Y"));
        detStrategy.setTokenIteratorForColumn(1, Token.iterator("A+*Y"));
        detStrategy.setTokenIteratorForColumn(2, Token.iterator("A+*Y"));
        detStrategy.setTokenIteratorForColumn(3, Token.iterator("A+*Y"));
        board.setFillingStrategy(detStrategy);
        board.fillWithTokens();
        System.out.println(board);
    }

    @Test
    public void toTokenString() throws Exception {

    }

    /**
     * Tests the constructor and getters of {@link Board} on the instance
     * created by {@code new MatchThreeBoard(Token.set("AB"), 2, 3);}.
     */
    @Test
    public void testAB23() {
        Board board = new MatchThreeBoard(Token.set("AB"), 2, 3);

        assertEquals(2, board.getColumnCount());
        assertEquals(3, board.getRowCount());
        assertEquals(Token.set("AB"), board.getAllValidTokens());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest1() throws Exception {
        Board mtb = new MatchThreeBoard(Token.set("A"),2,2);

    }

    @Test(expected = TokenStringParseException.class)
    public void constructorExceptionTest2() throws Exception {
        Board mtb = new MatchThreeBoard(Token.set("AB"),"AB;AC;AB");

    }

    @Test(expected = BoardDimensionException.class)
    public void constructorExceptionTest3() throws Exception {
        Board mtb = new MatchThreeBoard(Token.set("AB"),"AB;A;AB");

    }

    @Test(expected = NullPointerException.class)
    public void getTokenAtExceptionTest1() throws Exception {
        b1.getTokenAt(null);
    }

    @Test(expected = BoardDimensionException.class)
    public void getTokenAtExceptionTest2() throws Exception {
        b1.getTokenAt(new Position(0,3));
        b1.getTokenAt(new Position(3,0));
    }

    @Test(expected = NullPointerException.class)
    public void setTokenAtExceptionTest1() throws Exception {
        b1.setTokenAt(null, new Token("A"));
    }

    @Test(expected = BoardDimensionException.class)
    public void setTokenAtExceptionTest2() throws Exception {
        b1.setTokenAt(new Position(0,3), new Token("A"));
        b1.setTokenAt(new Position(3,0), new Token("A"));
    }

    @Test(expected = IllegalTokenException.class)
    public void setTokenAtExceptionTest3() throws Exception {
        b1.setTokenAt(null, new Token("Ö"));
    }

    @Test(expected = NullPointerException.class)
    public void containsPositionExceptionTest() throws Exception {
        b1.containsPosition(null);
    }

    @Test(expected = NullPointerException.class)
    public void swapTokensExceptionTest1() throws Exception {
        b1.swapTokens(null, new Position(0,0));
    }

    @Test(expected = NullPointerException.class)
    public void swapTokensExceptionTest2() throws Exception {
        b1.swapTokens(new Position(0,0), null);
    }

    @Test(expected = BoardDimensionException.class)
    public void swapTokensExceptionTest3() throws Exception {
        b1.swapTokens(new Position(0,0), new Position(3,0));
    }

    @Test(expected = BoardDimensionException.class)
    public void swapTokensExceptionTest4() throws Exception {
        b1.swapTokens(new Position(3,0), new Position(0,0));
    }

    @Test(expected = NullPointerException.class)
    public void removeTokensAtExceptionTest1() throws Exception {
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(0,0));
        positions.add(new Position(1,0));
        positions.add(null);
        positions.add(new Position(2,1));
        positions.add(new Position(2,2));
        b1.removeTokensAt(positions);
    }

    @Test(expected = BoardDimensionException.class)
    public void removeTokensAtExceptionTest2() throws Exception {
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(0,0));
        positions.add(new Position(1,0));
        positions.add(new Position(3,3));
        positions.add(new Position(2,1));
        positions.add(new Position(2,2));
        b1.removeTokensAt(positions);
    }

    @Test(expected = NullPointerException.class)
    public void setFillingStrategyExceptionTest1() throws Exception {
        b1.setFillingStrategy(null);
    }

    @Test(expected = NoFillingStrategyException.class)
    public void fillWithTokensExceptionTest1() throws Exception {
        b2.fillWithTokens();
    }
}
