package edu.kit.informatik.matchthree.tests;

import static org.junit.Assert.assertEquals;

import edu.kit.informatik.matchthree.framework.DeterministicStrategy;
import edu.kit.informatik.matchthree.framework.FillingStrategy;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

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
public class MatchThreeBoardConstructorTest {
    @Test
    public void getAllValidTokens() throws Exception {

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
}
