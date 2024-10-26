import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private int state; // 0 = empty, 1 = white, 2 = black
    private boolean showHint; // Do we show valid moves ?
    private Position position;

    public Cell(int row, int col) {
        this.position = new Position(row, col);
        this.state = 0;
        this.showHint = false;
        setBackground(new Color(0, 128, 0));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    }

    public void setState(int state) {
        this.state = state;
        this.showHint = false; // Reset hint display when state is set
        repaint();
    }

    public int getState() {
        return this.state;
    }

    public Position getPosition() {
        return this.position;
    }

    public void showHint(boolean show) {
        this.showHint = show;
        repaint();
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

        // Display hint if applicable
        if (showHint && state == 0) {
            g.setColor(Color.YELLOW);
            g.fillOval(15, 15, getWidth() - 30, getHeight() - 30);
        }
    }
}
