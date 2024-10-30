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
        setBackground(new Color(30, 30, 30)); // Fond de la fenêtre

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(30, 30, 30)); // Fond du panel principal
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding de 20 pour toute la fenêtre
        getContentPane().add(mainPanel);

        // Titre avec logo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(30, 30, 30)); // Fond du titre identique à l'application
        JLabel logoLabel = new JLabel(resizeIcon(new ImageIcon("public/logo.png"), 50, 50)); // Redimensionne le logo
        titlePanel.add(logoLabel);
        JLabel titleLabel = new JLabel("Othello");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Réduire l'espace entre le titre et les boutons
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espace réduit

        // Ligne 1 : Boutons J1 vs IA et J1 vs J2
        JPanel gameModePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        gameModePanel.setBackground(new Color(30, 30, 30));
        JButton playerVsPlayerButton = new CustomButton("P1 vs P2", CustomButton.ButtonType.DARK_GRAY);
        JButton playerVsAiButton = new CustomButton("P1 vs AI", CustomButton.ButtonType.GREEN);
        gameModePanel.add(playerVsPlayerButton);
        gameModePanel.add(playerVsAiButton);
        mainPanel.add(gameModePanel, BorderLayout.CENTER);

        // Ligne 2 : Bouton règles du jeu
        JButton rulesButton = new CustomButton("Game rules", CustomButton.ButtonType.DARK_GRAY);
        mainPanel.add(rulesButton, BorderLayout.SOUTH);

        // Ligne 3 : Bouton Quitter
        JButton exitButton = new CustomButton("Quit the game", CustomButton.ButtonType.DARK_GRAY);
        mainPanel.add(exitButton, BorderLayout.SOUTH);

        // Ajout des boutons à l'action
        playerVsPlayerButton.addActionListener(e -> startGame(false));
        playerVsAiButton.addActionListener(e -> startGame(true));
        rulesButton.addActionListener(e -> showRulesWindow());
        exitButton.addActionListener(e -> System.exit(0)); // Quitter l'application

        // Ajuster la largeur des boutons
        for (Component button : gameModePanel.getComponents()) {
            ((JButton) button).setPreferredSize(new Dimension(150, 50)); // Largeur de 150 pour chaque bouton
        }
        rulesButton.setPreferredSize(new Dimension(150, 50)); // Largeur de 150 pour le bouton des règles
        exitButton.setPreferredSize(new Dimension(150, 50)); // Largeur de 150 pour le bouton Quitter

        // Appliquer un layout vertical pour les boutons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(gameModePanel);
        buttonPanel.add(rulesButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(20, 20, 20)); // Fond gris foncé pour le footer

        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS)); // Alignement vertical

        // Texte du footer
        String footerText1 = "<html><span style='color:white;'>Created by</span></html>";
        String footerText2 = "<html><span style='color:white;'>" +
            "<a href='https://github.com/BilelBOUHEDDA' style='color:white; text-decoration:none;'>Bilel BOUHEDDA</a></span></html>";
        String footerText3 = "<html><span style='color:white;'>" +
            "<a href='https://github.com/GitGudShu' style='color:white; text-decoration:none;'>Thomas CHU</a></span></html>";

        // Ajouter les JLabel pour chaque texte
        JLabel footerLabel1 = new JLabel(footerText1);
        footerLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel1.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel footerLabel2 = new JLabel(footerText2);
        footerLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel2.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel footerLabel3 = new JLabel(footerText3);
        footerLabel3.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerLabel3.setHorizontalAlignment(SwingConstants.LEFT);

        // Ajouter les JLabels au panneau de footer
        footerPanel.add(footerLabel1);
        footerPanel.add(footerLabel2);
        footerPanel.add(footerLabel3);

        // Ajouter le panneau de footer au panneau principal
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Ajout d'un listener pour les clics sur les liens
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
        Image img = icon.getImage(); // Obtient l'image
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Redimensionne l'image
        return new ImageIcon(newImg); // Retourne une nouvelle ImageIcon
    }

    private void startGame(boolean vsAi) {
        // Créer une instance de la classe de jeu
        SwingUtilities.invokeLater(() -> {
            Game app = new Game(vsAi);
            app.setVisible(true);
            dispose(); // Ferme la fenêtre du menu principal
        });
    }

    public void showRulesWindow() {
        JDialog rulesDialog = new JDialog(this, "Game Rules", true);
    
        // Configuration de la fenêtre principale
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30)); // Fond gris très foncé
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding de 20 pour toute la fenêtre
        rulesDialog.getContentPane().add(panel);
    
        // Titre de la section des règles
        JLabel titleLabel = new JLabel("Othello Rules");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
    
        // Texte des règles avec mise en forme HTML
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
    
        // Bouton de fermeture
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton closeButton = new CustomButton("Close", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(closeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Action du bouton
        closeButton.addActionListener(e -> rulesDialog.dispose());
    
        // Afficher la fenêtre
        rulesDialog.pack();
        rulesDialog.setLocationRelativeTo(this);
        rulesDialog.setVisible(true);
    }
    
    

    private void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URI(urlString));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace(); // Gérer l'exception en cas d'erreur
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}