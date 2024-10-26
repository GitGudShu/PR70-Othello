import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadManagerWindow extends JDialog {
    private String selectedSaveFile = null;

    public LoadManagerWindow(JFrame parent) {
        super(parent, "Load Game", true);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Select a save to load:");
        panel.add(label, BorderLayout.NORTH);

        // Get available saves
        List<String> saves = SaveManager.getAvailableSaves();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String save : saves) {
            listModel.addElement(save);
        }

        JList<String> saveList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(saveList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load and cancel buttons
        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Load");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(loadButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!saveList.isSelectionEmpty()) {
                    String selectedValue = saveList.getSelectedValue();
                    selectedSaveFile = selectedValue.split(" ")[0]; // Extract file name without extension
                    dispose();
                }
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
