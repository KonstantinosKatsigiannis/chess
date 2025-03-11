package assignment.Model;

import assignment.Exceptions.InvalidLocationException;

public class Location {
    private final int row;
    private final int column;

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

    public Location(int row, int column) throws InvalidLocationException {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new InvalidLocationException("Position out of bounds, stay inside the board!");
        }
        this.row = row;
        this.column = column;
    } //saves the row and column values assuming valid input

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String toString() {
        return "" + (char)('a' + column) + (char)('1' + row);
    } //returns the text form of the position

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Location other)) return false;
        return row == other.row && column == other.column;
    } //used for move validation to check if two locations are the same
} 