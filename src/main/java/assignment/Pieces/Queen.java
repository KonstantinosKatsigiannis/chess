package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

@SuppressWarnings("Duplicates")
public class Queen extends Piece {
    public Queen(Color color, Location location, Board board) {
        super(color, location, board);
    }

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

    @Override
    public String toString() {
        return color == Color.WHITE ? "Q" : "q";
    }
} 