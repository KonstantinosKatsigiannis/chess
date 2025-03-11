package assignment;

/**
 * Entry point for the chess game application.
 * Creates and starts a new chess game with standard rules.
 */
public class Main {
    /**
     * The main method that starts the chess game.
     * Creates a new Game instance and starts the game loop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
} 