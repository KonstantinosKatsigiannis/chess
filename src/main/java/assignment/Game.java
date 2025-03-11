package assignment;

import assignment.Model.Board;
import assignment.Model.Location;
import assignment.Model.Color;
import assignment.Pieces.*;
import assignment.Exceptions.InvalidLocationException;

import java.io.*;
import java.util.Scanner;

public class Game {
    private Board board;
    private Color currentPlayer;
    private final Scanner scanner;
    private static final String SAVE_FILE = "src/main/java/assignment/Saved Games/chess_game.txt";

    public Game() {
        board = new Board();
        currentPlayer = Color.WHITE; //white always starts first, apparently for historical reasons and not racism
        scanner = new Scanner(System.in);
    }

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

    private void handleCommand(String command) {
        switch (command.toLowerCase()) {
            case ":h":
                printHelp();
                break;
            case ":s":
                saveGame();
                break;
            case ":o":
                openGame();
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

    public void saveGame() { //current implementation only allows for one saved game, and always loads from that
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            //first line in the saved file is the current player's turn
            writer.println(currentPlayer.name());
            //save board state for all pieces
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Location loc = new Location(i, j);
                    Piece piece = board.getPieceAt(loc);
                    if (piece != null) {
                        writer.println(i + "," + j + "," + piece.getClass().getSimpleName() + "," + piece.getColor());
                    }
                }
            }
            System.out.println("Game saved successfully.");
        } catch (IOException | InvalidLocationException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public void openGame() { //current implementation always loads from the one available file, user cannot choose
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String colorLine = reader.readLine();
            if (colorLine != null) {
                currentPlayer = Color.valueOf(colorLine);
                board = new Board(); //create an empty board
                
                String line;
                while ((line = reader.readLine()) != null) { //populate the board with the saved piece positions
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        String pieceType = parts[2];
                        Color pieceColor = Color.valueOf(parts[3]);
                        
                        Location loc = new Location(row, col);
                        Piece piece = createPiece(pieceType, pieceColor, loc);
                        if (piece != null) {
                            board.setPiece(loc, piece);
                        }
                    }
                }
                System.out.println("Game loaded successfully.");
            }
        } catch (IOException | InvalidLocationException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

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

    public boolean exitGame() {
        System.out.print("Are you sure you want to exit? Make sure to save your game before exiting. (y/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("y");
    }

    public void printHelp() {
        System.out.println("Available commands:");
        System.out.println(":h - Show this help message");
        System.out.println(":s - Save the current game");
        System.out.println(":o - Open the previously saved game");
        System.out.println(":x - Exit the game");
        System.out.println("\nMove format: 'e2e4' (from square to square). En passant, castling, and promotion are not implemented yet. No checks for a game winning scenario or draw are implemented yet, the user needs to exit the game manually.");
    }
} 