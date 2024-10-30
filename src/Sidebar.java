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

    public Sidebar(JFrame parentFrame, Board board) {
        this.parentFrame = parentFrame;
        this.board = board;

        ensurePublicExists();
        loadBackgroundImage();

        setLayout(new BorderLayout()); // Utilisation de BorderLayout pour la disposition générale

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false); // Garder le panneau transparent pour voir le fond d'écran

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Panneau de boutons à la ligne
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 20, 5, 20); // Espacement des boutons

        // Création des boutons avec CustomButton
        CustomButton saveButton = new CustomButton("Save Game", CustomButton.ButtonType.GREEN);
        CustomButton resetButton = new CustomButton("Reset Game", CustomButton.ButtonType.DARK_GRAY);
        CustomButton loadButton = new CustomButton("Load Game", CustomButton.ButtonType.GREEN);
        CustomButton mainMenuButton = new CustomButton("Main Menu", CustomButton.ButtonType.DARK_GRAY);

        // Actions des boutons
        saveButton.addActionListener(e -> handleSaveGame());
        loadButton.addActionListener(e -> handleLoadGame());
        resetButton.addActionListener(e -> handleResetGame());
        mainMenuButton.addActionListener(e -> {
            MainMenu menu = new MainMenu(); // Créez une nouvelle instance du menu principal
            menu.setVisible(true); // Affiche le menu principal
            parentFrame.dispose(); // Ferme la fenêtre actuelle (facultatif)
        });

        // Ajout des boutons au panneau
        buttonPanel.add(saveButton, gbc);
        buttonPanel.add(resetButton, gbc);
        buttonPanel.add(loadButton, gbc);
        buttonPanel.add(mainMenuButton, gbc);

        // Ajout du panneau de boutons au panneau principal
        add(buttonPanel, BorderLayout.CENTER);
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