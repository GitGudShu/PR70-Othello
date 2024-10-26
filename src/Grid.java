public class Grid {
    private final int gridSize = 8;
    private final Cell[][] cells;

    public Grid() {
        cells = new Cell[gridSize][gridSize];
        initializeBoard();
    }

    // Initializes the board with the starting position
    private void initializeBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }

        // Initial Othello starting positions
        cells[3][3].setState(1);
        cells[4][4].setState(1);
        cells[3][4].setState(2);
        cells[4][3].setState(2);
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

    public boolean isValidMove(Position pos, int player) {
        // Logic to validate moves will go here
        return true; // Simplified for now
    }
}
