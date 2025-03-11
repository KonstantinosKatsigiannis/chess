package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

@SuppressWarnings("Duplicates")
public class Bishop extends Piece {
    public Bishop(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "B" : "b";
    }
} 