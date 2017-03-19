package edu.kit.informatik.matchthree.tests;

import edu.kit.informatik.matchthree.*;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.DeterministicStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Game;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.moves.RotateColumnDownMove;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author David Oberacker
 */
public class MatchThreeGameTest {
    @Test
    public void getScore() throws Exception {

    }


    @Test
    public void getMatches2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("A*X0"), "0*0;* *;0*0;0*0");
        DeterministicStrategy strat = new DeterministicStrategy(Token.iterator("A*X0"));
        strat.setTokenIteratorForColumn(0, Token.iterator("A0A**"));
        strat.setTokenIteratorForColumn(1, Token.iterator("*AXAXA"));
        strat.setTokenIteratorForColumn(2, Token.iterator("A**A*"));
        board.setFillingStrategy(strat);
        Set<Delta> deltaSet1 = new HashSet<>();
        deltaSet1.add(new Delta(0,1));
        Set<Delta> deltaSet2 = new HashSet<>();
        deltaSet2.add(new Delta(1,0));
        MultiMatcher mm = new MultiMatcher(new MaximumDeltaMatcher(deltaSet1), new MaximumDeltaMatcher(deltaSet2));

        MatchThreeGame game = new MatchThreeGame(board, mm);
        game.initializeBoardAndStart();
        System.out.println(game.getScore());
    }

    @Test
    public void validTest1() throws Exception {
        Board board = new MatchThreeBoard(Token.set("A*X0"), "0*0;***;0*0;0*0");
        DeterministicStrategy strat = new DeterministicStrategy(Token.iterator("A*X0"));
        strat.setTokenIteratorForColumn(0, Token.iterator("A0A**"));
        strat.setTokenIteratorForColumn(1, Token.iterator("AXAXA"));
        strat.setTokenIteratorForColumn(2, Token.iterator("A**A*"));
        board.setFillingStrategy(strat);
        Set<Delta> deltaSet1 = new HashSet<>();
        deltaSet1.add(new Delta(0,1));
        Set<Delta> deltaSet2 = new HashSet<>();
        deltaSet2.add(new Delta(1,0));

        MultiMatcher mm = new MultiMatcher(new MaximumDeltaMatcher(deltaSet1), new MaximumDeltaMatcher(deltaSet2));


        MatchThreeGame game = new MatchThreeGame(board, mm);
        MoveFactoryImplementation mfi = new MoveFactoryImplementation();
        Move m = mfi.rotateColumnDown(1);
        game.acceptMove(m);
        System.out.println(game.getScore());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest1() throws Exception {
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(null, new MaximumDeltaMatcher(deltas));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Game game = new MatchThreeGame(board, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void acceptMoveexceptionTest1() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(board, new MaximumDeltaMatcher(deltas));
        game.acceptMove(null);
    }

    @Test(expected = BoardDimensionException.class)
    public void acceptMoveexceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(board, new MaximumDeltaMatcher(deltas));
        game.acceptMove(new RotateColumnDownMove(3));
    }
}