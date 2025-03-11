package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;
import assignment.Exceptions.InvalidLocationException;

public class Pawn extends Piece {
    public Pawn(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "P" : "p";
    }
} 