import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class App implements KeyListener {

    private Panel panel;
    
    public App() {
        JFrame  frame = new JFrame("Othello (Reversi)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new Panel();
        frame.getContentPane().add(panel);

        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }   

    @Override
    public void keyPressed(KeyEvent event) {
        panel.handleInput(event.getKeyCode());
     }

    @Override
    public void keyReleased(KeyEvent event) { }

    @Override
    public void keyTyped(KeyEvent event) { }

    public static void main(String[] args) {
        App app = new App();
    }
}
