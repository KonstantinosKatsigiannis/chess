package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;
import assignment.Exceptions.InvalidLocationException;

/**
 * Represents a Pawn chess piece.
 * Pawns have the most complex movement rules:
 * - They can only move forward (direction depends on color)
 * - They can move one square forward to an empty square
 * - On their first move, they can optionally move two squares forward
 * - They can only capture diagonally
 * Note: En passant and promotion are not implemented in this version.
 */
public class Pawn extends Piece {
    /**
     * Creates a new Pawn.
     *
     * @param color the color of the pawn (WHITE or BLACK)
     * @param location the initial position of the pawn
     * @param board the chess board this pawn belongs to
     */
    public Pawn(Color color, Location location, Board board) {
        super(color, location, board);
    }

    /**
     * Attempts to move this pawn to a new location.
     * The move is valid if one of these conditions is met:
     * <ul>
     *     <li>Moving one square forward to an empty square</li>
     *     <li>Moving two squares forward from starting position to an empty square, with no pieces in between</li>
     *     <li>Moving one square diagonally forward to capture an opponent's piece</li>
     * </ul>
     * Note: En passant and promotion are not implemented in this version.
     *
     * @param newLoc the destination location
     * @throws InvalidMoveException if the move violates any of the above rules
     */
    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        int rowDiff = newLoc.getRow() - location.getRow();
        int colDiff = Math.abs(newLoc.getColumn() - location.getColumn());
        
        //White moves upwards, black moves downwards, no going back
        int direction = (color == Color.WHITE) ? 1 : -1;
        
        //Moving one-square
        if (colDiff == 0 && rowDiff == direction) {
            if (board.getPieceAt(newLoc) != null) {
                throw new InvalidMoveException("Pawns can only move to an empty spot.");
            }
            board.movePiece(location, newLoc);
            return;
        }
        
        //If pawn is still in its initial position, it can move two squares
        if (colDiff == 0 && rowDiff == 2 * direction) {
            if ((color == Color.WHITE && location.getRow() == 1) ||
                (color == Color.BLACK && location.getRow() == 6)) {
                if (board.getPieceAt(newLoc) != null) {
                    throw new InvalidMoveException("Pawns can only move to an empty spot.");
                }
                try {
                    Location intermediate = new Location(location.getRow() + direction, location.getColumn());
                    if (board.getPieceAt(intermediate) != null) {
                        throw new InvalidMoveException("Pawns cannot jump over other pieces.");
                    }
                    board.movePiece(location, newLoc);
                    return;
                } catch (InvalidLocationException e) {
                    throw new InvalidMoveException("Invalid intermediate location: " + e.getMessage());
                }
            }
        }
        
        //Pawns capture diagonally
        //En passant is not implemented
        if (colDiff == 1 && rowDiff == direction) {
            Piece targetPiece = board.getPieceAt(newLoc);
            if (targetPiece != null && targetPiece.getColor() != color) {
                board.movePieceCapturing(location, newLoc);
                return;
            }
            throw new InvalidMoveException("You need to attack an opponent for the pawn to move diagonally.");
        }

        //Promotion is not implemented
        
        throw new InvalidMoveException("Invalid pawn move. Pawns only move forwards. They can move 2 squares if they have not moved before, else they can only move one step. They can only capture enemy pieces diagonally, if they are one square away, and not if they are in front of them. En passant and promotion are not implemented yet.");
    }

    /**
     * Returns a string representation of this pawn.
     * 'P' for white pawn, 'p' for black pawn.
     *
     * @return "P" for white pawn, "p" for black pawn
     */
    @Override
    public String toString() {
        return color == Color.WHITE ? "P" : "p";
    }
} 