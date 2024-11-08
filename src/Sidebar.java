import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sidebar extends JPanel {
    private final JFrame parentFrame;
    private final Board board;
    private BufferedImage backgroundImage;

    /**
     * Constructs a Sidebar panel for the Othello game interface, providing buttons for
     * various game-related functionalities.
     *
     * The Sidebar includes the following buttons:
     * - Save Game: Saves the current game state.
     * - Reset Game: Resets the board to its initial state.
     * - Load Game: Loads a previously saved game.
     * - Main Menu: Navigates back to the main menu and disposes of the current game frame.
     *
     * Each button is styled with custom types and has associated action listeners to handle
     * user interactions. The panel layout is set up using BorderLayout and GridBagLayout
     * for proper button alignment.
     *
     * @param parentFrame the parent JFrame to be referenced for navigation and disposal
     * @param board the game board object to be used for game operations
     */
    public Sidebar(JFrame parentFrame, Board board) {
        this.parentFrame = parentFrame;
        this.board = board;

        ensurePublicExists();
        loadBackgroundImage();

        setLayout(new BorderLayout());

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20);

        CustomButton saveButton = new CustomButton("Save Game", CustomButton.ButtonType.GREEN);
        CustomButton resetButton = new CustomButton("Reset Game", CustomButton.ButtonType.DARK_GRAY);
        CustomButton loadButton = new CustomButton("Load Game", CustomButton.ButtonType.GREEN);
        CustomButton mainMenuButton = new CustomButton("Main Menu", CustomButton.ButtonType.DARK_GRAY);

        // Button listeners
        saveButton.addActionListener(e -> handleSaveGame());
        loadButton.addActionListener(e -> handleLoadGame());
        resetButton.addActionListener(e -> handleResetGame());
        mainMenuButton.addActionListener(e -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
            parentFrame.dispose();
        });

        buttonPanel.add(saveButton, gbc);
        buttonPanel.add(resetButton, gbc);
        buttonPanel.add(loadButton, gbc);
        buttonPanel.add(mainMenuButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the save game functionality by displaying a SaveManagerWindow dialog
     * to prompt the user for a save file name. If a valid file name is provided,
     * the current game state is saved to the specified file.
     *
     * @return true if the game was successfully saved, false if canceled or invalid file name
     */
    public boolean handleSaveGame() {
        SaveManagerWindow saveWindow = new SaveManagerWindow(parentFrame);
        saveWindow.setVisible(true);

        if (saveWindow.isSaveConfirmed()) {
            String fileName = saveWindow.getSaveFileName();
            if (fileName != null && !fileName.trim().isEmpty()) {
                GameStatus gameState = board.getGameState();
                SaveManager.saveGame(gameState, fileName + ".xml");
                return true;
            }
        } 
        return false;
    }

    /**
     * Handles the load game functionality by displaying a LoadManagerWindow dialog
     * to prompt the user for a save file to load. If a valid file is selected,
     * the game state is loaded from the specified file.
     */
    private void handleLoadGame() {
        LoadManagerWindow loadWindow = new LoadManagerWindow(parentFrame);
        loadWindow.setVisible(true);

        String selectedSaveFile = loadWindow.getSelectedSaveFile();
        if (selectedSaveFile != null && !selectedSaveFile.trim().isEmpty()) {
            GameStatus loadedState = SaveManager.loadGame(selectedSaveFile);
            if (loadedState != null) {
                board.loadGameState(loadedState);
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Failed to load the game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the reset game functionality by displaying a confirmation dialog
     * to prompt the user for confirmation before resetting the game board to its
     * initial state.
     */
    private void handleResetGame() {
        JDialog confirmDialog = new JDialog(parentFrame, "Reset Game", true);
        confirmDialog.setSize(400, 160);
        confirmDialog.setLayout(new BorderLayout());
        confirmDialog.getContentPane().setBackground(new Color(30, 30, 30));
        confirmDialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        JLabel label = new JLabel("Are you sure you want to reset the game ?");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton yesButton = new CustomButton("Yes", CustomButton.ButtonType.GREEN);
        CustomButton noButton = new CustomButton("No", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        confirmDialog.getContentPane().add(panel);

        yesButton.addActionListener(e -> {
            board.resetGame();
            confirmDialog.dispose();
        });

        noButton.addActionListener(e -> confirmDialog.dispose());

        confirmDialog.setVisible(true);
    }

    // Helper method to ensure the public directory exists
    private void ensurePublicExists() {
        File publicDir = new File("public");
        if (!publicDir.exists()) {
            publicDir.mkdir();
        }
    }

    // Helper method to load the background image
    private void loadBackgroundImage() {
        try {
            File imageFile = new File("public/sb_background.jpg");
            if (imageFile.exists()) {
                backgroundImage = ImageIO.read(imageFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}