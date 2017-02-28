package edu.kit.informatik.matchthree;

import java.util.Set;

import edu.kit.informatik.matchthree.framework.FillingStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.exceptions.NoFillingStrategyException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * 
 */
public class MatchThreeBoard implements Board {
    /**
     * 
     * @param tokens
     * @param columnCount
     * @param rowCount
     */
    public MatchThreeBoard(Set<Token> tokens, int columnCount, int rowCount) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param tokens
     * @param tokenString
     */
    public MatchThreeBoard(Set<Token> tokens, String tokenString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Token> getAllValidTokens() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Token getTokenAt(Position position) throws BoardDimensionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTokenAt(Position position, Token newToken) throws BoardDimensionException, IllegalTokenException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsPosition(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Position> moveTokensToBottom() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void swapTokens(Position positionA, Position positionB) throws BoardDimensionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeTokensAt(Set<Position> positions) throws BoardDimensionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFillingStrategy(FillingStrategy strategy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fillWithTokens() throws NoFillingStrategyException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toTokenString() {
        throw new UnsupportedOperationException();
    }
}
