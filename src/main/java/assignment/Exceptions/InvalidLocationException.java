package assignment.Exceptions;

/**
 * Exception thrown when an invalid chess board location is specified.
 * This can occur in two cases:
 * <ul>
 *     <li>When the chess notation is invalid (e.g. not in format 'a1' to 'h8')</li>
 *     <li>When array indices are out of bounds (not in range 0-7)</li>
 * </ul>
 */
public class InvalidLocationException extends Exception {
    /**
     * Constructs a new InvalidLocationException with a detailed error message.
     *
     * @param message The detailed message explaining why the location is invalid
     */
    public InvalidLocationException(String message) {
        super(message);
    }
} 