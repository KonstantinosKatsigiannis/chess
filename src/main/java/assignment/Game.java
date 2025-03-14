package assignment;

import assignment.Model.Board;
import assignment.Model.Location;
import assignment.Model.Color;
import assignment.Pieces.*;
import assignment.Exceptions.InvalidLocationException;

import java.io.*;
import java.util.Scanner;

/**
 * Controls the flow of a chess game.
 * This class manages:
 * <ul>
 *     <li>The game board and its state</li>
 *     <li>Player turns (alternating between white and black)</li>
 *     <li>Move validation and execution</li>
 *     <li>Game commands (help, save, load, exit)</li>
 * </ul>
 * Note: Checkmate, stalemate, and draw conditions are not implemented in this version.
 */
public class Game {
    private Board board;
    private Color currentPlayer;
    private final Scanner scanner;
    private static final String SAVE_DIRECTORY = "src/main/java/assignment/Saved Games/";

    /**
     * Creates a new chess game with standard initial setup.
     * White player moves first, as per chess rules.
     */
    public Game() {
        board = new Board();
        currentPlayer = Color.WHITE; //white always starts first, apparently for historical reasons and not racism
        scanner = new Scanner(System.in);
    }

    /**
     * Starts and runs the main game loop.
     * The loop continues until the user explicitly exits using the ':x' command.
     * Each iteration:
     * <ul>
     *     <li>Displays the current board state</li>
     *     <li>Shows whose turn it is</li>
     *     <li>Processes user input (either a move or a command)</li>
     * </ul>
     */
    //Checkmate or draw checks are not implemented, game only stops if the user stops it
    @SuppressWarnings("InfiniteLoopStatement")
    public void play() {
        while (true) { //throws a warning because this while never ends unless an exception is thrown, this is expected as the only way to exit the game is via the :x command
            board.display();
            System.out.println(currentPlayer + "'s turn");
            
            String input = scanner.nextLine().trim();
            
            if (input.startsWith(":")) { //checks if input is a command or a move
                handleCommand(input);
            } else {
                handleMove(input);
            }
        }
    }

    /**
     * Processes game commands that start with ':'.
     * Available commands:
     * <ul>
     *     <li>:h - Display help information</li>
     *     <li>:s [filename] - Save the current game to specified file</li>
     *     <li>:o [filename] - Open (load) a previously saved game from specified file</li>
     *     <li>:x - Exit the game (with confirmation)</li>
     * </ul>
     *
     * @param command the command to process (must start with ':')
     */
    private void handleCommand(String command) {
        String[] parts = command.toLowerCase().split("\\s+", 2);
        String cmd = parts[0];
        String filename = parts.length > 1 ? parts[1].trim() : null;

        switch (cmd) {
            case ":h":
                printHelp();
                break;
            case ":s":
                if (filename == null) {
                    System.out.println("Please provide a filename to save the game (e.g., :s mygame)");
                    return;
                }
                saveGame(filename + ".txt");
                break;
            case ":o":
                if (filename == null) {
                    System.out.println("Please provide a filename to load the game (e.g., :o mygame)");
                    return;
                }
                openGame(filename + ".txt");
                break;
            case ":x":
                if (exitGame()) {
                    System.exit(0);
                }
                break;
            default:
                System.out.println("Invalid command. Type ':h' for help.");
        }
    }

    /**
     * Processes a move input from the user.
     * The move must be in the format "e2e4" (source square followed by destination square).
     * Validates that:
     * <ul>
     *     <li>The input format is correct</li>
     *     <li>There is a piece at the source location</li>
     *     <li>The piece belongs to the current player</li>
     *     <li>The move is valid according to chess rules</li>
     * </ul>
     *
     * @param moveString the move in chess notation (e.g., "e2e4")
     */
    public void handleMove(String moveString) {
        try {
            if (moveString.length() != 4) {
                System.out.println("Invalid move format! Format should be two pairs of coordinates, first the piece you want to move, and then the destination, for example 'e2e4'. Don't leave a space between the coordinates.");
                return;
            }

            //parse the input as coordinates
            Location from = new Location(moveString.substring(0, 2));
            Location to = new Location(moveString.substring(2, 4));

            Piece piece = board.getPieceAt(from);
            if (piece == null) {
                System.out.println("Choose a piece.");
                return;
            } else if (piece.getColor() != currentPlayer) {
                System.out.println("Choose your own piece, not an enemy one.");
                return;
            }

            //if piece selected is valid, move piece and continue to the other player's turn
            board.movePiece(from, to);
            currentPlayer = currentPlayer.nextColor();
            
        } catch (InvalidLocationException e) {
            System.out.println("Invalid location: " + e.getMessage());
        }
    }

