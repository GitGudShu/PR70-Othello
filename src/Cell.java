import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private int state; // 0 = empty, 1 = white, 2 = black
    private Position position;

    public Cell(int row, int col) {
        this.position = new Position(row, col);
        this.state = 0;
        // Green background for emtpy cells
        setBackground(new Color(0, 128, 0)); 
        // White border to visually separate cells
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); 
    }

    public void setState(int state) {
        this.state = state;
        repaint(); // Refresh the cell to update the visual representation
    }

    public int getState() {
        return this.state;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == 1) {
            g.setColor(Color.WHITE); 
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        } else if (state == 2) {
            g.setColor(Color.BLACK); 
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
        }
        
    }
}
