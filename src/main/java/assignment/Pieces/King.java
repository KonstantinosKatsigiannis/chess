package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

@SuppressWarnings("Duplicates")
public class King extends Piece {
    public King(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "K" : "k";
    }
} 