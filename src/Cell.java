import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private int state; // 0 = empty, 1 = white, 2 = black
    private boolean showHint; // Do we show valid moves ?
    private Position position;
    private Image whiteDisc; // White pawn image
    private Image blackDisc; // Black pawn image
    private Image hintDisc; // Hint pawn image

    /**
     * Create a new Cell object
     * @param row
     * @param col
     */
    public Cell(int row, int col) {
        this.position = new Position(row, col);
        this.state = 0;
        this.showHint = false;
        setBackground(new Color(0, 128, 0));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        whiteDisc = new ImageIcon("public/blanc.png").getImage(); // Assurez-vous que le chemin est correct
        blackDisc = new ImageIcon("public/noir.png").getImage(); // Assurez-vous que le chemin est correct
        hintDisc = new ImageIcon("public/jaune.png").getImage(); // Assurez-vous que le chemin est correct
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


    /**
     * Paints the cell with the appropriate image based on its state.
     *
     * @param g the Graphics object to paint with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == 1) {
            g.drawImage(whiteDisc, 5, 5, getWidth() - 10, getHeight() - 10, this);
        } else if (state == 2) {
            g.drawImage(blackDisc, 5, 5, getWidth() - 10, getHeight() - 10, this);
        }

        if (showHint && state == 0) {
            g.drawImage(hintDisc,5, 5, getWidth() - 10, getHeight() - 10, this);
        }
    }

}