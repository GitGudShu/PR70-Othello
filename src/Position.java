public class Position {
    protected int row;
    protected int col;

    /**
     * Create a new Position object
     * @param row
     * @param col
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Position other) {
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
