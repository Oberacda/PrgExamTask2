package edu.kit.informatik.matchthree.framework;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a token on a game board.
 * <p>
 * A token has a string representation that is exactly one character long. The
 * string representation must not be a semicolon ({@code ;}) or whitespace.
 * <p>
 * Two tokens are equal if their string representation are equal.
 * <p>
 * Tokens are {@link Comparable compared} via {@link String#compareTo(String)}
 * on their string representations.
 * <p>
 * The string representation can be obtained using {@link #toString()}.
 * 
 * @author IPD Koziolek
 */
public final class Token implements Comparable<Token> {
    private final String theToken;

    /**
     * Creates a new token with the given string representation.
     * 
     * @param stringRepresentation
     *            the string representation of the newly created token. Must be
     *            of length 1 and not be a semicolon ({@code ;}) or whitespace.
     *            Must not be {@code null}.
     */
    public Token(String stringRepresentation) {
        Objects.requireNonNull(stringRepresentation, "String representation must not be null");
        if (stringRepresentation.length() != 1) {
            throw new IllegalArgumentException(stringRepresentation + " must be of length 1");
        }
        if (stringRepresentation.equals(";") || stringRepresentation.trim().isEmpty()) {
            throw new IllegalArgumentException("String representation must not be \";\" or whitespace.");
        }

        this.theToken = stringRepresentation;
    }

    /**
     * Creates a new token with a string representation that consists of the
     * given character.
     * 
     * @param tokenCharacter
     *            the character to use for the string representation. Must be of
     *            length 1 and not be a semicolon ({@code ;}) or whitespace.
     * @see #Token(String)
     */
    public Token(char tokenCharacter) {
        this(Character.toString(tokenCharacter));
    }

    @Override
    public int hashCode() {
        return Objects.hash(theToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Token)) {
            return false;
        }

        return theToken.equals(((Token) obj).theToken);
    }

    @Override
    public String toString() {
        return theToken;
    }

    @Override
    public int compareTo(Token o) {
        return toString().compareTo(o.toString());
    }

    /**
     * Convenience method for creating a {@link Stream} of {@link Token Tokens}
     * from a String.
     * 
     * Each character of the String (as obtained by
     * {@link CharSequence#chars()}) is one element in the returned Stream.
     * Empty tokens (whitespace) are returned as a <code>null</code> value.
     * 
     * @param tokenString
     *            the string of tokens that is split up and converted into
     *            tokens. Must not be {@code null}.
     * @return A stream of tokens generated from the given string.
     */
    public static Stream<Token> stream(String tokenString) {
        Objects.requireNonNull(tokenString, "Token string to stream must not be null");
        return tokenString.chars().filter(c -> !Character.isWhitespace(c))
                .mapToObj(it -> new String(new int[] {it}, 0, 1)).map(Token::new);
    }

    /**
     * Convenience method for creating a set of tokens from a string of tokens.
     * <p>
     * Recurring characters in the token string are ignored.
     * 
     * @param tokenString
     *            the token string to create the set from. Must not be null.
     * @return a set consisting of all tokens that are included in
     *         {@code tokenString}.
     */
    public static Set<Token> set(String tokenString) {
        Objects.requireNonNull(tokenString, "Token string to convert to set must not be null");
        return stream(tokenString).filter(Objects::nonNull).collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Convenience method for creating an iterator of tokens from a string of
     * tokens.
     * <p>
     * Spaces are returned as {@code null} values by the iterator.
     * 
     * @param tokenString
     *            the token string to create the iterator from. Must not be
     *            null.
     * @return an iterator of all tokens that are included in
     *         {@code tokenString}.
     * @see #stream(String)
     */
    public static Iterator<Token> iterator(String tokenString) {
        Objects.requireNonNull(tokenString, "Token string to convert to iterator must not be null");
        return stream(tokenString).iterator();
    }
}
