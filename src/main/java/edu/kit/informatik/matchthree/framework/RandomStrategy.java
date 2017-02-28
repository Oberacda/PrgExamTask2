package edu.kit.informatik.matchthree.framework;

import static edu.kit.informatik.matchthree.framework.Position.at;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * A {@link FillingStrategy} that fills the board with random tokens.
 * <p>
 * The tokens are taken from the set of all valid tokens (determined by
 * {@link Board#getAllValidTokens()}).
 * <p>
 * Applying this strategy to the same board might yield different results.
 * 
 * @author IPD Koziolek
 */
public final class RandomStrategy implements FillingStrategy {
    private final Random random = new Random();

    @Override
    public void fill(Board board) {
        Objects.requireNonNull(board, "The board to fill must not be null");

        for (int column = 0; column < board.getColumnCount(); column++) {
            for (int row = board.getRowCount() - 1; row >= 0; row--) {
                if (board.getTokenAt(at(column, row)) == null) {
                    board.setTokenAt(at(column, row), randomToken(board));
                }
            }
        }
    }

    private Token randomToken(Board board) {
        Set<Token> validTokens = board.getAllValidTokens();
        return validTokens.stream().skip(random.nextInt(validTokens.size())).findFirst().get();
    }
}
