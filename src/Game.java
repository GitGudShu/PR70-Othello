import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends JFrame {

    private Sidebar sidebar;  // Sidebar instance to handle game saving

    /**
     * Constructs a new Othello game window with the specified game mode.
     * The game window contains a game board and a sidebar with game controls.
     *
     * @param vsAi true if the game is played against an AI, false for player vs player
     */
    public Game(boolean vsAi) {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Stop the default close operation
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400)); 

        Board board = new Board(vsAi);
        sidebar = new Sidebar(this, board);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(board, BorderLayout.WEST);
        mainPanel.add(sidebar, BorderLayout.CENTER);
        setContentPane(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (sidebar.handleSaveGame() == false) {
                    System.exit(0);
                }
            }
        });
    }
}