import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {

    public Game(boolean vsAi) {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
    
        Board board = new Board(vsAi); // Passer vsAi à Board
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(new Sidebar(this, board), BorderLayout.EAST); 
    
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}