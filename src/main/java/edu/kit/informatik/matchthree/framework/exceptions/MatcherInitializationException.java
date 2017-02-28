package edu.kit.informatik.matchthree.framework.exceptions;

import edu.kit.informatik.matchthree.framework.interfaces.Matcher;

/**
 * Thrown to indicate that a {@link Matcher} constructor has been called with
 * erroneous parameters.
 * 
 * @author IPD Koziolek
 */
public class MatcherInitializationException extends IllegalArgumentException {
    private static final long serialVersionUID = 2598338044137955208L;

    /**
     * Constructs a new {@link MatcherInitializationException} with the given
     * detail message.
     * 
     * @param detailMessage
     *            the detail message for the newly constructed exception
     */
    public MatcherInitializationException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@link MatcherInitializationException} with no detail
     * message.
     */
    public MatcherInitializationException() {
    }
}