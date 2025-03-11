package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Represents a Knight chess piece.
 * A knight moves in an L-shape pattern: two squares in one direction (horizontally or vertically)
 * and then one square perpendicular to that direction.
 * Knights are unique in that they can jump over other pieces.
 */
@SuppressWarnings("Duplicates")
public class Knight extends Piece {
    /**
     * Creates a new Knight.
     *
     * @param color the color of the knight (WHITE or BLACK)
     * @param location the initial position of the knight
     * @param board the chess board this knight belongs to
     */
    public Knight(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this knight to a new location.
     * The move is valid if:
     * <ul>
     *     <li>The destination forms an L-shape from the current position (2 squares in one direction, 1 in perpendicular)</li>
     *     <li>The destination is either empty or contains an opponent's piece</li>
     * </ul>
     * Note: Knights can jump over other pieces, so path checking is not needed.
     *
     * @param newLoc the destination location
     * @throws InvalidMoveException if the move violates any of the above rules
     */
    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        int rowDiff = Math.abs(newLoc.getRow() - location.getRow());
        int colDiff = Math.abs(newLoc.getColumn() - location.getColumn());
        //no need to check if path is free, it can jump over other pieces
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) { //checks for L-shape move
            Piece targetPiece = board.getPieceAt(newLoc);
            if (targetPiece == null) { //checks if destination is empty or an enemy piece
                board.movePiece(location, newLoc);
                return;
            } else if (targetPiece.getColor() != color) {
                board.movePieceCapturing(location, newLoc);
                return;
            }
            throw new InvalidMoveException("No civil wars here, try attacking the opponent instead.");
        }
        
        throw new InvalidMoveException("Knight can only move in an L-shape.");
    }

    /**
     * Returns a string representation of this knight.
     * 'N' for white knight, 'n' for black knight.
     * Note: 'N' is used instead of 'K' to avoid confusion with the King.
     *
     * @return "N" for white knight, "n" for black knight
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "N" : "n";
    }
} 