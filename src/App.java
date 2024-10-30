import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private boolean vsAi; // Indique si le jeu est contre l'IA

    public App(boolean vsAi) {
        this.vsAi = vsAi; // Stocke le mode de jeu
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