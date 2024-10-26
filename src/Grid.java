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

        // Iterate over all cells to find valid moves
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (cells[row][col].getState() == 0 && isValidMove(row, col, player, opponent)) {
                    validMoves.add(new Position(row, col));
                }
            }
        }
        return validMoves;
    }

    private boolean isValidMove(int row, int col, int player, int opponent) {
        for (Direction direction : Direction.values()) {
            if (checkDirection(row, col, direction, player, opponent)) {
                return true;
            }
        }
        return false;
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

    // Helper method to check if a position is within bounds, necessary to avoid ArrayIndexOutOfBoundsException
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

}
