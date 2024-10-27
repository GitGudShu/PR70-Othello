## **Othello (Reversi) Game**

### **Project Overview**
This project is a fully functional Othello (Reversi) game developed using Java, with a strong focus on Object-Oriented Programming (OOP) principles. The goal is to create a well-structured and maintainable codebase that demonstrates clean design, encapsulation, polymorphism, and effective use of Java’s built-in libraries like Swing for the graphical user interface.

### Technologies

- **Java (version 23.0.1)**: Core programming language for the game's logic and graphical interface.
- **Swing**: Java's graphical user interface (GUI) toolkit for rendering the game board and handling user interactions.
- **File I/O**: Used to save and load game progress.

### **Features**
- **Graphical Interface**: A user-friendly interface designed with Swing, featuring a visual board, interactive gameplay, and clear indicators for game states and valid moves.
- **Game Mechanics**:
  - Move validation and pawn flipping follow official Othello rules.
  - Supports skipped turns when no valid moves are available, with visual feedback to the player.
  - AI opponent with basic decision-making (chooses the first valid move) to challenge the user.
- **Save and Load System**:
  - Games can be saved to and loaded from XML files.
  - Saves are stored in a dedicated `saves` folder with a custom dialog for viewing and selecting previous games.
  - Each save file includes the current board state, scores, and the player's turn.
- **Reset Functionality**: The board can be reset to the initial state with a single button press.

### **File Structure**
- **`src/`**: Contains all the source code files.
  - `App.java`: The main class that initializes the game.
  - `Board.java`: Manages the graphical board and handles player interactions.
  - `Grid.java`: Controls the game logic, including move validation and board state.
  - `Cell.java`: Represents each cell on the game board.
  - `Sidebar.java`: Contains the control panel with options like Save, Load, and Reset.
  - `SaveManager.java`: Handles saving and loading game states using XML.
  - `SaveManagerWindow.java` and `LoadManagerWindow.java`: Custom dialogs for saving and loading.
  - `GameStatus.java`: A class to represent the current game state (used for saving and loading).
- **`saves/`**: Contains saved game files in XML format.
- **`public/`**: Stores additional assets like background images (`test.png`).

### **How to Play**
1. **Starting the Game**: Launch the game, and the Othello board will be displayed with the initial four pawns.
2. **Playing Moves**:
   - Click on a valid cell to place your pawn.
   - The game will automatically handle pawn flipping according to Othello rules.
   - The AI will make its move once you’ve played yours.
3. **Saving and Loading**:
   - Use the "Save Game" button to open a dialog and save the current game state.
   - Use the "Load Game" button to view a list of available saves and load a previous game.
4. **Resetting**: Use the "Reset Game" button to start a new game.

### **Dependencies**
- **Java SE**: This project uses only native Java libraries, including:
  - `javax.swing`: For GUI components.
  - `java.awt`: For graphics and UI elements.
  - `javax.xml.parsers` and `org.w3c.dom`: For XML parsing and file handling.

### **Contributions**
Contributions are welcome! Feel free to submit issues or open pull requests with your enhancements and suggestions.

### **License**
This project is licensed under the MIT License.
