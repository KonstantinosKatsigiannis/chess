package assignment.Model;

import assignment.Pieces.*;
import assignment.Exceptions.InvalidLocationException;
import java.io.Serializable;

public class Board implements Serializable {
    private final Piece[][] pieces;

    public Board() {
        pieces = new Piece[8][8];
        init();
    }

    public void init() {
        try {
            // Initialize pawns
            for (int i = 0; i < 8; i++) {
                pieces[1][i] = new Pawn(Color.WHITE, new Location(1, i), this);
                pieces[6][i] = new Pawn(Color.BLACK, new Location(6, i), this);
            }

            // Initialize Rooks
            pieces[0][0] = new Rook(Color.WHITE, new Location(0, 0), this);
            pieces[0][7] = new Rook(Color.WHITE, new Location(0, 7), this);
            pieces[7][0] = new Rook(Color.BLACK, new Location(7, 0), this);
            pieces[7][7] = new Rook(Color.BLACK, new Location(7, 7), this);

            // Initialize Knights
            pieces[0][1] = new Knight(Color.WHITE, new Location(0, 1), this);
            pieces[0][6] = new Knight(Color.WHITE, new Location(0, 6), this);
            pieces[7][1] = new Knight(Color.BLACK, new Location(7, 1), this);
            pieces[7][6] = new Knight(Color.BLACK, new Location(7, 6), this);

            // Initialize Bishops
            pieces[0][2] = new Bishop(Color.WHITE, new Location(0, 2), this);
            pieces[0][5] = new Bishop(Color.WHITE, new Location(0, 5), this);
            pieces[7][2] = new Bishop(Color.BLACK, new Location(7, 2), this);
            pieces[7][5] = new Bishop(Color.BLACK, new Location(7, 5), this);

            // Initialize Queens
            pieces[0][3] = new Queen(Color.WHITE, new Location(0, 3), this);
            pieces[7][3] = new Queen(Color.BLACK, new Location(7, 3), this);

            // Initialize Kings
            pieces[0][4] = new King(Color.WHITE, new Location(0, 4), this);
            pieces[7][4] = new King(Color.BLACK, new Location(7, 4), this);
        } catch (InvalidLocationException e) {
            // should never happen because of the hard-coded coordinates, it is here because the above Location() calls had an unhandled exception
            throw new RuntimeException("You've got bigger problems than you thought", e);
        }
    } //initializes the board state

    public Piece getPieceAt(Location loc) {
        return pieces[loc.getRow()][loc.getColumn()];
    } //returns the piece in loc

    public void movePiece(Location from, Location to) {
        Piece piece = pieces[from.getRow()][from.getColumn()];
        pieces[from.getRow()][from.getColumn()] = null;
        pieces[to.getRow()][to.getColumn()] = piece;
        piece.setLocation(to);
    } //move validity already checked, this just moves the piece and leaves nothing behind

    public void movePieceCapturing(Location from, Location to) {
        Piece piece = pieces[from.getRow()][from.getColumn()];
        pieces[from.getRow()][from.getColumn()] = null;
        pieces[to.getRow()][to.getColumn()] = piece;
        piece.setLocation(to);
    } //same as the previous one, but with an opponent's piece at 'to'

    public void setPiece(Location location, Piece piece) {
        pieces[location.getRow()][location.getColumn()] = piece;
    } //this one is only used when loading a game from a file, to set the pieces according to the saved board state

    public boolean freeHorizontalPath(Location from, Location to) {
        if (from.getRow() != to.getRow()) return false;
        
        int row = from.getRow();
        int start = Math.min(from.getColumn(), to.getColumn()) + 1;
        int end = Math.max(from.getColumn(), to.getColumn());
        
        for (int col = start; col < end; col++) {
            if (pieces[row][col] != null) return false;
        }
        return true;
    } //checks if horizontal path is free, used for rook and queen

    public boolean freeVerticalPath(Location from, Location to) {
        if (from.getColumn() != to.getColumn()) return false;
        
        int col = from.getColumn();
        int start = Math.min(from.getRow(), to.getRow()) + 1;
        int end = Math.max(from.getRow(), to.getRow());
        
        for (int row = start; row < end; row++) {
            if (pieces[row][col] != null) return false;
        }
        return true;
    } //same as the previous one but for vertical path, used for rook and queen

    public boolean freeDiagonalPath(Location from, Location to) { // no need for freeAntidiagonalPath, this already checks both directions
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getColumn() - from.getColumn();
        
        if (Math.abs(rowDiff) != Math.abs(colDiff)) return false;
        
        int rowStep = rowDiff > 0 ? 1 : -1;
        int colStep = colDiff > 0 ? 1 : -1;
        
        int row = from.getRow() + rowStep;
        int col = from.getColumn() + colStep;
        
        while (row != to.getRow() && col != to.getColumn()) {
            if (pieces[row][col] != null) return false;
            row += rowStep;
            col += colStep;
        }
        return true;
    } //same as the previous ones but for diagonal (and antidiagonal) paths, used for queen and bishop

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  a b c d e f g h\n");
        sb.append("  ---------------\n");
        for (int i = 7; i >= 0; i--) {
            sb.append(i + 1).append("|");
            for (int j = 0; j < 8; j++) {
                Piece piece = pieces[i][j];
                if (piece == null) {
                    sb.append(". ");
                } else {
                    sb.append(piece).append(" ");
                }
            }
            sb.append("|").append(i + 1).append("\n");
        }
        sb.append("  ---------------\n");
        sb.append("  a b c d e f g h");
        return sb.toString();
    } //returns the board state that is printed for the user

    public void display() {
        System.out.println(this);
    }

}