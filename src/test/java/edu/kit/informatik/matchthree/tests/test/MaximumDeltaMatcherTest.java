package edu.kit.informatik.matchthree.tests.test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.MaximumDeltaMatcher;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.MatcherInitializationException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author David Oberacker
 */
public class MaximumDeltaMatcherTest {
    @Test
    public void match() throws Exception {
        Board board = new MatchThreeBoard(Token.set("An"), "An;An;AA");
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(0,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        System.out.println(mdm.match(board, new Position(0,2)));
    }
    @Test
    public void match2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("n0"), "n00;000;nn0");
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(1,0));
        deltaSet.add(new Delta(0,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        System.out.println(mdm.match(board, new Position(2,1)));
    }
    @Test
    public void match3() throws Exception {
        Board board = new MatchThreeBoard(Token.set("n0*"), "n00;0*0;nn0");
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(1,1));
        deltaSet.add(new Delta(-1,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        System.out.println(mdm.match(board, new Position(2,1)));
    }

    @Test
    public void matchAll() throws Exception {

    }

    @Test(expected = MatcherInitializationException.class)
    public void constructorExceptionTest1() throws Exception {
        Set<Delta> deltas = new HashSet<>();
        new MaximumDeltaMatcher(deltas);
    }
    @Test(expected = MatcherInitializationException.class)
    public void constructorExceptionTest2() throws Exception {
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(1,1));
        deltas.add(null);
        deltas.add(new Delta(1,1));
        new MaximumDeltaMatcher(deltas);
    }

    @Test(expected = MatcherInitializationException.class)
    public void constructorExceptionTest3() throws Exception {
        Set<Delta> deltas = new HashSet<>();
        deltas.add(new Delta(1,1));
        deltas.add(new Delta(0,0));
        deltas.add(new Delta(1,1));
        new MaximumDeltaMatcher(deltas);
    }
    @Test(expected = NullPointerException.class)
    public void constructorExceptionTest4() throws Exception {
        new MaximumDeltaMatcher(null);
    }

    @Test(expected = NullPointerException.class)
    public void matchExceptionTest1() throws Exception {
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(1,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        mdm.match(null, new Position(0,0));
    }

    @Test(expected = NullPointerException.class)
    public void matchExceptionTest2() throws Exception {
        Board board = new MatchThreeBoard(Token.set("n0*"), "n00;0*0;nn0");
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(1,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        mdm.match(board, null);
    }

    @Test(expected = BoardDimensionException.class)
    public void matchExceptionTest3() throws Exception {
        Board board = new MatchThreeBoard(Token.set("n0*"), "n00;0*0;nn0");
        Set<Delta> deltaSet = new HashSet<>();
        deltaSet.add(new Delta(1,1));
        MaximumDeltaMatcher mdm = new MaximumDeltaMatcher(deltaSet);
        mdm.match(board, new Position(3,3));
    }
}