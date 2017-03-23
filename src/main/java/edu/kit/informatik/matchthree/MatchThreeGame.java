package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Game;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class describing a {@link Game} played on a {@link MatchThreeBoard}.
 * <p>
 * This game is played on a board and uses a {@link Matcher} to find
 * matches and remove them from the board and reward the player with
 * points for his score.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class MatchThreeGame implements Game {

    /**
     * Constant to calculate the score of a match.
     */
    private static final int SCORE_CONSTANT_3 = 3;

    /**
     * Constant to calculate the score of a match.
     */
    private static final int SCORE_CONSTANT_2 = 2;

    /**
     * Constant describing the min size of a match.
     */
    private static final int MIN_MATCH_SIZE = 3;

    /**
     * The board the game takes place on.
     * <p>
     * This board has to have a
     * {@link edu.kit.informatik.matchthree.framework.FillingStrategy} before the
     * {@link MatchThreeGame#initializeBoardAndStart()} method is called.
     * </p>
     */
    private final Board gameBoard;

    /**
     * The matcher used to find matches during the game.
     */
    private Matcher moveMatcher;

    /**
     * The score of the game.
     * <p>
     * It's updated everytime the {@link MatchThreeGame#findMatches(Set)} method
     * is called.
     * </p>
     * <p>
     * score = &sum; matchScores
     * </p>
     * <p>
     * matchscore = &sum;<sub>k=1</sub> <sup>numOfChainReactions</sup> ( k * moveScore)
     * [numOfChainReactions is the number of chain reactions during the match evaluation]
     * </p>
     * <p>
     * moveScore = &sum; {@link MatchThreeGame#SCORE_CONSTANT_3}
     * + (numOfTokensInMatch - {@link MatchThreeGame#SCORE_CONSTANT_3})
     * * {@link MatchThreeGame#SCORE_CONSTANT_2};
     * </p>
     */
    private int score;

    /**
     * Creates a new MatchThreeGame on a given board with a specified matcher to find
     * the matches on the board.
     * <p>
     * If one of the parameters is {@literal null} the constructor fails.
     * </p>
     * <p>
     * This constructor doesn't change the board in any way.
     * </p>
     *
     * @param board
     *         the board the game takes place on
     * @param matcher
     *         the matcher used to find matches on the board
     */
    public MatchThreeGame(final Board board, final Matcher matcher) {
        this.gameBoard = Objects.requireNonNull(board, "Board is null!");
        this.moveMatcher = Objects.requireNonNull(matcher, "Matcher is null!");
    }

    @Override
    public void initializeBoardAndStart() {
        Set<Position> changedPositions = this.gameBoard.moveTokensToBottom();
        for (int i = 0; i < this.gameBoard.getColumnCount(); i++) {
            for (int j = 0; j < this.gameBoard.getRowCount(); j++) {
                changedPositions.add(new Position(i, j));
            }
        }
        this.gameBoard.fillWithTokens();
        findMatches(changedPositions);
    }

    @Override
    public void acceptMove(final Move move) {
        Objects.requireNonNull(move, "Move is null!");
        if (!move.canBeApplied(this.gameBoard)) {
            throw new BoardDimensionException("Move not applicable on this board!");
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
        this.moveMatcher = Objects.requireNonNull(matcher, "Matcher is null!");
    }

    /**
     * Checks if the given positions contain any matches for the
     * {@link MatchThreeGame#moveMatcher} and if so removes them and
     * refills and evaluates the board again.
     * <p>
     * Before this methode is called the board has to have a
     * {@link edu.kit.informatik.matchthree.framework.FillingStrategy;},
     * or the method call will fail.
     * </p>
     * <p>
     * This method calculates the score of the move and the following chain reaction
     * and adds it to the {@link MatchThreeGame#score}.
     * </p>
     * <p>
     * If a position is not in the initial position set and is not reached during the evaluation
     * of the matcher, it will not be evaluated, even if it would be a match.
     * </p>
     *
     * @param initialPositions
     *         Changed positions on the board where the matcher starts its evaluation.
     */
    private void findMatches(Set<Position> initialPositions) {
        int moveScore = 0;
        int count = 1;
        Set<Position> changedPositions
                = Objects.requireNonNull(initialPositions, "Set of changed positions is null!");
        Set<Set<Position>> matchedPositions
                = moveMatcher.matchAll(this.gameBoard, changedPositions);

        while (!matchedPositions.isEmpty()) {
            int matchScore = 0;
            matchedPositions.removeIf(positions -> positions.size() < MIN_MATCH_SIZE);
            changedPositions = new HashSet<>();
            for (Set<Position> match : matchedPositions) {
                matchScore += SCORE_CONSTANT_3 + (match.size() - SCORE_CONSTANT_3) * SCORE_CONSTANT_2;
                this.gameBoard.removeTokensAt(match);
                changedPositions.addAll(match);
            }
            moveScore += count * (matchScore * matchedPositions.size());
            changedPositions.addAll(gameBoard.moveTokensToBottom());
            gameBoard.fillWithTokens();
            matchedPositions = moveMatcher.matchAll(this.gameBoard, changedPositions);
            count++;
        }
        this.score += moveScore;
    }
}
