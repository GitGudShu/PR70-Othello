import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Board extends JPanel {
    private final int gridSize = 8;
    private final Grid grid;
    private final AI ai;
    private int currentPlayer = 1; // 1 = white, 2 = black

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

    private void displayValidMoves() {
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        for (Position pos : validMoves) {
            Cell cell = grid.getCell(pos);
            cell.showHint(true); 
        }
    }

    private void handlePlayerMove(Cell cell) {
        if (currentPlayer == 1 && cell.getState() == 0) { // Player's turn (White)
            grid.setCellState(cell.getPosition().getRow(), cell.getPosition().getCol(), currentPlayer);
            updateBoard();
            switchTurn();
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;

        if (currentPlayer == 2) { // AI's turn
            handleAIMove();
        } else {
            displayValidMoves(); // Player's turn, show valid moves
        }
    }

    private void handleAIMove() {
        Position aiMove = ai.getMove(grid);
        if (aiMove != null) {
            grid.setCellState(aiMove.getRow(), aiMove.getCol(), 2); // AI plays as Black
            updateBoard();
        }
        switchTurn(); // Switch back to the player
    }

    private void updateBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                cell.setState(grid.getCellState(row, col));
            }
        }
    }
}