    /**
     * Saves the current game state to a specified file.
     * The save format includes:
     * <ul>
     *     <li>The current player's turn</li>
     *     <li>The position and type of each piece on the board</li>
     * </ul>
     * 
     * @param filename the name of the file to save the game to
     */
    public void saveGame(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        try {
            // Create the directory if it doesn't exist
            new File(SAVE_DIRECTORY).mkdirs();
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(fullPath))) {
                //first line in the saved file is the current player's turn
                writer.println(currentPlayer.name());
                //save board state
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        Location loc = new Location(i, j);
                        Piece piece = board.getPieceAt(loc);
                        //write empty for null pieces, otherwise write piece info
                        if (piece == null) {
                            writer.println(i + "," + j + ",empty,none");
                        } else {
                            writer.println(i + "," + j + "," + piece.getClass().getSimpleName() + "," + piece.getColor());
                        }
                    }
                }
                System.out.println("Game saved successfully to " + filename);
            }
        } catch (IOException | InvalidLocationException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads a previously saved game state from a specified file.
     * Restores:
     * <ul>
     *     <li>The current player's turn</li>
     *     <li>All pieces and their positions on the board</li>
     * </ul>
     * 
     * @param filename the name of the file to load the game from
     */
    public void openGame(String filename) {
        String fullPath = SAVE_DIRECTORY + filename;
        File saveFile = new File(fullPath);
        
        if (!saveFile.exists()) {
            System.out.println("Save file '" + filename + "' not found!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            String colorLine = reader.readLine();
            if (colorLine != null) {
                currentPlayer = Color.valueOf(colorLine);
                board = new Board(); // Create an empty board
                
                String line;
                while ((line = reader.readLine()) != null) { //populate the board with the saved piece positions
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        String pieceType = parts[2];
                        Location loc = new Location(row, col);
                        
                        // Handle empty squares explicitly
                        if (pieceType.equals("empty")) {
                            board.setPiece(loc, null);
                        } else {
                            Color pieceColor = Color.valueOf(parts[3]);
                            Piece piece = createPiece(pieceType, pieceColor, loc);
                            if (piece != null) {
                                board.setPiece(loc, piece);
                            }
                        }
                    }
                }
                System.out.println("Game loaded successfully from " + filename);
            }
        } catch (IOException | InvalidLocationException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    /**
     * Creates a new chess piece of the specified type.
     *
     * @param type the type of piece to create (Pawn, Rook, Knight, Bishop, Queen, or King)
     * @param color the color of the piece
     * @param location the location where the piece should be placed
     * @return the created piece, or null if the piece type is invalid
     */
    private Piece createPiece(String type, Color color, Location location) {
        return switch (type) {
            case "Pawn" -> new Pawn(color, location, board);
            case "Rook" -> new Rook(color, location, board);
            case "Knight" -> new Knight(color, location, board);
            case "Bishop" -> new Bishop(color, location, board);
            case "Queen" -> new Queen(color, location, board);
            case "King" -> new King(color, location, board);
            default -> null;
        };
    }

    /**
     * Prompts the user for confirmation before exiting the game.
     *
     * @return true if the user confirms they want to exit, false otherwise
     */
    public boolean exitGame() {
        System.out.print("Are you sure you want to exit? Make sure to save your game before exiting. (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y");
    }

    /**
     * Displays help information about available commands and game rules.
     */
    public void printHelp() {
        System.out.println("Available commands:");
        System.out.println(":h - Show this help message");
        System.out.println(":s [filename] - Save the current game (e.g., :s mygame)");
        System.out.println(":o [filename] - Open a saved game (e.g., :o mygame)");
        System.out.println(":x - Exit the game");
        System.out.println("\nMove format: 'e2e4' (from square to square). En passant, castling, and promotion are not implemented yet. No checks for a game winning scenario or draw are implemented yet, the user needs to exit the game manually.");
    }
} 