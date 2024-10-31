import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setBackground(new Color(30, 30, 30)); // Main background color

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(30, 30, 30)); // Panel background color
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        getContentPane().add(mainPanel);

        // Title and Logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(30, 30, 30));
        JLabel logoLabel = new JLabel(resizeIcon(new ImageIcon("public/logo.png"), 50, 50));
        titlePanel.add(logoLabel);
        JLabel titleLabel = new JLabel("Othello");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Remove spaces between title and buttons
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Buttons J1 vs J2 and J1 vs AI
        JPanel gameModePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        gameModePanel.setBackground(new Color(30, 30, 30));
        JButton playerVsPlayerButton = new CustomButton("P1 vs P2", CustomButton.ButtonType.DARK_GRAY);
        JButton playerVsAiButton = new CustomButton("P1 vs AI", CustomButton.ButtonType.GREEN);
        gameModePanel.add(playerVsPlayerButton);
        gameModePanel.add(playerVsAiButton);
        mainPanel.add(gameModePanel, BorderLayout.CENTER);

        // Button rules
        JButton rulesButton = new CustomButton("Game rules", CustomButton.ButtonType.DARK_GRAY);
        mainPanel.add(rulesButton, BorderLayout.SOUTH);

        // Button quit
        JButton exitButton = new CustomButton("Quit the game", CustomButton.ButtonType.DARK_GRAY);
        mainPanel.add(exitButton, BorderLayout.SOUTH);

        // Buttons actions listeners
        playerVsPlayerButton.addActionListener(e -> startGame(false));
        playerVsAiButton.addActionListener(e -> startGame(true));
        rulesButton.addActionListener(e -> showRulesWindow());
        exitButton.addActionListener(e -> System.exit(0));

        // Button widths
        for (Component button : gameModePanel.getComponents()) {
            ((JButton) button).setPreferredSize(new Dimension(150, 50));
        }
        rulesButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setPreferredSize(new Dimension(150, 50));

        // Vertical Layout for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(gameModePanel);
        buttonPanel.add(rulesButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(20, 20, 20)); // Footer background color

        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS)); // Vertical alignment

        // Footer text
        String footerText1 = "<html><span style='color:white;'>Created by</span></html>";
        String footerText2 = "<html><span style='color:white;'>" +
            "<a href='https://github.com/BilelBOUHEDDA' style='color:white; text-decoration:none;'>Bilel BOUHEDDA</a></span></html>";
        String footerText3 = "<html><span style='color:white;'>" +
            "<a href='https://github.com/GitGudShu' style='color:white; text-decoration:none;'>Thomas CHU</a></span></html>";

        JLabel footerLabel1 = new JLabel(footerText1);
        footerLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel1.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel footerLabel2 = new JLabel(footerText2);
        footerLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel2.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel footerLabel3 = new JLabel(footerText3);
        footerLabel3.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel3.setHorizontalAlignment(SwingConstants.LEFT);

        footerPanel.add(footerLabel1);
        footerPanel.add(footerLabel2);
        footerPanel.add(footerLabel3);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Footer listeners
        footerLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebpage("https://github.com/BilelBOUHEDDA");
            }
        });

        footerLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebpage("https://github.com/GitGudShu");
            }
        });

    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage(); 
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private void startGame(boolean vsAi) {
        SwingUtilities.invokeLater(() -> {
            Game app = new Game(vsAi);
            app.setVisible(true);
            dispose();
        });
    }

    public void showRulesWindow() {
        JDialog rulesDialog = new JDialog(this, "Game Rules", true);
    
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30)); // Background color
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        rulesDialog.getContentPane().add(panel);
    
        JLabel titleLabel = new JLabel("Othello Rules");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
    
        String rulesText = "<html><div style='width:350px; font-family: Arial; color: white;'>"
                + "<h2>Setup</h2>"
                + "<p>The game is played on an 8×8 board with disc-like gaming pieces that have a black and a white side."
                + "<br><br>At the beginning of every game, four pieces, two black and two white, are put in a definite starting position in the center of the board.</p>"
                + "<h2>Outflanking the opponent</h2>"
                + "<p>The black player always makes the first move and must place a black piece in a position that outflanks the opponent’s pieces.</p>"
                + "<h2>Turning the Pieces</h2>"
                + "<p>After placing a piece, black turns over all white pieces lying on a straight line between the new piece and any anchoring black pieces.</p>"
                + "<h2>Winning the Game</h2>"
                + "<p>The game ends when the board is full or when neither player can make a valid move. The player with the most pieces wins.</p>"
                + "<p><em>Rules source: <a href='https://info.lite.games/en/support/solutions/articles/60000688960-othello-rules'>lite.games</a></em></p>"
                + "</div></html>";
    
        JLabel rulesLabel = new JLabel(rulesText);
        rulesLabel.setForeground(Color.WHITE);
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(rulesLabel, BorderLayout.CENTER);
    
        // Close button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton closeButton = new CustomButton("Close", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        closeButton.addActionListener(e -> rulesDialog.dispose());
    
        rulesDialog.pack();
        rulesDialog.setLocationRelativeTo(this);
        rulesDialog.setVisible(true);
    }
    

    private void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URI(urlString));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}