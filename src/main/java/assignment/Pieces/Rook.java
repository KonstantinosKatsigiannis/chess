package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

@SuppressWarnings("Duplicates")
public class Rook extends Piece {
    public Rook(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "R" : "r";
    }
} 