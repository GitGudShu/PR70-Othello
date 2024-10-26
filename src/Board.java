import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private final int gridSize = 8;
    private final Grid grid;

    public Board() {
        this.grid = new Grid();
        setLayout(new GridLayout(gridSize, gridSize, 0, 0));
        setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(139, 69, 19)));

        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                add(cell);
            }
        }
    }
}
