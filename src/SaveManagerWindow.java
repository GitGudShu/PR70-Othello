import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveManagerWindow extends JDialog {
    private boolean saveConfirmed = false;
    private String saveFileName = null;

    public SaveManagerWindow(JFrame parent) {
        super(parent, "Save Game", true);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Do you want to save the current game?");
        panel.add(label, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfirmed = true;
                saveFileName = getFileNameFromUser();
                if (saveFileName != null) {
                    dispose();
                }
            }
        });

        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveConfirmed = false;
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private String getFileNameFromUser() {
        return JOptionPane.showInputDialog(this, "Enter the save file name:", "Save Game", JOptionPane.PLAIN_MESSAGE);
    }

    public boolean isSaveConfirmed() {
        return saveConfirmed;
    }

    public String getSaveFileName() {
        return saveFileName;
    }
}
