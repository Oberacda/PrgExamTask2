package edu.kit.informatik.matchthree.framework.exceptions;

/**
 * Thrown to indicate that a method has been passed an illegal Token.
 * 
 * @author IPD Koziolek
 */
public class IllegalTokenException extends IllegalArgumentException {
    private static final long serialVersionUID = 7648642816334860417L;

    /**
     * Constructs a new {@link IllegalTokenException} with the given detail
     * message.
     * 
     * @param detailMessage
     *            the detail message for the newly constructed exception
     */
    public IllegalTokenException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@link IllegalTokenException} with no detail message.
     */
    public IllegalTokenException() {
    }
}
