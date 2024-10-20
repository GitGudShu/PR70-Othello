public class Cell {
    protected Position position;
    protected int width;
    protected int height;

    public Cell(int x, int y, int width, int height) {
        this.position = new Position(x, y);
        this.width = width;
        this.height = height;
    }

    public Cell(Position position, int width, int height) {
        this.position = new Position(position);
        this.width = width;
        this.height = height;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Position getCenter() {
        return new Position(this.position.x + this.width / 2, this.position.y + this.height / 2);
    }
}
