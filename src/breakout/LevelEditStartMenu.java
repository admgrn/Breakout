package breakout;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class LevelEditStartMenu extends JPanel implements  ActionListener {

    private JComboBox<Object> insertNew = new JComboBox<>();
    private JComboBox<Object> editLevel = new JComboBox<>();
    private LevelEditor mainLevelEdit;
    private JFrame frame;
    private Dimension frameSize;

    public LevelEditStartMenu(LevelEditor mainLevelEdit, JFrame frame) {
        super();

        JLabel instructions = new JLabel("Select which levels to edit");
        JButton reset = new JButton("Reset Levels");
        JButton back = new JButton("Back");

        this.mainLevelEdit = mainLevelEdit;
        this.setLayout(new GridBagLayout());
        this.frame = frame;
        this.frameSize = new Dimension(new Dimension(frame.getWidth(), frame.getHeight()));
        setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();

        setLevels();

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        this.add(instructions, constraints);

        constraints.gridy = 2;
        this.add(Box.createVerticalStrut(20), constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        this.add(insertNew, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        this.add(editLevel, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 5;
        this.add(reset, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        this.add(back, constraints);

        back.addActionListener(this);
        reset.addActionListener(this);
        insertNew.addActionListener(this);
        editLevel.addActionListener(this);
    }

    public void setLevels() {
        insertNew.removeAllItems();
        editLevel.removeAllItems();

        insertNew.addItem("Insert After Level");
        editLevel.addItem("Select Level To Edit");

        Vector<Level> levels = LevelPackage.getCurrentLevels(false);
        insertNew.addItem(0);

        int i = 1;

        for (Level l: levels) {
            insertNew.addItem(i);
            editLevel.addItem(i);
            ++i;
        }
    }

    public void restoreSize() {
        frame.setSize(new Dimension(frameSize.width, frameSize.height));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox box = (JComboBox) e.getSource();

            int index = box.getSelectedIndex();

            if (index > 0) {
                frameSize = new Dimension(frame.getWidth(), frame.getHeight());
                frame.setSize(new Dimension(740, 480));
                frame.validate();
                if (box == insertNew) {
                    mainLevelEdit.insertLevelToEdit(index - 1);
                    mainLevelEdit.showCard("Main");
                } else {
                    mainLevelEdit.setLevelToEdit(index - 1);
                    mainLevelEdit.showCard("Main");
                }
            }
        } else {
            JButton clicked = (JButton)e.getSource();
            switch (clicked.getText()) {
                case "Back":
                    Breakout.changeCard("Main Menu");
                    break;
                case "Reset Levels":
                    LevelPackage.resetLevels();
                    setLevels();
                    break;
                default:
                    break;
            }
        }
    }
}