import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int gridSize = 8;
    private final Cell[][] cells;

    public Grid() {
        cells = new Cell[gridSize][gridSize];
        initializeBoard();
    }

    /**
     * Initializes the board with the starting positions for an Othello game.
     * The board is an 8x8 grid represented as a 2D array of Cell objects, where each
     * Cell has a state: 0 for empty, 1 for black, and 2 for white.
     *
     * Starting positions:
     * - Black pieces at (3, 3) and (4, 4)
     * - White pieces at (3, 4) and (4, 3)
     *
     * The rest of the cells are initialized as empty (state 0).
     * This method is used to set up the initial game state, check valid moves,
     * place and flip pieces, calculate scores, and reset the game.
     */
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

    /**
     * Finds and returns a list of valid moves for the specified player in the Othello game.
     *
     * A move is considered valid if the selected cell is empty (state 0) and placing a piece
     * at that position results in flipping at least one opponent's piece.
     *
     * @param player the player for whom to find valid moves (1 for black, 2 for white)
     * @return a list of valid positions where the player can place a piece
     */
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

    /**
     * Checks if the specified cell is a valid move for the player.
     *
     * A move is considered valid if the selected cell is empty (state 0) and placing a piece
     * at that position results in flipping at least one opponent's piece.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @param player the player for whom to check the move
     * @param opponent the opponent player
     * @return true if the move is valid, false otherwise
     */
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

    /**
     * Places a pawn for the specified player at the given position and flips the opponent's pawns
     * in all directions where the move is valid.
     *
     * @param pos the position to place the pawn
     * @param player the player placing the pawn (1 for black, 2 for white)
     */
    public void placePawnAndFlip(Position pos, int player) {
        int row = pos.getRow();
        int col = pos.getCol();
        cells[row][col].setState(player);
        // System.out.println("Placing pawn for player " + player + " at (" + row + ", " + col + ")");
        flipPawns(row, col, player);
    }

    /**
     * Flips the opponent's pawns in all directions where the move is valid.
     *
     * @param row the row of the placed pawn
     * @param col the column of the placed pawn
     * @param player the player placing the pawn (1 for black, 2 for white)
     */
    private void flipPawns(int row, int col, int player) {
        int opponent = (player == 1) ? 2 : 1;
        for (Direction direction : Direction.values()) {
            if (checkDirection(row, col, direction, player, opponent)) {
                // System.out.println("Flipping direction: " + direction + " for player " + player + " starting at (" + row + ", " + col + ")");
                flipDirection(row, col, direction, player, opponent);
            }
        }
    }

    /**
     * Flips the opponent's pawns in the specified direction starting from the given position.
     *
     * @param row the row of the starting position
     * @param col the column of the starting position
     * @param direction the direction to flip the pawns
     * @param player the player placing the pawn (1 for black, 2 for white)
     * @param opponent the opponent player
     */
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

    /**
     * Checks if the specified direction starting from the given position is a valid move for the player.
     *
     * @param row the row of the starting position
     * @param col the column of the starting position
     * @param direction the direction to check
     * @param player the player for whom to check the move
     * @param opponent the opponent player
     * @return true if the direction is a valid move, false otherwise
     */
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

    /**
     * Checks if the specified row and column are within the bounds of the grid.
     *
     * @param row the row to check
     * @param col the column to check
     * @return true if the row and column are within the bounds of the grid, false otherwise
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    /**
     * Checks what the cell contains (0 for empty, 1 for black, 2 for white).
     *
     * @return the state of the cell
     */
    public int getCellState(int row, int col) {
        return cells[row][col].getState();
    }


    /**
     * Sets the state of the cell to the specified value (0 for empty, 1 for black, 2 for white).
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @param state the state to set
     */
    public void setCellState(int row, int col, int state) {
        if (row >= 0 && row < gridSize && col >= 0 && col < gridSize) {
            cells[row][col].setState(state);
        }
    }

    public Cell getCell(Position pos) {
        return cells[pos.getRow()][pos.getCol()];
    }

    /**
     * Gets the score of the specified player on the board.
     *
     * @param player the player for whom to calculate the score (1 for black, 2 for white)
     * @return the score of the player
     */
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

    /**
     * Resets the board to the initial state with the starting positions for the game.
     */
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
