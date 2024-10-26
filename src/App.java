import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App() {
        setTitle("Othello (Reversi)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        Board board = new Board();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(board, BorderLayout.CENTER);
        mainPanel.add(new Sidebar(this, board), BorderLayout.EAST);
        
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
