package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

@SuppressWarnings("Duplicates")
public class Knight extends Piece {
    public Knight(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "N" : "n";
    }
} 