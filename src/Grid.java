import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int gridSize = 8;
    private final Cell[][] cells;

    public Grid() {
        cells = new Cell[gridSize][gridSize];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }

        cells[3][3].setState(1);
        cells[4][4].setState(1);
        cells[3][4].setState(2);
        cells[4][3].setState(2);
    }

    public List<Position> getValidMoves(int player) {
        List<Position> validMoves = new ArrayList<>();
        int opponent = (player == 1) ? 2 : 1;

        // System.out.println("Getting valid moves for player " + player);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (cells[row][col].getState() == 0 && isValidMove(row, col, player, opponent)) {
                    validMoves.add(new Position(row, col));
                    // System.out.println("Valid move found at: (" + row + ", " + col + ")");
                }
            }
        }

        return validMoves;
    }

    public boolean isValidMove(int row, int col, int player, int opponent) {
        boolean valid = false;
        for (Direction direction : Direction.values()) {
            if (checkDirection(row, col, direction, player, opponent)) {
                // System.out.println("Valid direction found for move at (" + row + ", " + col + ") in direction: " + direction);
                valid = true;
            }
        }
        return valid;
    }

    public void placePawnAndFlip(Position pos, int player) {
        int row = pos.getRow();
        int col = pos.getCol();
        cells[row][col].setState(player);
        // System.out.println("Placing pawn for player " + player + " at (" + row + ", " + col + ")");
        flipPawns(row, col, player);
    }

    private void flipPawns(int row, int col, int player) {
        int opponent = (player == 1) ? 2 : 1;
        for (Direction direction : Direction.values()) {
            if (checkDirection(row, col, direction, player, opponent)) {
                // System.out.println("Flipping direction: " + direction + " for player " + player + " starting at (" + row + ", " + col + ")");
                flipDirection(row, col, direction, player, opponent);
            }
        }
    }

    private void flipDirection(int row, int col, Direction direction, int player, int opponent) {
        int dRow = direction.getRowDelta();
        int dCol = direction.getColDelta();
        int currentRow = row + dRow;
        int currentCol = col + dCol;

        while (isInBounds(currentRow, currentCol) && cells[currentRow][currentCol].getState() == opponent) {
            // System.out.println("Flipping cell at (" + currentRow + ", " + currentCol + ") to player " + player);
            cells[currentRow][currentCol].setState(player);
            currentRow += dRow;
            currentCol += dCol;
        }
    }

    private boolean checkDirection(int row, int col, Direction direction, int player, int opponent) {
        int currentRow = row + direction.getRowDelta();
        int currentCol = col + direction.getColDelta();
        boolean foundOpponent = false;

        while (isInBounds(currentRow, currentCol) && cells[currentRow][currentCol].getState() == opponent) {
            foundOpponent = true;
            currentRow += direction.getRowDelta();
            currentCol += direction.getColDelta();
        }

        if (foundOpponent && isInBounds(currentRow, currentCol) && cells[currentRow][currentCol].getState() == player) {
            return true;
        }

        return false;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    public int getCellState(int row, int col) {
        return cells[row][col].getState();
    }

    public void setCellState(int row, int col, int state) {
        if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
            cells[row][col].setState(state);
        }
    }

    public Cell getCell(Position pos) {
        return cells[pos.getRow()][pos.getCol()];
    }


    public int getPlayerScore(int player) {
        int score = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (cells[row][col].getState() == player) {
                    score++;
                }
            }
        }
        return score;
    }

    public void reset() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col].setState(0); // Reset all cells to empty
            }
        }
    
        // Reinitialize the starting positions
        cells[3][3].setState(1);
        cells[4][4].setState(1);
        cells[3][4].setState(2);
        cells[4][3].setState(2);
    }
    
}
