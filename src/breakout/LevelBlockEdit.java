package breakout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.Box;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LevelBlockEdit extends JPanel implements ActionListener, ItemListener {

    private JComboBox<Object> pointSelect = new JComboBox<>();
    private JComboBox<Object> typeSelect = new JComboBox<>();

    private LevelEditor editor;

    public LevelBlockEdit(LevelEditor editor) {
        super();

        JLabel pointLabel = new JLabel("Edit Point Weight");
        JLabel typeLabel = new JLabel("Edit Block Type");
        JButton done = new JButton("Done");

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

        done.addActionListener(this);
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

    public void actionPerformed(ActionEvent e) {
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

    public void itemStateChanged(ItemEvent e) {
        JComboBox found = (JComboBox)e.getSource();

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