package edu.kit.informatik.matchthree.tests;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.MaximumDeltaMatcher;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
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

}