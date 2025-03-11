package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Represents a Rook chess piece.
 * A rook can move any number of squares horizontally or vertically,
 * as long as the path is not blocked by other pieces.
 * Note: Castling is not implemented in this version.
 */
@SuppressWarnings("Duplicates")
public class Rook extends Piece {
    /**
     * Creates a new Rook.
     *
     * @param color the color of the rook (WHITE or BLACK)
     * @param location the initial position of the rook
     * @param board the chess board this rook belongs to
     */
    public Rook(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this rook to a new location.
     * The move is valid if:
     * <ul>
     *     <li>The destination is on the same row or column as the current position</li>
     *     <li>The path to the destination is not blocked by other pieces</li>
     *     <li>The destination is either empty or contains an opponent's piece</li>
     * </ul>
     * Note: Castling is not implemented in this version.
     *
     * @param newLoc the destination location
     * @throws InvalidMoveException if the move violates any of the above rules
     */
    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        //first case deals with horizontal movement
        if (newLoc.getRow() == location.getRow()) {
            if (board.freeHorizontalPath(location, newLoc)) { //checks if path is free
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
            throw new InvalidMoveException("Another piece is in the way.");
        }
        //second case deals with vertical movement
        if (newLoc.getColumn() == location.getColumn()) {
            if (board.freeVerticalPath(location, newLoc)) { //checks if path is free
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
            throw new InvalidMoveException("Another piece is in the way.");
        }

        //Castling is not implemented
        
        throw new InvalidMoveException("Rook can only move horizontally or vertically. Castling is not implemented yet.");
    }

    /**
     * Returns a string representation of this rook.
     * 'R' for white rook, 'r' for black rook.
     *
     * @return "R" for white rook, "r" for black rook
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "R" : "r";
    }
} 