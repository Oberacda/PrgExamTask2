package edu.kit.informatik.matchthree.framework.interfaces;

import java.util.Set;

import edu.kit.informatik.matchthree.framework.FillingStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.exceptions.NoFillingStrategyException;

/**
 * A rectangular square-tiled game board.
 * <p>
 * The board has a fixed number of rows and columns of <em>fields</em> which are
 * arranged in the shape of a matrix. It contains at least 2 columns and 2 rows.
 * The fields are identified by their row and column indices. The indices are
 * counted from the top left field starting with 0. A 3 by 3 field has the
 * following shape:
 * <table summary="" border="1px" cellpadding="5">
 * <tr>
 * <td>(0,0)</td>
 * <td>(1,0)</td>
 * <td>(2,0)</td>
 * </tr>
 * <tr>
 * <td>(0,1)</td>
 * <td>(1,1)</td>
 * <td>(2,1)</td>
 * </tr>
 * <tr>
 * <td>(0,2)</td>
 * <td>(1,2)</td>
 * <td>(2,2)</td>
 * </tr>
 * </table>
 * 
 * Each field can be empty or filled with one {@link Token}.
 * 
 * @author IPD Koziolek
 */
public interface Board {

    /**
     * Returns all {@link Token Tokens} that <em>can</em> be placed on the
     * board.
     * <p>
     * The result always contains at least two different {@link Token Tokens}.
     * The result must always the same for one instance of a {@link Board}.
     * 
     * @return all {@link Token Tokens} that can be placed on the board.
     */
    Set<Token> getAllValidTokens();

    /**
     * Returns the number of columns on the board.
     * <p>
     * Is always larger than or equal to 2. The result must always the same for
     * one instance of a {@link Board}.
     * 
     * @return the number of columns on the board.
     */
    int getColumnCount();

    /**
     * Returns the number of rows on the board.
     * <p>
     * Is always larger than or equal to 2. The result must always the same for
     * one instance of a {@link Board}.
     * 
     * @return the number of rows on the board.
     */
    int getRowCount();

    /**
     * Returns the {@link Token} at the given position.
     * 
     * @param position
     *            the position of the requested {@link Token}. The position must
     *            not be {@code null} and must be contained in the board, i.e.
     *            {@link #containsPosition(Position)} must be {@code true} for
     *            the given position.
     * @return the {@link Token} at the given {@link Position} or {@code null},
     *         if the position is empty.
     * @throws BoardDimensionException
     *             when the position is not on the board.
     */
    Token getTokenAt(Position position);

    /**
     * Sets or removes the {@link Token} at the given position.
     * <p>
     * If the given token is {@code null}, the field is empty after the call of
     * this method.
     * 
     * @param position
     *            the {@link Position} on the board to manipulate
     * @param newToken
     *            the {@link Token} that should be placed on the field or
     *            {@code null}
     * @throws BoardDimensionException
     *             if {@code position} is not contained in the board
     * @throws IllegalTokenException
     *             if the token is not valid for this board
     */
    void setTokenAt(Position position, Token newToken);

    /**
     * Indicates whether the given position is contained in the board.
     * 
     * @param position
     *            the position to check.
     * @return <code>true</code> iff <code>position</code> is contained in this
     *         board
     */
    boolean containsPosition(Position position);

    /**
     * Moves all tokens on the board to the maximum row position that can be
     * reached without skipping other tokens.
     * <p>
     * After this operation has finished there are only empty fields at the top
     * of the board, if there are any. Subsequent calls to this method have no
     * effect and always return an empty set.
     * 
     * @return the set of positions on the resulting board that have been
     *         modified by this operation. Never {@code null}. Might be an empty
     *         set.
     */
    Set<Position> moveTokensToBottom();

    /**
     * Swaps the tokens at the given positions.
     * <p>
     * If there is no token on one of the given positions, the token is moved
     * from the non-empty to the empty position. If both positions on the field
     * are empty, nothing happens.
     * <p>
     * Calling {@code swapTokens(a, b)} always has the same effect as
     * {@code swapTokens(b, a)}.
     * 
     * @param positionA
     *            the first position to swap
     * @param positionB
     *            the second position to swap
     * @throws BoardDimensionException
     *             if either of the positions is not contained in the board
     */
    void swapTokens(Position positionA, Position positionB);

    /**
     * Removes all tokens at the given positions.
     * <p>
     * If one of the positions is already empty, nothing happens to this field
     * and the other fields are handled normally. If one of the positions is not
     * contained in the board, a {@link BoardDimensionException} is thrown and
     * nothing happens (i.e. the state of the board is not changed by this
     * method).
     * 
     * @param positions
     *            The set of positions for which tokens should be removed. Must
     *            not contain {@code null} or a position that is not on the
     *            board.
     * @throws BoardDimensionException
     *             if one of the positions is not contained in the board.
     */
    void removeTokensAt(Set<Position> positions);

    /**
     * Sets a filling strategy for the board.
     * 
     * @param strategy
     *            the new filling strategy. Must not be {@code null}.
     */
    void setFillingStrategy(FillingStrategy strategy);

    /**
     * Fills the board with tokens using a previously set
     * {@link FillingStrategy}.
     * 
     * @throws NoFillingStrategyException
     *             if no {@link FillingStrategy} has been set using
     *             {@link #setFillingStrategy(FillingStrategy)} prior.
     */
    void fillWithTokens();

    /**
     * Returns a <em>token string</em> representation of the board.
     * <p>
     * A <em>token string</em> consists of a concatenation of the String
     * representations of the tokens in each row. Empty fields are denoted by a
     * space (" "). The rows are separated by semicolon ({@code ;}). There is no
     * trailing semicolon.
     * 
     * @return a token string representation of the board.
     */
    String toTokenString();
}