package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Abstract base class for all chess pieces.
 * Provides common functionality and properties that all chess pieces share.
 * Each specific piece type (Pawn, Rook, etc.) extends this class and implements
 * its own movement rules.
 */
public abstract class Piece {
    protected final Color color; //this cannot be changed
    protected Location location;
    protected final Board board; //this cannot be changed

    /**
     * Creates a new chess piece.
     *
     * @param color the color of the piece (WHITE or BLACK)
     * @param location the initial position of the piece on the board
     * @param board the chess board this piece belongs to
     */
    public Piece(Color color, Location location, Board board) {
        this.color = color;
        this.location = location;
        this.board = board;
    }

    /**
     * Gets the color of this piece.
     *
     * @return the color of the piece (WHITE or BLACK)
     */
    public Color getColor() {
        return color;
    }

    /**
     * Updates the piece's location on the board.
     * This method is called internally by the Board class when moving pieces.
     *
     * @param location the new location of the piece
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Attempts to move this piece to a new location on the board.
     * Each piece type implements its own movement rules in this method.
     *
     * @param newLoc the destination location for the piece
     * @throws InvalidMoveException if the move violates the piece's movement rules
     */
    @SuppressWarnings("unused")
    public abstract void moveTo(Location newLoc) throws InvalidMoveException; //just a declaration, it is implemented in each piece's class

    /**
     * Returns a string representation of this piece.
     * By convention, white pieces are represented by uppercase letters,
     * and black pieces by lowercase letters.
     *
     * @return a single character representing this piece
     */
    @Override
    public abstract String toString();
} 