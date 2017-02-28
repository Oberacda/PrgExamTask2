package edu.kit.informatik.matchthree.framework.exceptions;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * Thrown to indicate
 * <ul>
 * <li>that a method has been passed an illegal {@link Position}, i.e. one that
 * cannot be accessed on the relevant playing board, or</li>
 * <li>that the program tried to construct a {@link Board} with an illegal size,
 * i.e. width or height less than or equal to 2.</li>
 * </ul>
 * 
 * @author IPD Koziolek
 */
public class BoardDimensionException extends IllegalArgumentException {
    private static final long serialVersionUID = -1005432103085870770L;

    /**
     * Constructs a new {@link BoardDimensionException} with the given detail
     * message.
     * 
     * @param detailMessage
     *            the detail message for the newly constructed exception
     */
    public BoardDimensionException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@link BoardDimensionException} with no detail message.
     */
    public BoardDimensionException() {
    }
}
