import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public App() {
        setTitle("Othello (Reversi)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new Board(), BorderLayout.CENTER);
        mainPanel.add(new Sidebar(), BorderLayout.EAST);
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
