import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class LoadManagerWindow extends JDialog {
    private String selectedSaveFile = null;

    public LoadManagerWindow(JFrame parent) {
        super(parent, "Load Game", true);
        getContentPane().setBackground(new Color(30, 30, 30));
        
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel label = new JLabel("Select a save to load :");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        panel.add(label, BorderLayout.NORTH);

        List<String> saves = SaveManager.getAvailableSaves();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        saves.forEach(listModel::addElement);
        
        JList<String> saveList = new JList<>(listModel);
        saveList.setBackground(new Color(50, 50, 50));
        saveList.setForeground(Color.WHITE);

        
        saveList.setFont(new Font("Helvetica", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(saveList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        CustomButton loadButton = new CustomButton("Load", CustomButton.ButtonType.GREEN);
        CustomButton cancelButton = new CustomButton("Cancel", CustomButton.ButtonType.DARK_GRAY);
        
        buttonPanel.add(loadButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadButton.addActionListener((ActionEvent e) -> {
            if (!saveList.isSelectionEmpty()) {
                selectedSaveFile = saveList.getSelectedValue().split(" ")[0];
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        getContentPane().add(panel);
        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    public String getSelectedSaveFile() {
        return selectedSaveFile;
    }
}