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
                if (cells[row][col].getState() == 0 && isValidMove(new Position(row, col), player, opponent)) {
                    validMoves.add(new Position(row, col));
                }
            }
        }
        return validMoves;
    }

    public boolean isValidMove(Position position, int player, int opponent) {
        // Logic to validate moves will go here
        return true; // Simplified for now
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
