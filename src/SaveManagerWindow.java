import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveManagerWindow extends JDialog {
    private boolean saveConfirmed = false;
    private String saveFileName = null;

    /**
     * Constructs a SaveManagerWindow dialog for confirming and initiating the game save process.
     * The dialog presents the user with a confirmation message and buttons for saving or canceling.
     *
     * @param parent the parent JFrame to set as the owner of this dialog
     */
    public SaveManagerWindow(JFrame parent) {
        super(parent, "Save Game", true);

        // Main panel configuration
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30)); // Fond gris très foncé
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding de 20 pour toute la fenêtre
        getContentPane().add(panel);

        // Confirmation message
        JLabel label = new JLabel("Do you want to save the current game ?");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Apple Casual", Font.BOLD, 16));
        panel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparence pour conserver le fond gris
        CustomButton yesButton = new CustomButton("Yes", CustomButton.ButtonType.GREEN);
        CustomButton noButton = new CustomButton("No", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions listeners
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

    /**
     * Displays an input dialog for the user to enter a name for the save file.
     * The dialog is modal and requires the user to confirm or cancel the action.
     *
     * @return the entered file name as a string, or null if the input is empty
     */
    private String getFileNameFromUser() {
        JDialog inputDialog = new JDialog(this, "Enter Save File Name", true);
        inputDialog.setSize(400, 220);
        inputDialog.setLayout(new BorderLayout());
        inputDialog.getContentPane().setBackground(new Color(30, 30, 30));
        inputDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        JLabel label = new JLabel("Enter the save file name :");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Helvetica", Font.PLAIN, 14));
        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton okButton = new CustomButton("OK", CustomButton.ButtonType.GREEN);
        CustomButton cancelButton = new CustomButton("Cancel", CustomButton.ButtonType.DARK_GRAY);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        okButton.addActionListener(e -> inputDialog.dispose());
        cancelButton.addActionListener(e -> {
            textField.setText("");
            inputDialog.dispose();
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
        inputDialog.add(panel);
        inputDialog.setVisible(true);

        return textField.getText().isEmpty() ? null : textField.getText();
    }


    public boolean isSaveConfirmed() {
        return saveConfirmed;
    }

    public String getSaveFileName() {
        return saveFileName;
    }
}