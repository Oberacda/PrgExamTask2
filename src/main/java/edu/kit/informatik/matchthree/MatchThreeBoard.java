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

    private FillingStrategy boardFillingStrategy = null;

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
        return this.board.get(position.y).get(position.x).orElse(null);
    }

    @Override
    public void setTokenAt(Position position, Token newToken) throws BoardDimensionException, IllegalTokenException {
        if (!containsPosition(position)) {
            throw new BoardDimensionException("Specified position is not on the board!");
        }
        this.board.get(position.y).set(position.x,Optional.ofNullable(newToken));
    }

    @Override
    public boolean containsPosition(Position position) {
        return position.x >= 0
                && position.y >= 0
                && position.y <= getRowCount() - 1
                && position.x <= getColumnCount()- 1;
    }

    @Override
    public Set<Position> moveTokensToBottom() {
        Set<Position> changedPositions = new LinkedHashSet<>();
        for (int i = 0; i < getColumnCount(); i++) {
            for (int k = getRowCount() - 2; k >= 0; k--) {
                for (int j = k; j >= 0; j--) {
                    Optional<Token> cnt = this.board.get(j).get(i);
                    Optional<Token> nxt = this.board.get(j + 1).get(i);
                    if (!nxt.isPresent() && cnt.isPresent()) {
                        this.board.get(j).set(i, Optional.empty());
                        this.board.get(j + 1).set(i, cnt);
                        changedPositions.add(new Position(i, j));
                        changedPositions.add(new Position(i, j + 1));
                    }
                }
            }
        }
        return changedPositions;
    }

    @Override
    public void swapTokens(Position positionA, Position positionB) throws BoardDimensionException {
        Token tokenA = getTokenAt(positionA);
        Token tokenB = getTokenAt(positionB);
        setTokenAt(positionA, tokenB);
        setTokenAt(positionB, tokenA);
    }

    @Override
    public void removeTokensAt(Set<Position> positions) throws BoardDimensionException {
        if (!positions.parallelStream().allMatch(this::containsPosition)) {
            throw new BoardDimensionException("position is not on board!");
        }
        for (Position p : positions) {
            setTokenAt(p, null);
        }
    }

    @Override
    public void setFillingStrategy(FillingStrategy strategy) {
        this.boardFillingStrategy = strategy;
    }

    @Override
    public void fillWithTokens() throws NoFillingStrategyException {
        Optional<FillingStrategy> strategy = Optional.ofNullable(this.boardFillingStrategy);
        if (strategy.isPresent()) {
            strategy.get().fill(this);
        } else {
            throw new NoFillingStrategyException();
        }
    }

    @Override
    public String toTokenString() {
        String result = "";
        for (List<Optional<Token>> rows : this.board) {
            String row = "";
            for (Optional<Token> tokenOptional: rows){
                if (tokenOptional.isPresent()) {
                    row += tokenOptional.get().toString();
                } else {
                    row += "\u0020";
                }
            }
            result += row + ";";
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", toTokenString());
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

        if (rows.parallelStream().anyMatch(s -> s.length() < minBoardSize) || rows.size() < minBoardSize) {
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
