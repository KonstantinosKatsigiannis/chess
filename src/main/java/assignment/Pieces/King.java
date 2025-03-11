package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Represents a King chess piece.
 * A king can move one square in any direction (horizontally, vertically, or diagonally).
 * Note: Castling is not implemented in this version.
 */
@SuppressWarnings("Duplicates")
public class King extends Piece {
    /**
     * Creates a new King.
     *
     * @param color the color of the king (WHITE or BLACK)
     * @param location the initial position of the king
     * @param board the chess board this king belongs to
     */
    public King(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this king to a new location.
     * The move is valid if:
     * <ul>
     *     <li>The destination is exactly one square away in any direction</li>
     *     <li>The destination is either empty or contains an opponent's piece</li>
     * </ul>
     * Note: Castling is not implemented in this version.
     *
     * @param newLoc the destination location
     * @throws InvalidMoveException if the move violates any of the above rules
     */
    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        int rowDiff = Math.abs(newLoc.getRow() - location.getRow());
        int colDiff = Math.abs(newLoc.getColumn() - location.getColumn());
        //no need to check if path is free, since it only moves one square, so it skips to the null/enemy check
        if (rowDiff <= 1 && colDiff <= 1) {
            Piece targetPiece = board.getPieceAt(newLoc);
            if (targetPiece == null) { //checks if the destination is empty or an enemy piece
                board.movePiece(location, newLoc);
                return;
            } else if (targetPiece.getColor() != color) {
                board.movePieceCapturing(location, newLoc);
                return;
            }
            throw new InvalidMoveException("No civil wars here, try attacking the opponent instead.");
        }

        //Castling is not implemented
        
        throw new InvalidMoveException("King can only move one square in any direction. Castling is not implemented yet.");
    }

    /**
     * Returns a string representation of this king.
     * 'K' for white king, 'k' for black king.
     *
     * @return "K" for white king, "k" for black king
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "K" : "k";
    }
} 