import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends JFrame {

    private Sidebar sidebar;  // Référence vers Sidebar pour appeler handleSaveGame()

    public Game(boolean vsAi) {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Empêche la fermeture directe de la fenêtre
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400)); 

        Board board = new Board(vsAi);
        sidebar = new Sidebar(this, board);  // Créer une instance de Sidebar pour appeler handleSaveGame()
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(board, BorderLayout.WEST);
        mainPanel.add(sidebar, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Ajout du WindowListener pour intercepter la fermeture de la fenêtre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (sidebar.handleSaveGame() == false) {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game gameWithCustomSetup = new Game(false);
            gameWithCustomSetup.setVisible(true);
        });
    }
}