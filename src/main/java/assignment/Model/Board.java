package assignment.Model;

import assignment.Pieces.*;
import assignment.Exceptions.InvalidLocationException;

/**
 * Represents a chess board and manages the placement and movement of pieces.
 * The board is represented as an 8x8 grid where each cell can contain a chess piece.
 * This class provides methods for initializing the board, moving pieces, and checking
 * if paths between locations are clear.
 */
public class Board {
    /** The 8x8 grid representing the chess board, where each cell can contain a piece */
    private final Piece[][] pieces;

    /**
     * Creates a new chess board and initializes it with pieces in their starting positions.
     */
    public Board() {
        pieces = new Piece[8][8];
        init();
    }

    /**
     * Initializes the chess board with all pieces in their standard starting positions.
     * White pieces are placed on ranks 1-2, black pieces on ranks 7-8.
     * The order from left to right is: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook.
     * Pawns occupy the second rank for white and seventh rank for black.
     */
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

    /**
     * Gets the piece at the specified location on the board.
     *
     * @param loc the location to check
     * @return the piece at the specified location, or null if the location is empty
     */
    public Piece getPieceAt(Location loc) {
        return pieces[loc.getRow()][loc.getColumn()];
    } //returns the piece in loc

    /**
     * Moves a piece from one location to another, assuming the move is valid.
     * The destination square must be empty.
     *
     * @param from the starting location of the piece
     * @param to the destination location
     */
    public void movePiece(Location from, Location to) {
        Piece piece = pieces[from.getRow()][from.getColumn()];
        pieces[from.getRow()][from.getColumn()] = null;
        pieces[to.getRow()][to.getColumn()] = piece;
        piece.setLocation(to);
    } //move validity already checked, this just moves the piece and leaves nothing behind

    /**
     * Moves a piece from one location to another, capturing any piece at the destination.
     * Similar to movePiece, but used when capturing an opponent's piece.
     *
     * @param from the starting location of the piece
     * @param to the destination location where an opponent's piece will be captured
     */
    public void movePieceCapturing(Location from, Location to) {
        Piece piece = pieces[from.getRow()][from.getColumn()];
        pieces[from.getRow()][from.getColumn()] = null;
        pieces[to.getRow()][to.getColumn()] = piece;
        piece.setLocation(to);
    } //same as the previous one, but with an opponent's piece at 'to'

    /**
     * Sets a piece at a specific location on the board.
     * Used primarily when loading a game from a file.
     *
     * @param location the location where to place the piece
     * @param piece the piece to place
     */
    public void setPiece(Location location, Piece piece) {
        pieces[location.getRow()][location.getColumn()] = piece;
    } //this one is only used when loading a game from a file, to set the pieces according to the saved board state

    /**
     * Checks if there are any pieces between two locations horizontally.
     * Used for validating rook and queen moves.
     *
     * @param from the starting location
     * @param to the ending location
     * @return true if the horizontal path is clear, false otherwise
     */
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

    /**
     * Checks if there are any pieces between two locations vertically.
     * Used for validating rook and queen moves.
     *
     * @param from the starting location
     * @param to the ending location
     * @return true if the vertical path is clear, false otherwise
     */
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

    /**
     * Checks if there are any pieces between two locations diagonally.
     * Used for validating bishop and queen moves.
     * This method handles both diagonal directions (/ and \).
     *
     * @param from the starting location
     * @param to the ending location
     * @return true if the diagonal path is clear, false otherwise
     */
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

    /**
     * Returns a string representation of the current board state.
     * The board is displayed with ranks 8-1 from top to bottom and files a-h from left to right.
     * White pieces are represented by uppercase letters, black pieces by lowercase letters.
     * Empty squares are represented by dots.
     *
     * @return a string representation of the board
     */
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

    /**
     * Displays the current board state to the console.
     */
    public void display() {
        System.out.println(this);
    }
}