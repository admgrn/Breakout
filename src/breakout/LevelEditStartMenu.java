package breakout;

import javax.swing.*;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class LevelEditStartMenu extends JPanel implements MouseListener, ActionListener {
    private JLabel instructions = new JLabel("Select which levels to edit");
    private JComboBox<Object> insertNew = new JComboBox<Object>();
    private JComboBox<Object> editLevel = new JComboBox<Object>();
    private JButton reset = new JButton("Reset Levels");
    private JButton back = new JButton("Back");
    private LevelEditor mainLevelEdit;

    public LevelEditStartMenu(LevelEditor mainLevelEdit) {
        super();
        this.mainLevelEdit = mainLevelEdit;
        this.setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();

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

        back.addMouseListener(this);
        reset.addMouseListener(this);
        insertNew.addActionListener(this);
        editLevel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox box = (JComboBox)e.getSource();

        int index = box.getSelectedIndex();

        if (index > 0) {
            if (box == insertNew) {

            } else {
                mainLevelEdit.setLevelToEdit(index - 1);
                mainLevelEdit.showCard("Main");
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        JButton clicked = (JButton)e.getSource();
        switch (clicked.getText()) {
            case "Back":
                Breakout.changeCard("Main Menu");
                break;
            case "Reset Levels":
                LevelPackage.resetLevels();
                break;
            default:
                // error handling?
                break;
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}