package edu.kit.informatik.matchthree;

import edu.kit.informatik.matchthree.framework.FillingStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.exceptions.BoardDimensionException;
import edu.kit.informatik.matchthree.framework.exceptions.IllegalTokenException;
import edu.kit.informatik.matchthree.framework.exceptions.NoFillingStrategyException;
import edu.kit.informatik.matchthree.framework.exceptions.TokenStringParseException;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents a {@link Board} for the {@link MatchThreeGame}.
 * <p>
 *     This board can only be filled with tokens
 *     allowed during the constructor.
 * </p>
 *
 * @author David Oberacker
 * @version 1.0.1
 */
public class MatchThreeBoard implements Board {

    /**
     * Minimal amount of rows and columns a board has to have.
     */
    private static final Integer MIN_BOARD_SIZE = 2;

    /**
     * All allowed tokens on the board.
     */
    private final Set<Token> boardTokens;

    /**
     * The field that contains the board.
     * <p>
     * All keys for this map have to be set in the constructor.
     * If a position is not on the board after its initial construction
     * this position is invalid.
     * </p>
     * <p>
     * The entrys have to be optionals of tokens specified in the
     * {@link MatchThreeBoard#boardTokens} field.
     * </p>
     */
    private final TreeMap<Position, Optional<Token>> board;

    /**
     * The filling strategy for the board.
     * <p>
     *     This parameter is {@code null} in the beginning.
     * </p>
     */
    private FillingStrategy boardFillingStrategy = null;

    /**
     * Creates a new MatchThreeBoard with a specified amount of columns and rows.
     * <p>
     *     A emtpy board is a board with no tokens on all fields.
     * </p>
     *
     * @param tokens a set of all tokens that are allowed on the field.
     * @param columnCount the amount of columns on the board (min. 2).
     * @param rowCount the amount of rows on the board (min. 2).
     */
    public MatchThreeBoard(Set<Token> tokens, int columnCount, int rowCount) {
        this(tokens, createEmptyStringRepresentation(columnCount, rowCount));
    }

    /**
     * Creates a MatchThreeBoard from a token string.
     *  <p>
     * This string representation has the form: {@literal "-columns-;-columns;...;-columns-"}
     * , where the {@literal "-column-"} token gets repeated as many times as rows are specified.
     * The {@literal "-columns-"} token consists of as many tokens as there are columns.
     * </p>
     *
     * @param tokens a set of all tokens that are allowed on the field.
     * @param tokenString the token string the board should be build with.
     */
    public MatchThreeBoard(Set<Token> tokens, String tokenString) {
        this.boardTokens = Objects.requireNonNull(tokens, "Tokens is null!");
        if (this.boardTokens.size() < 2) {
            throw new IllegalArgumentException("Missing tokens! At least two tokens are required!");
        }
        this.board = getBoardFromString(tokens, tokenString);
    }

    @Override
    public Set<Token> getAllValidTokens() {
        return this.boardTokens;
    }

    @Override
    public int getColumnCount() {
        return this.board.descendingKeySet().parallelStream()
                .flatMapToInt(position -> IntStream.of(position.x)).max().getAsInt() + 1;
    }

    @Override
    public int getRowCount() {
        return this.board.descendingKeySet().parallelStream()
                .flatMapToInt(position -> IntStream.of(position.y)).max().getAsInt() + 1;
    }

    @Override
    public Token getTokenAt(Position position) throws BoardDimensionException {
        Objects.requireNonNull(position, "Position is null!");
        if (!containsPosition(position)) {
            throw new BoardDimensionException(String.format("Position \"%s\" is not on the board!"
                    , position.toString()));
        }
        return this.board.get(position).orElse(null);
    }

    @Override
    public void setTokenAt(Position position, Token newToken) throws BoardDimensionException, IllegalTokenException {
        if (newToken != null && !(this.boardTokens.contains(newToken))) {
            throw new IllegalTokenException(String.format("Unknown token \"%s\"!", newToken.toString()));
        }
        if (position == null) {
            throw new NullPointerException("Position is null!");
        }
        if (!containsPosition(position)) {
            throw new BoardDimensionException(String.format("Position \"%s\" is not on the board!"
                    , position.toString()));
        }

        this.board.put(position, Optional.ofNullable(newToken));
    }

    @Override
    public boolean containsPosition(Position position) {
        if (position == null) {
            throw new NullPointerException("Position is null!");
        }
        return this.board.containsKey(position);
    }

