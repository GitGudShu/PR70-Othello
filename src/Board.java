import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private final int gridSize = 8;
    private final Color boardColor = new Color(0, 128, 0);

    public Board() {
        setLayout(new GridLayout(gridSize, gridSize));
        setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(139, 69, 19)));

        for (int i = 0; i < gridSize * gridSize; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(boardColor);
            cell.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            add(cell);
        }
    }
}
