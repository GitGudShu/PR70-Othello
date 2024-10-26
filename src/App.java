import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private final int gridSize = 8; // The size of the grid (8x8 for Othello)
    private final Color boardColor = new Color(0, 128, 0); // Define the green color for the board

    public App() {
        setTitle("Othello Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Adjusted to fit sidebar and board with borders
        setLocationRelativeTo(null); // Center the window on the screen

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(createBoard(), BorderLayout.CENTER);
        mainPanel.add(createSidebar(), BorderLayout.EAST);
        setContentPane(mainPanel);
    }

    private JPanel createBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(gridSize, gridSize));
        // Increase the outer border thickness here
        boardPanel.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(139, 69, 19))); // Brown border simulating wooden edges
    
        // Increase the line border thickness for each cell if needed
        for (int i = 0; i < gridSize * gridSize; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(boardColor);
            // Adjust the cell border thickness to better integrate with the thick outer border
            cell.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Thick black border for each cell
            boardPanel.add(cell);
        }
        return boardPanel;
    }
    

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.white);
        sidebar.setLayout(new GridBagLayout()); // Using GridBagLayout for better control over positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component in its own row
        gbc.fill = GridBagConstraints.HORIZONTAL; // Horizontal fill
        gbc.insets = new Insets(10, 20, 10, 20); // Margins around components

        // Adding buttons with centered alignment and spacing
        JButton saveButton = new JButton("Save Game");
        JButton resetButton = new JButton("Reset Game");
        JButton otherButton = new JButton("Other Option");

        sidebar.add(saveButton, gbc);
        sidebar.add(resetButton, gbc);
        sidebar.add(otherButton, gbc);

        return sidebar;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
