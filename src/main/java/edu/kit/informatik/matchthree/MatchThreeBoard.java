package edu.kit.informatik.matchthree;

import java.util.*;

import edu.kit.informatik.matchthree.framework.FillingStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.exceptions.NoFillingStrategyException;
import edu.kit.informatik.matchthree.framework.exceptions.TokenStringParseException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * 
 */
public class MatchThreeBoard implements Board {

    private static final Integer minBoardSize = 2;

    private final Set<Token> boardTokens;
    private final List<List<Optional<Token>>> board;

    /**
     * 
     * @param tokens
     * @param columnCount
     * @param rowCount
     */
    public MatchThreeBoard(Set<Token> tokens, int columnCount, int rowCount) {
        this(tokens, createEmptyStringRepresentation(columnCount, rowCount));
    }

    /**
     * 
     * @param tokens
     * @param tokenString
     */
    public MatchThreeBoard(Set<Token> tokens, String tokenString) {
        this.boardTokens = tokens;
        if (this.boardTokens.size() < 2) {
            throw new IllegalArgumentException("Missing tokens! At least two tokens are required!");
        }
        this.board = getBoardFromString(this.boardTokens, tokenString);
    }

    @Override
    public Set<Token> getAllValidTokens() {
        return this.boardTokens;
    }

    @Override
    public int getColumnCount() {
        return this.board.get(0).size();
    }

    @Override
    public int getRowCount() {
        return this.board.size();
    }

    @Override
    public Token getTokenAt(Position position) throws BoardDimensionException {
        if (!containsPosition(position)) {
            throw new BoardDimensionException("Specified position is not on the board!");
        }
        return this.board.get(position.x).get(position.y).orElse(null);
    }

    @Override
    public void setTokenAt(Position position, Token newToken) throws BoardDimensionException, IllegalTokenException {
        if (!containsPosition(position)) {
            throw new BoardDimensionException("Specified position is not on the board!");
        }
        this.board.get(position.x).set(position.y,Optional.ofNullable(newToken));
    }

    @Override
    public boolean containsPosition(Position position) {
        return position.x <= getRowCount() && position.y <= getColumnCount();
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

    private static List<List<Optional<Token>>> getBoardFromString(final Set<Token> tokens, final String boardString) {
        List<List<Optional<Token>>> board = new LinkedList<>();
        List<String> rows = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(boardString);
            scanner.useDelimiter(";");

            while (scanner.hasNext()) {
                rows.add(scanner.next());
            }
        } catch (NoSuchElementException nse) {
            throw new IllegalArgumentException("parsing error during board contruction!");
        }

        if (rows.parallelStream().noneMatch(s -> s.length() <= minBoardSize) || rows.size() < minBoardSize) {
            throw new BoardDimensionException(String.format("provided board is smaller than %1$dx%1$d fields!"
                    , minBoardSize));
        }
        for (String row:rows) {
            List<Optional<Token>> rowList = new LinkedList<>();
            for (Character c : row.toCharArray()) {
                Optional<Token> token = tokens.stream().filter(t -> c.toString()
                        .contentEquals(t.toString())).findFirst();
                if (token.isPresent() || c.equals('\u0020')) {
                    rowList.add(token);
                } else {
                    throw new TokenStringParseException(String.format("the char \"%s\" does't match a token!", c));
                }
            }
            board.add(rowList);
        }
        int boardSize = board.get(0).size();
        if (board.stream().anyMatch(list -> list.size() != boardSize)) {
            throw new TokenStringParseException();
        }
        return board;
    }

    private static String createEmptyStringRepresentation(final int columnCount, final int rowCount) {
        String tokenString = "";
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tokenString += "\u0020";
            }
            tokenString += ";";
        }
        return tokenString.substring(0, tokenString.length() - 1);
    }
}
