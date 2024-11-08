import java.util.List;

public class AI {
    private final int player; // computer's player number (1 for White, 2 for Black)

    public AI(int player) {
        this.player = player;
    }

    public Position getMove(Grid grid) {
        List<Position> validMoves = grid.getValidMoves(player);
        if (!validMoves.isEmpty()) {
            return validMoves.get(0); // Dumb AI, just return the first valid move :)
        }
        return null; // No valid moves (Empty list)
    }

    // TODO: Implement the minimax algorithm to make the AI smarter
}
