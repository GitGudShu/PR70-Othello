import java.util.List;

public class AI {
    private Grid grid;

    public AI(Grid grid) {
        this.grid = grid;
    }

    public Position getMove(){
        List<Position> validMoves = grid.getAllValidMoves();
        if(validMoves.size() == 0) {
            return new Position(-1,-1); // Out of bounds so nothing happens = pass
        }
        return validMoves.get((int)(Math.random()*validMoves.size()));
    }
}
