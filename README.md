# Othello Game (Reversi)

Welcome to the Othello Game, a strategic two-player board game where you compete to capture and flip your opponent's pieces. This project is a Java-based implementation with a graphical interface, allowing a single player to play against the computer.

## Features

- **New Game**: Start a new game at any time and play on the traditional 8x8 Othello board.
- **Save and Load Games**: Save your game progress and continue where you left off by loading a previously saved game.
- **Valid Moves Only**: The game ensures that players can only place their pawns in valid spots according to the official Othello rules.
- **Automatic Flipping**: When a valid move is made, all opponent’s pieces between the newly placed pawn and existing ones are automatically flipped.
- **Game Over Detection**: The game detects when no more moves are available for either player and announces the winner based on the number of pieces on the board.
- **Friendly User Interface**: The interface is intuitive, allowing easy interaction between the player and the game board.

## How to Play

1. Start a new game.
2. Play alternately against the computer by clicking on valid board spots.
3. Watch as the pawns flip based on your moves.
4. Save your game progress at any time and load it back later.
5. The game ends when no valid moves remain, and the player with the most pieces on the board is declared the winner.

Enjoy the game!

## Technologies

- **Java (version 23.0.1)**: Core programming language for the game's logic and graphical interface.
- **Swing**: Java's graphical user interface (GUI) toolkit for rendering the game board and handling user interactions.
- **File I/O**: Used to save and load game progress.
