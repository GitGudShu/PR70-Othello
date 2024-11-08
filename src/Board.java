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
    private JLabel whitePawnLabel, blackPawnLabel;

    private enum GameState { 
        IN_PROGRESS, 
        WIN,
        LOSS,
        DRAW
    }

    private GameState gameState = GameState.IN_PROGRESS;

    /**
     * Constructs a Board object for an Othello game, initializing the game grid,
     * user interface components, and setting up the game display.
     *
     * @param vsAi a boolean indicating whether the game is against an AI opponent
     */
    public Board(boolean vsAi) {
        this.vsAi = vsAi;
        this.grid = new Grid();
        this.ai = new AI(1);
    
        // Status label to display the current player's turn
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 22));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(new EmptyBorder(15, 15, 0, 15));
        statusLabel.setBackground(new Color(30, 30, 30)); // Définit la couleur de fond en noir
        statusLabel.setOpaque(true); // Rend le fond opaque
    
        // Panel to display the number of pawns
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(30, 30, 30));
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
    
        // Labels to display the number of pawns for each player
        whitePawnLabel = new JLabel("White pawns: 0");
        blackPawnLabel = new JLabel("Black pawns: 0");
        
        whitePawnLabel.setForeground(Color.WHITE);
        blackPawnLabel.setForeground(Color.WHITE);
    
        // Panel to display the number of pawns
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.add(whitePawnLabel);
    
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.add(blackPawnLabel);
    
        infoPanel.add(leftPanel, BorderLayout.WEST);
        infoPanel.add(statusLabel, BorderLayout.CENTER);
        infoPanel.add(rightPanel, BorderLayout.EAST);
    
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
    
        JPanel boardPanel = new JPanel(new GridLayout(gridSize, gridSize, 0, 0));
        boardPanel.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 15, new Color(30, 30, 30)));
    
        // Board initialization
        initializeBoard(boardPanel);
        add(boardPanel, BorderLayout.CENTER);
    
        displayValidMoves();
        updateStatusLabel();
        updatePawnCount();
    }

    /**
     * Play a sound effect when a move is made on the board.
     */
    private void playSound() {
        try {
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
    
    /**
     * Initializes the game board by creating a grid of cells and adding them to the board panel.
     * @param boardPanel the panel to which the cells are added
     */
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
    
    /**
     * Updates the status label to display the current player's turn.
     */
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

    /**
     * Updates the game state based on the current board configuration.
     * @return true if the game is still in progress, false if the game is over
     */
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
            return false;
        }
        return true;
    }

    /**
     * Checks if the game is over by verifying if both players have no valid moves.
     * @return true if the game is over, false otherwise
     */
    private boolean isGameOver() {
        return grid.getValidMoves(1).isEmpty() && grid.getValidMoves(2).isEmpty();
    }

    /**
     * Displays the game result in a dialog box when the game is over.
     */
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
    
        // Result dialog window
        JDialog resultDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Game result", true);
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        resultDialog.getContentPane().add(panel);
    
        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton playAgainButton = new CustomButton("Play again", CustomButton.ButtonType.GREEN);
        CustomButton exitButton = new CustomButton("Quit the game", CustomButton.ButtonType.DARK_GRAY);
        CustomButton mainMenuButton = new CustomButton("Back to main menu", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(mainMenuButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        playAgainButton.addActionListener(e -> resultDialog.dispose()); // Ferme la boîte de dialogue

        resultDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                resetGame();
            }
        });
    
        exitButton.addActionListener(e -> System.exit(0));
    
        mainMenuButton.addActionListener(e -> {
            resultDialog.dispose();
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }
    
    /**
     * Resets the game to its initial state by clearing the board and updating the display.
     */
    protected void resetGame() {
        currentPlayer = 2;
        gameState = GameState.IN_PROGRESS;
        grid.reset();

        updateBoard();
        System.out.println("Le reset a été effectué");
        displayValidMoves();
        updateStatusLabel();
        updatePawnCount();
    }    

    // ########################## Player Moves ################################# //

    /**
     * Displays the valid moves for the current player on the board.
     */
    private void displayValidMoves() {
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        // System.out.println("Valid moves for player " + currentPlayer + ": " + validMoves); // Log des mouvements valides
        for (Position pos : validMoves) {
            Cell cell = grid.getCell(pos);
            cell.showHint(true); 
        }
    }
    
    /**
     * Handles the player move by placing a pawn on the selected cell and flipping the opponent's pawns.
     * @param cell the cell where the player wants to place a pawn
     */
    private void handlePlayerMove(Cell cell) {
        if (currentPlayer == 2 && cell.getState() == 0) {
            Position pos = cell.getPosition();
            if (grid.isValidMove(pos.getRow(), pos.getCol(), currentPlayer, 1)) {
                grid.placePawnAndFlip(pos, currentPlayer);
                playSound();
                updateBoard();
                updatePawnCount();
                if (!updateGameState()) {
                    return;
                }
                switchTurn();
            }
        } else if (currentPlayer == 1 && cell.getState() == 0) { // J2 joue avec le blanc
            Position pos = cell.getPosition();
            if (grid.isValidMove(pos.getRow(), pos.getCol(), currentPlayer, 2)) {
                grid.placePawnAndFlip(pos, currentPlayer);
                playSound();
                updateBoard();
                updatePawnCount();
                if (!updateGameState()) {
                    return;
                }
                switchTurn();
            }
        }
    }    
    
    /**
     * Switches the current player's turn in the Othello game, updates the status label,
     * and checks for valid moves for the new current player.
     *
     * If the current player has no valid moves, the turn is passed to the opponent, and
     * a dialog is shown to inform the player. If neither player has valid moves, the game
     * state is updated to end the game.
     *
     * Additionally, if the game is set to play against an AI and the AI's turn is next,
     * the method triggers the AI to make a move.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        updateStatusLabel();
        updatePawnCount();
        List<Position> validMoves = grid.getValidMoves(currentPlayer);
        if (validMoves.isEmpty()) {
            String message = (currentPlayer == 1)
                    ? "White has no valid moves! Passing turn to Black."
                    : "Black has no valid moves! Passing turn to White.";
    
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Turn Skipped", true);
            JPanel panel = new JPanel(new BorderLayout(0, 15));
            panel.setBackground(new Color(30, 30, 30));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
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
    
            dialog.setSize(600, 160);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
    
            // Switch to the next player if the current player has no valid moves
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
    
            if (grid.getValidMoves(currentPlayer).isEmpty()) {
                updateGameState(); // if no player can play, end the game
            } else {
                updateStatusLabel();
                if (vsAi && currentPlayer == 1) {
                    handleAIMove();
                } else {
                    displayValidMoves();
                }
            }
        } else {
            // If the next player is the AI, let the AI make a move
            if (vsAi && currentPlayer == 1) {
                handleAIMove();
            } else {
                displayValidMoves();
            }
        }
    }

    /**
     * Handles the AI's move by delaying the action by one second and executing the move.
     * If the move is valid, the AI places a piece, updates the board, and switches the turn.
     */
    private void handleAIMove() {
        // Delay AI move by 1 seconde
        Timer timer = new Timer(1000, e -> {
            Position aiMove = ai.getMove(grid);
            if (aiMove != null && grid.isValidMove(aiMove.getRow(), aiMove.getCol(), currentPlayer, 2)) {
                grid.placePawnAndFlip(aiMove, currentPlayer);
                playSound(); // Joue le son après le coup de l'IA
                updateBoard();
                updatePawnCount();
                if (updateGameState()) { // Vérifie si la partie est toujours en cours
                    switchTurn();
                }
            }
        });
        timer.setRepeats(false); // This makes sure the timer only runs once
        timer.start();
    }
    
    /**
     * Updates the game board by refreshing the state of each cell and repainting the board.
     */
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
    
    /**
     * Retrieves the current game state, including the grid configuration, player turn,
     * and scores for both players.
     *
     * @return the current game state as a GameStatus object
     */
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
        updateStatusLabel();
        updatePawnCount();

        SwingUtilities.invokeLater(() -> {
            // Réinitialiser le texte à jour dans le thread de l'UI
            updateStatusLabel();  // Actualiser le texte du statut
        });
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

    // ########################## UI Methods ################################# //
    /**
     * Responsively resizes the board panel to ensure it is square.
     * Returns the preferred size of the board panel to ensure it is square.
     * @return the preferred size of the board panel
     */
    @Override
    public Dimension getPreferredSize() {
        // On calcule la dimension carrée basée sur la taille actuelle de la fenêtre
        int size = Math.min(getParent().getWidth(), getParent().getHeight());
        return new Dimension(size, size);
    }

    /**
     * Updates the count of pawns for each player and displays the count.
     */
    private void updatePawnCount() {
        int blackPawns = countPawns(2); // Noir
        int whitePawns = countPawns(1); // Blanc

        whitePawnLabel.setText("White pawns: " + whitePawns);
        blackPawnLabel.setText("Black pawns: " + blackPawns);
    }

    /**
     * Counts the number of pawns for the specified player on the game board.
     * @param player the player for whom to count the pawns (1 for White, 2 for Black)
     * @return the number of pawns for the specified player
     */
    private int countPawns(int player) {
        int count = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int cellState = grid.getCellState(row, col);
                if (cellState == player) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void doLayout() {
        // Board panel is resized to be square and centered in the parent container
        Dimension preferredSize = getPreferredSize();
        setBounds(0, 0, preferredSize.width, preferredSize.height);
        super.doLayout();
    }

}