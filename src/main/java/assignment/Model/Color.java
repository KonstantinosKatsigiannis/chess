package assignment.Model;

/**
 * Represents the color of a chess piece.
 * In chess, pieces are either white or black, and players take turns moving their respective colored pieces.
 */
public enum Color {
    /** Represents the white pieces */
    WHITE,
    /** Represents the black pieces */
    BLACK;

    /**
     * Returns the opposite color, used for alternating turns in the game.
     *
     * @return the opposite color (BLACK for WHITE, WHITE for BLACK)
     */
    public Color nextColor() {
        return this == WHITE ? BLACK : WHITE;
    }
} 