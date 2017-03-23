package edu.kit.informatik.matchthree.tests.test;

import edu.kit.informatik.matchthree.*;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.DeterministicStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.*;
import edu.kit.informatik.matchthree.moves.RotateColumnDownMove;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

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

    @Test(expected = NullPointerException.class)
    public void constructorExceptionTest1() throws Exception {
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(null, new MaximumDeltaMatcher(deltas));
    }

    @Test(expected = NullPointerException.class)
    public void constructorExceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Game game = new MatchThreeGame(board, null);
    }

    @Test(expected = NullPointerException.class)
    public void acceptMoveexceptionTest1() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(board, new MaximumDeltaMatcher(deltas));
        game.acceptMove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void acceptMoveexceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), 2,2);
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(0,1));
        Game game = new MatchThreeGame(board, new MaximumDeltaMatcher(deltas));
        game.acceptMove(new RotateColumnDownMove(3));
    }

    @Test
    public void scoreCalcTestCase() throws Exception {
        Board board = new MatchThreeBoard(Token.set("AB"), "AAA;AAA;AAA");
        DeterministicStrategy deterministicStrategy = new DeterministicStrategy(Token.iterator("AAABAB"),
                Token.iterator("AAAABA"),Token.iterator("AAABAB"));
        board.setFillingStrategy(deterministicStrategy);
        Matcher matcher = new MaximumDeltaMatcher(new HashSet<>(Arrays.asList(new Delta(0,1), new Delta(1,0))));
        Game game = new MatchThreeGame(board, matcher);

        MoveFactory mf = new MoveFactoryImplementation();

        game.acceptMove(mf.flipRight(new Position(0,0)));
        assertEquals(45, game.getScore());
    }
}