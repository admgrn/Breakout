package breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelEditMenu extends JPanel implements ItemListener, MouseListener {
    private JLabel heading = new JLabel("Toolbox");
    private JLabel rowCountLabel = new JLabel("Row Count");
    private JLabel columnCountLabel = new JLabel("Column Count");
    private JComboBox rowCount = new JComboBox();
    private JComboBox columnCount = new JComboBox();
    private JButton saveButton = new JButton("Save");
    private JButton discardButton = new JButton("Discard");
    private JButton delete = new JButton("Delete Level");
    private JButton help = new JButton("Help");

    private boolean noUpdate = false;

    private LevelEditor editor;

    public LevelEditMenu(LevelEditor editor) {
        super();
        this.editor = editor;

        setBackground(new Color(255, 232, 132));
        setLayout(new GridBagLayout());

        for (int i = 1; i < 8; ++i)
            columnCount.addItem(i);

        for (int i = 1; i < 6; ++i)
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

        saveButton.addMouseListener(this);
        discardButton.addMouseListener(this);
        delete.addMouseListener(this);
        help.addMouseListener(this);
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

    public void mouseClicked(MouseEvent e) {
        JButton button = (JButton)e.getSource();

        switch (button.getText()) {
            case "Save":
                if (editor.saveLevel(true))
                    editor.showCard("Level Edit Start");
                break;
            case "Discard":
                editor.saveLevel(false);
                editor.showCard("Level Edit Start");
                break;
            case "Delete Level":
                if (editor.deleteLevel())
                    editor.showCard("Level Edit Start");
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

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}