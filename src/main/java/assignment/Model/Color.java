package assignment.Model;

public enum Color {
    WHITE, BLACK;

    public Color nextColor() {
        return this == WHITE ? BLACK : WHITE;
    }
} 