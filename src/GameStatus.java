public class GameStatus {
    private String saveName;
    private int currentPlayer;
    private int[][] grid;
    private int whiteScore;
    private int blackScore;

    public GameStatus(String saveName, int currentPlayer, int[][] grid, int whiteScore, int blackScore) {
        this.saveName = saveName;
        this.currentPlayer = currentPlayer;
        this.grid = grid;
        this.whiteScore = whiteScore;
        this.blackScore = blackScore;
    }

    public String getSaveName() {
        return saveName;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    
}
