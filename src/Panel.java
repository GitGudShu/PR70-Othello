import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Panel extends JPanel implements MouseListener {
    
    public enum GameState {WTurn,BTurn,Draw,WWins,BWins}

    private static final int P_HEIGHT = 500;
    private static final int P_WIDTH = 500;    

    private Grid grid;
    private GameState gameState;
    private String gameStateMessage;

    private AI aiBehaviour;

    public Panel() {
        setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
        setBackground(new Color(0, 128, 0)); // Dark green othello board

        grid = new Grid(new Position(0,0), P_WIDTH, P_WIDTH,8,8);
        setGameState(GameState.BTurn);
        chooseAIType();
        addMouseListener(this);
    }

    private void playTurn(Position gridPosition) {
        if(!grid.isValidMove(gridPosition)) {
            return;
        } else if(gameState == GameState.BTurn) {
            grid.playMove(gridPosition, 1);
            setGameState(GameState.WTurn);
        } else if(gameState == GameState.WTurn) {
            grid.playMove(gridPosition, 2);
            setGameState(GameState.BTurn);
        }
    }

    private void setGameState(GameState newState) {
        gameState = newState;
        switch (gameState) {
            case WTurn:
                // If there are moves for the White player
                if(grid.getAllValidMoves().size() > 0) {
                    // TODO: White player turn message
                } else {
                    // No moves for the white player. Check the black player
                    grid.updateValidMoves(1);
                    if(grid.getAllValidMoves().size() > 0) {
                        // The black player has moves, swap back to them
                        setGameState(GameState.BTurn);
                    } else {
                        // No moves for either player found, end the game.
                        testForEndGame(false);
                    }
                }
                break;
            case BTurn:
                // If there are moves for the Black player
                if(grid.getAllValidMoves().size() > 0) {
                    // TODO: Black player turn message
                } else {
                    // No moves for the black player. Check the white player
                    grid.updateValidMoves(2);
                    if(grid.getAllValidMoves().size() > 0) {
                        // The white player has moves, swap back to them
                        setGameState(GameState.WTurn);
                    } else {
                        // No moves for either player found, end the game.
                        testForEndGame(false);
                    }
                }
                 break;
            case WWins: gameStateMessage = "White Player Wins! Press R."; break;
            case BWins: gameStateMessage = "Black Player Wins! Press R."; break;
            case Draw: gameStateMessage = "Draw! Press R."; break;
        }
    }

    private void chooseAIType() {
        String[] options = new String[] {"Player vs Player", "Player vs Random AI"};
        String message = "Select the game mode you would like to use.";
        int difficultyChoice = JOptionPane.showOptionDialog(null, message,"Choose how to play.", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch(difficultyChoice) {
            case 0: // Player vs Player
                aiBehaviour = null;
                break;
            case 1: // Player vs Dumb AI
                aiBehaviour = new AI(grid);
                break;
        }
    }

    private void testForEndGame(boolean stillValidMoves) {
        int gameResult = grid.getWinner(stillValidMoves);
        if(gameResult == 1) {
            setGameState(GameState.BWins);
        } else if(gameResult == 2) {
            setGameState(GameState.WWins);
        } else if(gameResult == 3) {
            setGameState(GameState.Draw);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        grid.paint(g);
    }

    public void restart() {
        grid.reset();
        setGameState(GameState.WTurn);
    }

    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(keyCode == KeyEvent.VK_R) {
            restart();
            repaint();
        } else if(keyCode == KeyEvent.VK_A) {
            chooseAIType();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(gameState == GameState.WTurn || gameState == GameState.BTurn) {
            Position gridPosition = grid.convertMouseToGridPosition(new Position(e.getX(), e.getY()));
            playTurn(gridPosition);
            testForEndGame(true);

            while(gameState == GameState.WTurn && aiBehaviour != null) {
                playTurn(aiBehaviour.getMove());
                testForEndGame(true);
            }
        }

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent event) { }

    @Override
    public void mouseReleased(MouseEvent event) { }

    @Override
    public void mouseEntered(MouseEvent event) { }

    @Override
    public void mouseExited(MouseEvent event) { }
    
    
}
