import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        setBackground(Color.white);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JButton saveButton = new JButton("Save Game");
        JButton resetButton = new JButton("Reset Game");
        JButton otherButton = new JButton("Other Option");

        add(saveButton, gbc);
        add(resetButton, gbc);
        add(otherButton, gbc);
    }
}
