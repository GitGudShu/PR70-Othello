import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {

    public enum ButtonType {
        DARK_GRAY, GREEN
    }

    public CustomButton(String text, ButtonType type) {
        super(text);
        customizeButton(type);
    }

    // Customizes the button appearance based on the provided type (color scheme)
    private void customizeButton(ButtonType type) {
        setForeground(Color.LIGHT_GRAY);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);

        setBorder(new EmptyBorder(7, 20, 7, 20));
        
        Color baseColor;
        Color hoverColor;

        if (type == ButtonType.DARK_GRAY) {
            baseColor = new Color(60, 60, 60);
            hoverColor = new Color(90, 90, 90);
            setForeground(new Color(169, 169, 169));
        } else {
            baseColor = new Color(139, 195, 74);
            hoverColor = new Color(174, 221, 129); 
            setForeground(Color.WHITE);
        }

        setBackground(baseColor);
        
        // Color change on hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(baseColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Coins arrondis

        super.paintComponent(g);
        g2.dispose();
    }
}