package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

/**
 * Represents a Queen chess piece.
 * A queen combines the movement abilities of a rook and bishop:
 * it can move any number of squares horizontally, vertically, or diagonally,
 * as long as the path is not blocked by other pieces.
 */
@SuppressWarnings("Duplicates")
public class Queen extends Piece {
    /**
     * Creates a new Queen.
     *
     * @param color the color of the queen (WHITE or BLACK)
     * @param location the initial position of the queen
     * @param board the chess board this queen belongs to
     */
    public Queen(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this queen to a new location.
     * The move is valid if:
     * <ul>
     *     <li>The destination is on a straight line (horizontal, vertical, or diagonal) from the current position</li>
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

        //first case deals with diagonal movement
        if (rowDiff == colDiff) {
            if (board.freeDiagonalPath(location, newLoc)) {
                Piece targetPiece = board.getPieceAt(newLoc);
                if (targetPiece == null) {
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

        //second case deals with horizontal movement
        if (newLoc.getRow() == location.getRow()) {
            if (board.freeHorizontalPath(location, newLoc)) {
                Piece targetPiece = board.getPieceAt(newLoc);
                if (targetPiece == null) {
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

        //third case deals with vertical movement
        if (newLoc.getColumn() == location.getColumn()) {
            if (board.freeVerticalPath(location, newLoc)) {
                Piece targetPiece = board.getPieceAt(newLoc);
                if (targetPiece == null) {
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
        
        throw new InvalidMoveException("Queen can only move horizontally, vertically, or diagonally.");
    }

    /**
     * Returns a string representation of this queen.
     * 'Q' for white queen, 'q' for black queen.
     *
     * @return "Q" for white queen, "q" for black queen
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "Q" : "q";
    }
} 