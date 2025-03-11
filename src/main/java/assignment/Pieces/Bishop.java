package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Represents a Bishop chess piece.
 * A bishop can move any number of squares diagonally in any direction,
 * as long as the path is not blocked by other pieces.
 */
@SuppressWarnings("Duplicates")
public class Bishop extends Piece {
    /**
     * Creates a new Bishop.
     *
     * @param color the color of the bishop (WHITE or BLACK)
     * @param location the initial position of the bishop
     * @param board the chess board this bishop belongs to
     */
    public Bishop(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this bishop to a new location.
     * The move is valid if:
     * <ul>
     *     <li>The destination is on a diagonal from the current position</li>
     *     <li>The path to the destination is not blocked by other pieces</li>
     *     <li>The destination is either empty or contains an opponent's piece</li>
     * </ul>
     *
     * @param newLoc the destination location
     * @throws InvalidMoveException if the move violates any of the above rules
     */
    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        int rowDiff = Math.abs(newLoc.getRow() - location.getRow());
        int colDiff = Math.abs(newLoc.getColumn() - location.getColumn());
        
        if (rowDiff == colDiff) {
            if (board.freeDiagonalPath(location, newLoc)) { //first checks if the path is free
                Piece targetPiece = board.getPieceAt(newLoc);
                if (targetPiece == null) { //then checks if the destination is empty or an enemy piece
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
        
        throw new InvalidMoveException("Bishop can only move diagonally.");
    }

    /**
     * Returns a string representation of this bishop.
     * 'B' for white bishop, 'b' for black bishop.
     *
     * @return "B" for white bishop, "b" for black bishop
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "B" : "b";
    }
} 