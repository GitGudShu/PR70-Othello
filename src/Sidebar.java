import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    private final JFrame parentFrame;
    private final Board board;

    public Sidebar(JFrame parentFrame, Board board) {
        this.parentFrame = parentFrame;
        this.board = board;

        setBackground(Color.white);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JButton saveButton = new JButton("Save Game");
        JButton resetButton = new JButton("Reset Game");
        JButton loadButton = new JButton("Load Game");

        saveButton.addActionListener(e -> handleSaveGame());
        loadButton.addActionListener(e -> handleLoadGame());

        add(saveButton, gbc);
        add(resetButton, gbc);
        add(loadButton, gbc);
    }

    private void handleSaveGame() {
        SaveManagerWindow saveWindow = new SaveManagerWindow(parentFrame);
        saveWindow.setVisible(true);

        if (saveWindow.isSaveConfirmed()) {
            String fileName = saveWindow.getSaveFileName();
            if (fileName != null && !fileName.trim().isEmpty()) {
                GameStatus gameState = board.getGameState();
                SaveManager.saveGame(gameState, fileName + ".xml");
            }
        }
    }

    private void handleLoadGame() {
        // Ask the user to select a file to load
        String fileName = JOptionPane.showInputDialog(parentFrame, "Enter the save file name to load:", "Load Game", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null && !fileName.trim().isEmpty()) {
            GameStatus loadedState = SaveManager.loadGame(fileName + ".xml");
            if (loadedState != null) {
                board.loadGameState(loadedState); // Load the game state into the board
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Failed to load the game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
