public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public void add(Position position) {
        this.x += position.x;
        this.y += position.y;
    }

    public void subtract(Position position) {
        this.x -= position.x;
        this.y -= position.y;
    }

    public void multiply(int value) {
        this.x *= value;
        this.y *= value;
    }

    public void distance(Position position) {
        this.x = Math.abs(this.x - position.x);
        this.y = Math.abs(this.y - position.y);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Position)) return false;
        Position position = (Position) object;
        return this.x == position.x && this.y == position.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
