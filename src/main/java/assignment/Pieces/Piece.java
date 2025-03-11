package assignment.Pieces;

import assignment.Model.Board;
import assignment.Model.Color;
import assignment.Model.Location;
import assignment.Exceptions.InvalidMoveException;

public abstract class Piece {
    protected final Color color; //this cannot be changed
    protected Location location;
    protected final Board board; //this cannot be changed

    public Piece(Color color, Location location, Board board) {
        this.color = color;
        this.location = location;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @SuppressWarnings("unused")
    public abstract void moveTo(Location newLoc) throws InvalidMoveException; //just a declaration, it is implemented in each piece's class

    @Override
    public abstract String toString();
} 