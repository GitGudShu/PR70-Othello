import java.awt.Color;
import java.awt.Graphics;

public class GridCell extends Cell {

    // State of the cell: 0 = empty, 1 = player 1, 2 = player 2
    private int cellState;
    private boolean validMove;

    public GridCell(Position position, int width, int height) {
        super(position, width, height);
        this.cellState = 0;
        this.validMove = false;
    }

    public void reset() {
        cellState = 0;
        validMove = false;
    }

    public int getCellState() {
        return cellState;
    }

    public void setCellState(int cellState) {
        this.cellState = cellState;
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }

    public void paint(Graphics g) {
        if (cellState == 1) { 
            drawOutlinedPawn(g, Color.BLACK, Color.WHITE, 0.75, 0.85);
        } else if (cellState == 2) { 
            drawOutlinedPawn(g, Color.WHITE, Color.BLACK, 0.75, 0.85);
        } else if (validMove) {
            drawOval(g, Color.YELLOW, 0.3);
        }
    }
    
    private void drawOutlinedPawn(Graphics g, Color fillColor, Color outlineColor, double fillRatio, double outlineRatio) {
        // Draw outline of the pawn
        g.setColor(outlineColor);
        int outlineWidth = (int) Math.round(width * outlineRatio);
        int outlineHeight = (int) Math.round(height * outlineRatio);
        int outlineX = position.x + (width - outlineWidth) / 2;
        int outlineY = position.y + (height - outlineHeight) / 2;
        g.fillOval(outlineX, outlineY, outlineWidth, outlineHeight);
    
        // Draw the inner pawn (slightly smaller oval)
        g.setColor(fillColor);
        int fillWidth = (int) Math.round(width * fillRatio);
        int fillHeight = (int) Math.round(height * fillRatio);
        int fillX = position.x + (width - fillWidth) / 2;
        int fillY = position.y + (height - fillHeight) / 2;
        g.fillOval(fillX, fillY, fillWidth, fillHeight);
    }
    
    
    private void drawOval(Graphics g, Color color, double sizeRatio) {
        g.setColor(color);
        int ovalWidth = (int) Math.round(width * sizeRatio);
        int ovalHeight = (int) Math.round(height * sizeRatio);
        int adjustedX = position.x + (width - ovalWidth) / 2;
        int adjustedY = position.y + (height - ovalHeight) / 2;
        g.fillOval(adjustedX, adjustedY, ovalWidth, ovalHeight);
    }
    
    


}
