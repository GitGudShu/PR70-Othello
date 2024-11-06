import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Board extends JPanel {
    private final int gridSize = 8;
    private final Grid grid;
    private final AI ai;
    private int currentPlayer = 2; // 1 = white, 2 = black
    private JLabel statusLabel; 
    private boolean vsAi;

    private enum GameState { 
        IN_PROGRESS, 
        WIN,
        LOSS,
        DRAW
    }

    private GameState gameState = GameState.IN_PROGRESS;

    public Board(boolean vsAi) {
        this.vsAi = vsAi;
        System.out.println(vsAi);
        this.grid = new Grid();
        this.ai = new AI(1);
    
        // Initialisation du JLabel
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(new EmptyBorder(15, 15, 0, 15));
        statusLabel.setBackground(new Color(30, 30, 30)); // Définit la couleur de fond en noir
        statusLabel.setOpaque(true); // Rend le fond opaque
        
        // Ajouter le JLabel au panneau
        setLayout(new BorderLayout()); // Change le layout pour ajouter un JLabel
        add(statusLabel, BorderLayout.NORTH); // Ajoute le JLabel en haut
        JPanel boardPanel = new JPanel(new GridLayout(gridSize, gridSize, 0, 0));
        boardPanel.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(30, 30, 30)));
    
        // Initialisation des cellules
        initializeBoard(boardPanel);
    
        add(boardPanel, BorderLayout.CENTER); // Ajoute le panneau du plateau
        displayValidMoves();
        updateStatusLabel(); // Met à jour le JLabel au démarrage
    }

    private void playSound() {
        try {
            // Chemin relatif vers le fichier audio
            File audioFile = new File("public/clouc.wav");
            if (!audioFile.exists()) {
                System.err.println("Sound file not found : " + audioFile.getAbsolutePath());
                return;
            }
    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            System.out.println("Sound played with success !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeBoard(JPanel boardPanel) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        handlePlayerMove(cell);
                    }
                });
                boardPanel.add(cell);
            }
        }
    }
    
    private void updateStatusLabel() {
        if (vsAi && currentPlayer == 2) {
            statusLabel.setText("It's J1's (Black) turn!"); // Noir
        } else if (vsAi && currentPlayer == 1) {
            statusLabel.setText("AI is playing (White)..."); // Blanc
        } else if (currentPlayer == 2) {
            statusLabel.setText("It's J1's (Black) turn!"); // Noir
        } else {
            statusLabel.setText("It's J2's (White) turn!"); // Blanc
        }
    }

    private boolean updateGameState() {
        if (isGameOver()) {
            int playerScore = grid.getPlayerScore(2); // Noir
            int aiScore = grid.getPlayerScore(1); // Blanc
    
            if (playerScore > aiScore) {
                gameState = GameState.WIN;
            } else if (playerScore < aiScore) {
                gameState = GameState.LOSS;
            } else {
                gameState = GameState.DRAW;
            }
    
            showGameResult();
            return false; // La partie est terminée
        }
        return true; // La partie continue
    }
    

    private boolean isGameOver() {
        return grid.getValidMoves(1).isEmpty() && grid.getValidMoves(2).isEmpty();
    }

    public void showGameResult() {
        String message;
        switch (gameState) {
            case WIN:
                message = "Player 1 (Black) win the game !";
                break;
            case LOSS:
                message = "Player 2 (White) win the game !";
                break;
            case DRAW:
                message = "It's a draw !";
                break;
            default:
                message = "Game on...";
        }
    
        // Création d'une nouvelle fenêtre pour afficher le résultat
        JDialog resultDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game result", true);
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30)); // Fond sombre
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding de 20 pour toute la fenêtre

        resultDialog.getContentPane().add(panel);
    
        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparence pour conserver le fond sombre
        CustomButton playAgainButton = new CustomButton("Play again", CustomButton.ButtonType.GREEN);
        CustomButton exitButton = new CustomButton("Quit the game", CustomButton.ButtonType.DARK_GRAY);
        CustomButton mainMenuButton = new CustomButton("Back to main menu", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(mainMenuButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Configurer le bouton "Jouer à nouveau"
        playAgainButton.addActionListener(e -> resultDialog.dispose()); // Ferme la boîte de dialogue

        // Ajout d'un WindowListener à la boîte de dialogue pour capturer sa fermeture
        resultDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                resetGame();
            }
        });

    
        // Action pour le bouton "Quitter"
        exitButton.addActionListener(e -> System.exit(0));
    
        // Action pour le bouton "Retour au menu principal"
        mainMenuButton.addActionListener(e -> {
            resultDialog.dispose();
            MainMenu menu = new MainMenu(); // Créez une nouvelle instance du menu principal
            menu.setVisible(true); // Affiche le menu principal
        });
    
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }
    
    protected void resetGame() {
        currentPlayer = 2; // Assurez-vous que le joueur noir commence
        gameState = GameState.IN_PROGRESS;
        
        grid.reset(); // Réinitialise l'état de la grille
        
        updateBoard(); // Met à jour l'affichage du plateau
        System.out.println("Le reset a été effectué");
        displayValidMoves(); // Affiche les mouvements valides pour le joueur noir
        updateStatusLabel(); // Met à jour le JLabel pour indiquer que c'est aux noirs de jouer
    }    

    // ########################## Player Moves ################################# //
    private void displayValidMoves() {
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        System.out.println("Valid moves for player " + currentPlayer + ": " + validMoves); // Log des mouvements valides
        for (Position pos : validMoves) {
            Cell cell = grid.getCell(pos);
            cell.showHint(true); 
        }
    }
    
    private void handlePlayerMove(Cell cell) {
        if (currentPlayer == 2 && cell.getState() == 0) { // J1 joue avec le noir
            System.out.println("Oui");
            Position pos = cell.getPosition();
            if (grid.isValidMove(pos.getRow(), pos.getCol(), currentPlayer, 1)) {
                grid.placePawnAndFlip(pos, currentPlayer);
                playSound(); // Joue le son après le coup
                updateBoard();
                if (!updateGameState()) { // Si la partie est terminée, ne pas changer de tour
                    return;
                }
                switchTurn();
            }
        } else if (currentPlayer == 1 && cell.getState() == 0) { // J2 joue avec le blanc
            Position pos = cell.getPosition();
            if (grid.isValidMove(pos.getRow(), pos.getCol(), currentPlayer, 2)) {
                grid.placePawnAndFlip(pos, currentPlayer);
                playSound(); // Joue le son après le coup
                updateBoard();
                if (!updateGameState()) { // Si la partie est terminée, ne pas changer de tour
                    return;
                }
                switchTurn();
            }
        }
    }    
    
    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;

        updateStatusLabel(); // Met à jour le JLabel après le changement de tour
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        System.out.println(validMoves);
        if (validMoves.isEmpty()) {
            String message = (currentPlayer == 1)
                    ? "White has no valid moves! Passing turn to Black."
                    : "Black has no valid moves! Passing turn to White.";
    
            // Affichage d'une fenêtre indiquant que le tour est passé
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Turn Skipped", true);
            dialog.setSize(600, 250);
            JPanel panel = new JPanel(new BorderLayout(0, 15));
            panel.setBackground(new Color(30, 30, 30)); // Fond sombre
            panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding de 20 pour toute la fenêtre
    
            dialog.getContentPane().add(panel);
    
            JLabel label = new JLabel(message);
            label.setForeground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Helvetica", Font.BOLD, 16));
            panel.add(label, BorderLayout.CENTER);
    
            JButton okButton = new CustomButton("OK", CustomButton.ButtonType.GREEN);
            okButton.addActionListener(e -> dialog.dispose());
    
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(okButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
    
            dialog.setSize(400, 160);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
    
            // Changer le tour à nouveau si l'adversaire peut jouer
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
    
            if (grid.getValidMoves(currentPlayer).isEmpty()) {
                updateGameState(); // Si aucun joueur ne peut jouer, fin du jeu
            } else {
                updateStatusLabel();
                if (vsAi && currentPlayer == 1) {
                    handleAIMove(); // Forcer l'IA à jouer si elle peut
                } else {
                    displayValidMoves(); // Affiche les coups valides pour le joueur humain
                }
            }
        } else {
            // Si le joueur peut jouer
            if (vsAi && currentPlayer == 1) {
                handleAIMove();
            } else {
                displayValidMoves();
            }
        }
    }
    

    private void handleAIMove() {
        // Delay AI move by 1 seconde
        Timer timer = new Timer(1000, e -> {
            Position aiMove = ai.getMove(grid);
            if (aiMove != null && grid.isValidMove(aiMove.getRow(), aiMove.getCol(), currentPlayer, 2)) {
                grid.placePawnAndFlip(aiMove, currentPlayer);
                playSound(); // Joue le son après le coup de l'IA
                updateBoard();
                
                if (updateGameState()) { // Vérifie si la partie est toujours en cours
                    switchTurn();
                }
            }
        });
        timer.setRepeats(false); // This makes sure the timer only runs once
        timer.start();
    }
    

    private void updateBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = grid.getCell(new Position(row, col));
                cell.setState(grid.getCellState(row, col));
            }
        }
        repaint();
    }

    // ########################## Save Manager methods ################################# //

    // Retrieve the loaded game state
    public GameStatus getGameState() {
        int[][] gridState = new int[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridState[row][col] = grid.getCellState(row, col);
            }
        }
        int whiteScore = grid.getPlayerScore(1);
        int blackScore = grid.getPlayerScore(2);

        return new GameStatus("CurrentSave", currentPlayer, gridState, whiteScore, blackScore);
    }

    // Method to load a saved game state
    public void loadGameState(GameStatus gameState) {
        currentPlayer = gameState.getCurrentPlayer();
        int[][] gridData = gameState.getGrid();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid.setCellState(row, col, gridData[row][col]);
            }
        }

        updateBoard();
        displayValidMoves(); 
    }

    public void setupBoard(int[][] initialState) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid.setCellState(row, col, initialState[row][col]);
            }
        }
        updateBoard();
        displayValidMoves();
    }
    
}