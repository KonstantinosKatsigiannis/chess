package assignment.Exceptions;

/**
 * Exception thrown when an invalid chess move is attempted.
 * This can occur in various cases:
 * <ul>
 *     <li>When a piece attempts to move in a way that violates its movement rules</li>
 *     <li>When a piece attempts to move through other pieces (except for Knights)</li>
 *     <li>When a piece attempts to capture a piece of the same color</li>
 *     <li>When a pawn attempts to move diagonally without capturing</li>
 * </ul>
 */
public class InvalidMoveException extends Exception {
    /**
     * Constructs a new InvalidMoveException with a detailed error message.
     *
     * @param message The detailed message explaining why the move is invalid
     */
    public InvalidMoveException(String message) {
        super(message);
    }
} 