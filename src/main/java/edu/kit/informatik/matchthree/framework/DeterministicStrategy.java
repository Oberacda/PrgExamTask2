package edu.kit.informatik.matchthree.framework;

import static edu.kit.informatik.matchthree.framework.Position.at;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * A {@link FillingStrategy} that uses configurable {@link Token Tokens} as
 * input for columns.
 * <p>
 * An {@link Iterator} of {@link Token Tokens} can be set for each column. When
 * the board is to be filled ({@link #fill(Board)}), the tokens are taken from
 * the iterator of the respective column. The filling order is deterministic.
 * The fields are traversed starting from the bottom row to the top row and for
 * each row from left to the right.
 * <p>
 * Applying this strategy to the same board with the same configuration of the
 * strategy always yields the same result.
 * 
 * @author IPD Koziolek
 */
public final class DeterministicStrategy implements FillingStrategy {
    private final Map<Integer, Supplier<Token>> nextTokenProviders;

    /**
     * Creates a new instance with the given token iterators.
     * <p>
     * The iterators are assigned to the columns in the order they appear in the
     * arguments. They must not be {@code null}.
     * <p>
     * If no or not enough iterators are provided and the instance is used for
     * filling without calling {@link #setTokenIteratorForColumn(int, Iterator)}
     * first for the requested columns, an exception will be thrown.
     * 
     * @param tokenIterators
     *            the token iterators to use
     */
    @SafeVarargs
    public DeterministicStrategy(Iterator<Token>... tokenIterators) {
        nextTokenProviders = new HashMap<>();
        for (int iteratorColumn = 0; iteratorColumn < tokenIterators.length; iteratorColumn++) {
            setTokenIteratorForColumn(iteratorColumn, tokenIterators[iteratorColumn]);
        }
    }

    /**
     * Sets an {@link Iterator} of {@link Token Tokens} for the given column.
     * <p>
     * Note that the tokens are taken from the iterator on demand and not
     * buffered when calling this method. The given iterator must not return
     * {@code null} values. If the iterator does not return a valid token for
     * the given board when filling the board using {@link #fill(Board)} or if
     * the iterator has no next token, an exception is thrown.
     * 
     * @param column
     *            the column to set the iterator for. Must be greater than or
     *            equal to zero.
     * @param nextTokens
     *            The iterator of tokens to use next for the given column. Must
     *            not be {@code null}.
     */
    public void setTokenIteratorForColumn(int column, Iterator<Token> nextTokens) {
        Objects.requireNonNull(nextTokens, "Token iterator must not be null");
        ensureValidColumn(column);

        this.nextTokenProviders.put(column, () -> nextTokens.hasNext() ? nextTokens.next() : null);
    }

    @Override
    public void fill(Board board) {
        Objects.requireNonNull(board, "The board to fill must not be null");

        for (int column = 0; column < board.getColumnCount(); column++) {
            for (int row = board.getRowCount() - 1; row >= 0; row--) {
                if (board.getTokenAt(at(column, row)) == null) {
                    Token newToken = getNextTokenForColumn(column);
                    ensureValidToken(board, newToken);
                    board.setTokenAt(at(column, row), newToken);
                }
            }
        }
    }

    private void ensureValidToken(Board board, Token newToken) {
        if (!board.getAllValidTokens().contains(newToken)) {
            throw new IllegalTokenException("Token " + newToken + " is not valid for board.");
        }
    }

    private void ensureValidColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException(
                    "Invalid column number, must be greater than or equal to zero: " + column);
        }
    }

    private Token getNextTokenForColumn(int column) {
        Supplier<Token> tokenProvider = nextTokenProviders.getOrDefault(column, () -> null);
        Token nextToken = tokenProvider.get();
        Objects.requireNonNull(nextToken, "No next token found for column " + column);
        return nextToken;
    }
}