    @Override
    public Set<Position> moveTokensToBottom() {
        Set<Position> changedPositions = new LinkedHashSet<>();
        for (int i = 0; i < getColumnCount(); i++) {
            for (int k = getRowCount() - 1; k >= 0; k--) {
                Position cnt = new Position(i, k);
                if (!(this.board.get(cnt).isPresent())) {
                    for (int j = k; j >= 0; j--) {
                        Position nxt = new Position(i, j);
                        Optional<Token> nxtToken = this.board.get(nxt);
                        if (nxtToken.isPresent()) {
                            setTokenAt(cnt, nxtToken.get());
                            setTokenAt(nxt, null);
                            changedPositions.add(cnt);
                            changedPositions.add(nxt);
                            break;
                        }
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
        for (Position p : positions) {
            setTokenAt(p, null);
        }
    }

    @Override
    public void setFillingStrategy(FillingStrategy strategy) {
        this.boardFillingStrategy = Objects.requireNonNull(strategy, "Filling strategy is null!");
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
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                Optional<Token> tokenOptional = this.board.get(new Position(j, i));
                if (tokenOptional.isPresent()) {
                    result += tokenOptional.get().toString();
                } else {
                    result += "\u0020";
                }
            }
            result += ';';
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public String toString() {
        return String.format("\"%s\"", toTokenString());
    }

    /**
     * Returns a string representation of a empty board with a specified amount of columns and rows.
     * <p>
     * This string representation has the form: {@literal "-columns-;-columns;...;-columns-"}
     * , where the {@literal "-column-"} token gets repeated as many times as rows are specified.
     * The {@literal "-columns-"} token consists of the amount of whitespaces as there are columns.
     * </p>
     *
     * @param columnCount
     *         the amount of columns on the board.
     * @param rowCount
     *         the amount of rows on the board.
     *
     * @return a token string representation of a empty board.
     */
    private static String createEmptyStringRepresentation(final int columnCount, final int rowCount) {
        if (columnCount < MIN_BOARD_SIZE || rowCount < MIN_BOARD_SIZE) {
            throw new BoardDimensionException("Board is to small!");
        }
        String tokenString = "";
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tokenString += "\u0020";
            }
            tokenString += ";";
        }
        return tokenString.substring(0, tokenString.length() - 1);
    }

    /**
     * Uses a token string representation of a board to create a tree map of this board with
     * the tokens at their specified positions.
     *
     * @param tokenString
     *         the token string that should be displayed as a board
     *
     * @return a tree map representing the token string as a board.
     *
     * @throws TokenStringParseException
     *         if the token string doesn't match
     *         the specified requirements this exception is called.
     * @throws BoardDimensionException
     *         if the specified token string defined a board that is smaller than
     *         {@link MatchThreeBoard#MIN_BOARD_SIZE} or the rows hae different sizes this exception is thrown.
     */
    private static TreeMap<Position, Optional<Token>> getBoardFromString(final Set<Token> boardTokens,
                                                                  final String tokenString)
            throws TokenStringParseException, BoardDimensionException {
        Objects.requireNonNull(tokenString, "Token string is null!");
        Scanner semicolonScanner = new Scanner(tokenString);
        semicolonScanner.useDelimiter(";");
        List<List<Optional<Token>>> board = new LinkedList<>();

        // Read all rows from the token string.
        try {
            while (semicolonScanner.hasNext()) {
                List<Optional<Token>> rowToken = new LinkedList<>();
                String row = semicolonScanner.next();
                for (Character c : row.toCharArray()) {
                    //Checks if char is whitespace and iff adds empty field to board
                    if (c.equals('\u0020')) {
                        rowToken.add(Optional.empty());
                        continue;
                    }
                    //Checks if char is a valid token and iff adds token to board
                    Token stringToken = new Token(c);
                    if (boardTokens.parallelStream().anyMatch(stringToken::equals)) {
                        rowToken.add(Optional.of(stringToken));
                    } else {
                        throw new TokenStringParseException(String.format("Unknown token: \"%s\"", c));
                    }
                }
                board.add(rowToken);
            }
        } catch (NoSuchElementException nse) {
            throw new TokenStringParseException("Missing tokens in string representation!");
        }

        //Checks for wrong formatting
        if (tokenString.endsWith(";")) {
            throw new TokenStringParseException("Invalid token string, missing tokens!");
        }

        //Checks if board dimension is correct
        IntSummaryStatistics boardSummary = board.parallelStream()
                .flatMapToInt(optionals -> IntStream.of(optionals.size())).summaryStatistics();
        if (!(board.size() >= MIN_BOARD_SIZE
                && (boardSummary.getMax() == boardSummary.getMin())
                && boardSummary.getMax() >= MIN_BOARD_SIZE)) {
            throw new BoardDimensionException("Token string doesn't match board size requirements!");
        }

        //Creates the tree map with a comparator for rows.
        TreeMap<Position, Optional<Token>> result = new TreeMap<>((o1, o2) -> {
            if (Integer.compare(o1.y, o2.y) != 0) {
                return Integer.compare(o1.y, o2.y);
            } else {
                return Integer.compare(o1.x, o2.x);
            }
        });

        //Maps the List of Lists to a TreeMap.
        for (int i = 0; i < board.size(); i++) {
            List<Optional<Token>> tokenRow = board.get(i);
            for (int j = 0; j < tokenRow.size(); j++) {
                result.put(new Position(j, i), tokenRow.get(j));
            }
        }
        return result;
    }
}
