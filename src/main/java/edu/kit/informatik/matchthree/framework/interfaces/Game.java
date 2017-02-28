package edu.kit.informatik.matchthree.framework.interfaces;

/**
 * A <em>match three</em> game.
 * <p>
 * A game always has an associated board that is used for playing. It executes
 * moves on the board and keeps score.
 * <p>
 * The interface is described in more detail on the <a href=
 * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">task
 * sheet</a>.
 * 
 * @author IPD Koziolek
 */
public interface Game {
    /**
     * Moves tokens on the associated board to the bottom
     * ({@link Board#moveTokensToBottom()}) and fills the board with tokens
     * ({@link Board#fillWithTokens()}). The resulting situation is immediately
     * considered for matching and scoring.
     */
    void initializeBoardAndStart();

    /**
     * Executes the given {@link Move} on the associated board.
     * <p>
     * The resulting situation is considered for matching and scoring, possibly
     * resulting in chain reactions.
     * 
     * @param move
     *            the {@link Move} to execute and score.
     */
    void acceptMove(Move move);

    /**
     * Returns the score of the game.
     * <p>
     * How the score is calculated is described in detail on the <a href=
     * "https://sdqweb.ipd.kit.edu/lehre/WS1617-Programmieren/final02.pdf">task
     * sheet</a>.
     * 
     * @return the score of the game
     */
    int getScore();

    /**
     * Sets a {@link Matcher} to use for finding matches on the board of this
     * game.
     * 
     * @param matcher
     *            the new {@link Matcher} to use. Must not be {@code null}.
     */
    void setMatcher(Matcher matcher);
}
