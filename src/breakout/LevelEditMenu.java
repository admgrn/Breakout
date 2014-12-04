package breakout;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelEditMenu extends JPanel implements ItemListener, ActionListener {

    private JComboBox<Integer> rowCount = new JComboBox<>();
    private JComboBox<Integer> columnCount = new JComboBox<>();

    private boolean noUpdate = false;
    private LevelEditor editor;

    public LevelEditMenu(LevelEditor editor) {
        super();
        this.editor = editor;

        JLabel heading = new JLabel("Toolbox");
        JLabel rowCountLabel = new JLabel("Row Count");
        JLabel columnCountLabel = new JLabel("Column Count");
        JButton saveButton = new JButton("Save");
        JButton discardButton = new JButton("Discard");
        JButton delete = new JButton("Delete Level");
        JButton help = new JButton("Help");

        setBackground(new Color(255, 232, 132));
        setLayout(new GridBagLayout());

        for (int i = 1; i < 11; ++i)
            columnCount.addItem(i);

        for (int i = 1; i < 11; ++i)
            rowCount.addItem(i);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weighty = 1;
        constraints.gridy = 1;
        this.add(heading, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 2;
        constraints.weightx = 1;
        this.add(rowCountLabel, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridy = 3;
        constraints.weightx = 1;
        this.add(rowCount, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        constraints.weightx = 1;
        this.add(columnCountLabel, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridy = 5;
        constraints.weightx = 1;
        this.add(columnCount, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        constraints.weightx = 1;
        this.add(saveButton, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridy = 7;
        constraints.weightx = 1;
        this.add(discardButton, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 8;
        constraints.weightx = 1;
        this.add(delete, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 9;
        constraints.weightx = 1;
        this.add(help, constraints);

        columnCount.addItemListener(this);
        rowCount.addItemListener(this);

        saveButton.addActionListener(this);
        discardButton.addActionListener(this);
        delete.addActionListener(this);
        help.addActionListener(this);
    }

    public void setRowsColumns(int rows, int columns) {
        noUpdate = true;
        rowCount.setSelectedIndex(rows - 1);
        columnCount.setSelectedIndex(columns - 1);
        noUpdate = false;
    }

    public void itemStateChanged(ItemEvent e) {
        JComboBox box = (JComboBox)e.getSource();
        if (!noUpdate) {
            if (box == rowCount) {
                editor.updateRowsColumns(box.getSelectedIndex() + 1, columnCount.getSelectedIndex() + 1);
            } else {
                editor.updateRowsColumns(rowCount.getSelectedIndex() + 1, box.getSelectedIndex() + 1);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();

        switch (button.getText()) {
            case "Save":
                if (editor.saveLevel(true)) {
                    editor.updateSize();
                    editor.showCard("Level Edit Start");
                }
                break;
            case "Discard":
                editor.updateSize();
                editor.saveLevel(false);
                editor.showCard("Level Edit Start");
                break;
            case "Delete Level":
                if (editor.deleteLevel()) {
                    editor.updateSize();
                    editor.showCard("Level Edit Start");
                }
                break;
            case "Help":
                JOptionPane.showMessageDialog(editor, "1. Drag ball to start position\n" +
                                                      "2. Click anywhere to adjust ball direction\n" +
                                                      "3. Adjust rows on columns in the toolbox\n" +
                                                      "4. Click on blocks to change settings");
                break;
            default:
                break;
        }
    }
}