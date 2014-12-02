package breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LevelBlockEdit extends JPanel implements MouseListener, ItemListener {

    private JLabel pointLabel = new JLabel("Edit Point Weight");
    private JLabel typeLabel = new JLabel("Edit Block Type");
    private JComboBox<Object> pointSelect = new JComboBox<Object>();
    private JComboBox<Object> typeSelect = new JComboBox<Object>();
    private JButton done = new JButton("Done");

    private LevelEditor editor;

    public LevelBlockEdit(LevelEditor editor) {
        super();

        this.editor = editor;

        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        typeSelect.addItem("Empty");
        typeSelect.addItem("Breakable");
        typeSelect.addItem("Unbreakable");

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 1;
        constraints.gridx = 1;
        add(pointLabel, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 2;
        add(pointSelect, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridy = 2;
        constraints.gridx = 1;
        add(typeLabel, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 2;
        constraints.gridx = 2;
        add(typeSelect, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(Box.createVerticalStrut(20), constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(done, constraints);

        done.addMouseListener(this);
        typeSelect.addItemListener(this);
    }

    public void setBlockProperties(BlockAbstract block) {
        if (block instanceof BlockStrong) {
            typeSelect.setSelectedIndex(2);
            pointSelect.setEnabled(false);
            setPointsValue(true, false, 0);
        } else {
            if (block.isVis()) {
                typeSelect.setSelectedIndex(1);
                pointSelect.setEnabled(true);
                setPointsValue(false, true, ((BlockWeak)block).getWeight());
            } else {
                typeSelect.setSelectedIndex(0);
                pointSelect.setEnabled(false);
                setPointsValue(true, false, 0);
            }
        }
    }

    public void setPointsValue(boolean zero, boolean enabled, int weight) {
        pointSelect.removeAllItems();

        if (enabled) {
            pointSelect.setEnabled(true);

            for (int i = zero ? 0 : 5; i <= 100; i += 5)
                pointSelect.addItem(i);

            if (weight > 100)
                weight = 100;

            if (weight < 0 && zero)
                weight = 0;
            else if (weight < 5 && !zero)
                weight = 5;


            if ((weight % 5) != 0)
                weight = (weight / 5) * 5;

            pointSelect.setSelectedItem(weight);
        } else {
            pointSelect.setEnabled(false);
            pointSelect.addItem(0);
        }
    }

    public void mouseClicked(MouseEvent e) {
        BlockAbstract block = null;

        switch (typeSelect.getSelectedIndex()) {
            case 0:
                block = new BlockWeak(false).setEditible(true);
                break;
            case 1:
                block = new BlockWeak((int)pointSelect.getSelectedItem()).setEditible(true);
                break;
            case 2:
                block = new BlockStrong();
                break;
            default:
                break;
        }

        editor.updateEditedBlock(block);
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void itemStateChanged(ItemEvent e) {
        JComboBox<Object> found = (JComboBox<Object>)e.getSource();

        switch (found.getSelectedIndex()) {
            case 0:
                setPointsValue(true, false, 0);
                break;
            case 1:
                setPointsValue(false, true, 0);
                break;
            case 2:
                setPointsValue(true, false, 0);
                break;
            default:
                break;
        }
    }
}