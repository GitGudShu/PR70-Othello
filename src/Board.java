import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Board extends JPanel {
    private final int gridSize = 8;
    private final Grid grid;
    private final AI ai;
    private int currentPlayer = 1; // 1 = white, 2 = black

    private enum GameState { 
        IN_PROGRESS, 
        WIN,
        LOSS,
        DRAW
    }

    private GameState gameState = GameState.IN_PROGRESS;

    public Board() {
        this.grid = new Grid();
        this.ai = new AI(2); // AI is black
        setLayout(new GridLayout(gridSize, gridSize, 0, 0));
        setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(139, 69, 19)));

        initializeBoard();
        displayValidMoves();
    }

    private void initializeBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        handlePlayerMove(cell);
                    }
                });
                add(cell);
            }
        }
    }


    private void updateGameState() {
        if (isGameOver()) {
            int playerScore = grid.getPlayerScore(1); // White
            int aiScore = grid.getPlayerScore(2); // Black

            if (playerScore > aiScore) {
                gameState = GameState.WIN;
            } else if (playerScore < aiScore) {
                gameState = GameState.LOSS;
            } else {
                gameState = GameState.DRAW;
            }

            showGameResult();
        }
    }

    private boolean isGameOver() {
        return grid.getValidMoves(1).isEmpty() && grid.getValidMoves(2).isEmpty();
    }

    private void showGameResult() {
        String message;
        switch (gameState) {
            case WIN:
                message = "Congratulations! You win!";
                break;
            case LOSS:
                message = "You lost! Better luck next time!";
                break;
            case DRAW:
                message = "It's a draw!";
                break;
            default:
                message = "Game in progress...";
        }
    
        int choice = JOptionPane.showConfirmDialog(
            this,
            message + "\nDo you want to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
    
        if (choice == JOptionPane.YES_OPTION) {
            resetGame(); 
        } else {
            System.exit(0); // Exit the game if user chooses not to play again
        }
    }
    

    private void resetGame() {
        grid.reset();
        currentPlayer = 1;
        gameState = GameState.IN_PROGRESS;
        updateBoard();
        displayValidMoves();
    }

    // ########################## Player Moves ################################# //
    private void displayValidMoves() {
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        for (Position pos : validMoves) {
            Cell cell = grid.getCell(pos);
            cell.showHint(true); 
        }
    }

    private void handlePlayerMove(Cell cell) {
        if (currentPlayer == 1 && cell.getState() == 0) {
            Position pos = cell.getPosition();
            if (grid.isValidMove(pos.getRow(), pos.getCol(), currentPlayer, 2)) {
                grid.placePawnAndFlip(pos, currentPlayer);
                updateBoard();
                updateGameState(); // Check if game is over after player move
                if (gameState == GameState.IN_PROGRESS) {
                    switchTurn();
                }
            }
        }
    }
    

    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        if (validMoves.isEmpty()) {
            // No valid moves available
            String message = (currentPlayer == 1)
                    ? "White has no valid moves! Passing turn to Black."
                    : "Black has no valid moves! Passing turn to White.";
            JOptionPane.showMessageDialog(this, message, "Turn Skipped", JOptionPane.INFORMATION_MESSAGE);
    
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
    
            if (grid.getValidMoves(currentPlayer).isEmpty()) {
                updateGameState(); 
            } else if (currentPlayer == 2) {
                handleAIMove();
            } else {
                displayValidMoves(); // Show valid moves only for human player
            }
        } else {
            if (currentPlayer == 1) {
                displayValidMoves(); 
            } else {
                handleAIMove(); // TODO: I had a problem where the AI got stuck if given an extra turn, this is difficult to recreate but this line should fix it, to test
            }
        }
    }
    
    

    private void handleAIMove() {
        // Delay AI move by 2 seconds
        Timer timer = new Timer(2000, e -> {
            Position aiMove = ai.getMove(grid);
            if (aiMove != null && grid.isValidMove(aiMove.getRow(), aiMove.getCol(), currentPlayer, 1)) {
                grid.placePawnAndFlip(aiMove, currentPlayer);
                updateBoard();
                updateGameState(); // Check if game is over after AI move
            }
            switchTurn();
        });
        timer.setRepeats(false); // This makes sure the timer only runs once
        timer.start();
    }

    private void updateBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                cell.setState(grid.getCellState(row, col));
                cell.showHint(false); // Reset hint display
            }
        }
    }
    
}
