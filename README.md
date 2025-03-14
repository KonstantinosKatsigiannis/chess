# Java Chess Game

A command-line chess game implementation in Java. This project provides a basic chess game where two players can play against each other, with the ability to save and load game states.

## Features

- Command-line interface with clear board visualization
- Basic chess piece movements
- Move validation for all pieces
- Save and load game functionality
- Turn-based gameplay
- Simple command system

## Current Limitations

- No implementation of special moves (castling, en passant, pawn promotion)
- No checkmate, stalemate or draw detection, game only ends via the `:x` command
- The save/load function does not save or load all the moves, but saves and loads the board state instead. This means the previous moves are lost

## How to Play

### Starting the Game

1. Compile and run the game
2. White player moves first
3. The board is displayed with coordinates:
    - Columns: a-h (left to right)
    - Rows: 1-8 (bottom to top)
    - Pieces are displayed as follows:
        - White pieces: P (Pawn), R (Rook), N (Knight), B (Bishop), Q (Queen), K (King)
        - Black pieces: p (pawn), r (rook), n (knight), b (bishop), q (queen), k (king)
        - Empty squares: .

### Making Moves

Enter moves in the format `fromTo` where:
- `from` is the starting position (e.g., "e2")
- `to` is the destination position (e.g., "e4")
  Example: `e2e4` moves the piece at e2 to e4

### Commands

- `:h` - Display help message
- `:s [filename]` - Save the current game to `filename.txt`
- `:o [filename]` - Open (load) a previously saved game from `filename.txt`
- `:x` - Exit the game

### Save/Load Feature

- Games are saved to `src/main/java/assignment/Saved Games/`
- The file is in a `.txt` format that easy to read and edit, so users can create a board state by simply editing it.
- Save your game before exiting using the `:s [filename]` command, otherwise it will be lost

## Project Structure

- `Model/`
    - `Board.java` - Manages the chess board and piece positions
    - `Location.java` - Handles chess coordinates and position validation
    - `Color.java` - Enum for piece colors
- `Pieces/`
    - `Piece.java` - Abstract base class for chess pieces
    - Individual piece classes (Pawn, Rook, Knight, Bishop, Queen, King)
- `Exceptions/`
    - Custom exceptions for invalid moves and locations
- `Saved Games/`
    - `[filename].txt` - A saved board configuration, used to load the game again. File can be easily edited.
- `Game.java` - Main game logic and user interface

## Documentation

The project includes comprehensive Javadoc documentation for all classes and methods. You can:

1. View the documentation directly in the source code
2. Generate HTML documentation from scratch, or use the already generated documentation in the `doc` directory
3. Open `doc/index.html` in your web browser to browse the generated documentation

The documentation covers:
- Detailed class descriptions
- Method parameters and return values
- Exception conditions
- Usage examples for game commands
- Implementation notes for special chess rules (or rules not implemented)

## Requirements

- Java Development Kit (JDK) 17 or higher
- Command-line interface/terminal

## How to Run

Run `Main.java` via an IDE like IntelliJ IDEA, or follow these steps from the command line.

1. Compile the project:
```bash
javac src/main/java/assignment/*.java src/main/java/assignment/Model/*.java src/main/java/assignment/Pieces/*.java src/main/java/assignment/Exceptions/*.java
```

2. Run the game (from the project root directory):
```bash
java -cp src/main/java assignment.Main
```