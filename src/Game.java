import javax.swing.*;
import java.awt.*;

    public class Game extends JFrame {

        public Game(boolean vsAi) {
            setTitle("Othello");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 600);
            setLocationRelativeTo(null);
            setMinimumSize(new Dimension(600, 400)); 
            
            Board board = new Board(vsAi);
            
            
            
            // // Configuration du plateau pour les tests
            // int[][] initialState = {
            //     {1, 2, 2, 2, 2, 2, 0, 0},
            //     {1, 1, 1, 1, 1, 0, 0, 0},
            //     {1, 1, 1, 2, 2, 2, 2, 1},
            //     {1, 1, 1, 1, 2, 1, 2, 1},
            //     {1, 1, 1, 1, 1, 2, 2, 1},
            //     {1, 1, 2, 2, 2, 1, 2, 1},
            //     {1, 1, 1, 1, 2, 2, 2, 1},
            //     {2, 2, 2, 2, 2, 2, 2, 2}
            // };
            // board.setupBoard(initialState);
        

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(board, BorderLayout.WEST);
            mainPanel.add(new Sidebar(this, board), BorderLayout.CENTER);
            setContentPane(mainPanel);
        }
        
        

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                Game gameWithCustomSetup = new Game(false);
                gameWithCustomSetup.setVisible(true);
            });
        }
    }