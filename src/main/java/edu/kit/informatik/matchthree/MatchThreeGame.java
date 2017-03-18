package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Game;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.*;

/**
 *
 */
public class MatchThreeGame implements Game {

    private static final int SCORECONSTANT1 = 3;
    private static final int SCORECONSTANT2 = 2;

    private final Board gameBoard;

    private Matcher moveMatcher;

    private int score;

    /**
     * @param board
     * @param matcher
     */
    public MatchThreeGame(final Board board, final Matcher matcher) {
        if (board != null && matcher != null) {
            this.gameBoard = board;
            //this.gameBoard.setFillingStrategy(new RandomStrategy());
            this.moveMatcher = matcher;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void initializeBoardAndStart() {
        Set<Position> changedPositions = this.gameBoard.moveTokensToBottom();
        this.gameBoard.fillWithTokens();
        findMatches(changedPositions);
    }

    @Override
    public void acceptMove(final Move move) {
       if (!move.canBeApplied(this.gameBoard)) {
           throw new BoardDimensionException("move not applicable on this board!");
       }
       move.apply(this.gameBoard);
       findMatches(move.getAffectedPositions(gameBoard));
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void setMatcher(final Matcher matcher) {
        if (matcher != null) {
            this.moveMatcher = matcher;
        } else {
            throw new NullPointerException();
        }
    }

    private void findMatches(Set<Position> changedPositions) {
        int moveScore = 0;
        int count = 1;

        Set<Set<Position>> matchedPositions = moveMatcher.matchAll(this.gameBoard, changedPositions);

        while (!matchedPositions.isEmpty()) {
            int matchScore = 0;
            matchedPositions.removeIf(positions -> positions.size() < SCORECONSTANT1);
            for (Set<Position> match : matchedPositions) {
                    matchScore += SCORECONSTANT1 + (match.size() - SCORECONSTANT1) * SCORECONSTANT2;
                    this.gameBoard.removeTokensAt(match);
            }
            moveScore += count * (matchScore * matchedPositions.size());
            matchedPositions = moveMatcher.matchAll(this.gameBoard, gameBoard.moveTokensToBottom());
            gameBoard.fillWithTokens();
            count++;
        }
        this.score += moveScore;
    }
}
