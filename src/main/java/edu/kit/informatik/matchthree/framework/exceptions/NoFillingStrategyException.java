package edu.kit.informatik.matchthree.framework.exceptions;

import edu.kit.informatik.matchthree.framework.FillingStrategy;

/**
 * Thrown to indicate that no {@link FillingStrategy} has been found for filling
 * a board with tokens.
 * 
 * @author IPD Koziolek
 */
public class NoFillingStrategyException extends IllegalStateException {
    private static final long serialVersionUID = -5618131703942295900L;

    /**
     * Constructs a new {@link NoFillingStrategyException}.
     */
    public NoFillingStrategyException() {
    }
}
