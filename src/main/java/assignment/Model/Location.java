package assignment.Model;

import assignment.Exceptions.InvalidLocationException;

/**
 * Represents a location on the chess board.
 * Handles both chess notation (e.g. "e4") and array indices (row/column) representations.
 * The board is represented as an 8x8 grid with rows 0-7 and columns 0-7,
 * corresponding to chess notation a1-h8.
 */
public class Location {
    /** The row index (0-7) corresponding to ranks 1-8 in chess notation */
    private final int row;
    /** The column index (0-7) corresponding to files a-h in chess notation */
    private final int column;

    /**
     * Creates a new Location from chess notation (e.g. "e4").
     *
     * @param position the position in chess notation (e.g. "e4")
     * @throws InvalidLocationException if the position is null, not 2 characters long,
     *         or contains invalid chess coordinates
     */
    public Location(String position) throws InvalidLocationException {
        if (position == null || position.length() != 2) {
            throw new InvalidLocationException("Invalid position format!");
        }
        
        char col = position.charAt(0);
        char row = position.charAt(1);
        
        if (col < 'a' || col > 'h' || row < '1' || row > '8') {
            throw new InvalidLocationException("Position out of bounds, stay inside the board!");
        }
        
        this.column = col - 'a';
        this.row = row - '1';
    } //analyzes the row and column input, converts chess notation to coordinates

    /**
     * Creates a new Location from array indices.
     *
     * @param row the row index (0-7)
     * @param column the column index (0-7)
     * @throws InvalidLocationException if row or column is outside the valid range (0-7)
     */
    public Location(int row, int column) throws InvalidLocationException {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new InvalidLocationException("Position out of bounds, stay inside the board!");
        }
        this.row = row;
        this.column = column;
    } //saves the row and column values assuming valid input

    /**
     * Gets the row index of this location.
     *
     * @return the row index (0-7)
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this location.
     *
     * @return the column index (0-7)
     */
    public int getColumn() {
        return column;
    }

    /**
     * Converts the location to chess notation.
     *
     * @return the location in chess notation (e.g. "e4")
     */
    public String toString() {
        return "" + (char)('a' + column) + (char)('1' + row);
    } //returns the text form of the position

    /**
     * Checks if this location is equal to another object.
     * Two locations are equal if they have the same row and column indices.
     *
     * @param obj the object to compare with
     * @return true if the locations are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location other)) return false;
        return row == other.row && column == other.column;
    } //used for move validation to check if two locations are the same
} 