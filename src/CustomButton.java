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

    private void customizeButton(ButtonType type) {
        setForeground(Color.LIGHT_GRAY);
        setOpaque(false);
        setContentAreaFilled(false); // Pour l'effet de coin arrondi
        setFocusPainted(false);

        // Ajouter du padding autour du texte
        setBorder(new EmptyBorder(7, 20, 7, 20)); // Padding haut/bas = 10, gauche/droite = 20
        
        // Couleurs et coins arrondis
        Color baseColor;
        Color hoverColor;

        if (type == ButtonType.DARK_GRAY) {
            baseColor = new Color(60, 60, 60);       // Gris foncé
            hoverColor = new Color(90, 90, 90);      // Gris clair au survol
            setForeground(new Color(169, 169, 169)); // Texte gris clair
        } else {
            baseColor = new Color(139, 195, 74);     // Vert (C60 M0 J95 N0)
            hoverColor = new Color(174, 221, 129); 
            setForeground(Color.WHITE);              // Texte blanc sur le bouton vert
            // Vert plus clair au survol
        }

        setBackground(baseColor);
        
        // Listener pour changer la couleur au survol
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

        // Dessiner le fond arrondi
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Coins arrondis

        // Dessiner le texte avec du padding
        super.paintComponent(g);
        g2.dispose();
    }
}