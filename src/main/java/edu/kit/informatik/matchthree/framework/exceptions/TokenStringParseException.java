package edu.kit.informatik.matchthree.framework.exceptions;

/**
 * Thrown to indicate that a method has been passed an illegal String
 * representation of a playing board.
 * 
 * @author IPD Koziolek
 */
public class TokenStringParseException extends IllegalArgumentException {
    private static final long serialVersionUID = 6579120163272144512L;

    /**
     * Constructs a new {@link TokenStringParseException} with the given detail
     * message.
     * 
     * @param detailMessage
     *            the detail message for the newly constructed exception
     */
    public TokenStringParseException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@link TokenStringParseException} with no detail
     * message.
     */
    public TokenStringParseException() {
    }
}
