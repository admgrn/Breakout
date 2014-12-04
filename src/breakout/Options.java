package breakout;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Options extends JPanel implements ActionListener {

    private GameManager game;
    private JFrame frame;

    private JComboBox<Object> resolution = new JComboBox<>();
    private JComboBox<Object> startingLives = new JComboBox<>();

    private static String location;

    public Options(GameManager game, JFrame frame) {
        super();

        JLabel title = new JLabel("Options");
        JLabel resTitle = new JLabel("Resolution: ");
        JLabel startLivesTitle = new JLabel("Starting Lives: ");
        JButton done = new JButton("Done");

        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());

        this.game = game;
        this.frame = frame;

        resolution.addItem("740x480");
        resolution.addItem("1000x700");
        resolution.addItem("1200x1000");

        switch (frame.getHeight()) {
            case 480:
                resolution.setSelectedItem("740x480");
                break;
            case 700:
                resolution.setSelectedItem("1000x700");
                break;
            case 1000:
                resolution.setSelectedItem("1200x1000");
                break;
        }

        startingLives.addItem(3);
        startingLives.addItem(4);
        startingLives.addItem(5);
        startingLives.addItem(6);
        startingLives.addItem(7);
        startingLives.addItem(8);

        startingLives.setSelectedItem(game.getStartLives());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        this.add(title, constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        this.add(Box.createVerticalStrut(20), constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        this.add(startLivesTitle, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 3;
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        this.add(startingLives, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridy = 4;
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        this.add(resTitle, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridy = 4;
        constraints.gridx = 2;
        constraints.gridwidth = 1;
        this.add(resolution, constraints);

        constraints.gridy = 5;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        this.add(Box.createVerticalStrut(20), constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        this.add(done, constraints);

        done.addActionListener(this);
    }

    public static void setReturnTo(String location) {
        Options.location = location;
    }

    public void actionPerformed(ActionEvent e) {
        game.setStartLives((int)startingLives.getSelectedItem());

        switch ((String)resolution.getSelectedItem()) {
            case "740x480":
                frame.setSize(new Dimension(740, 480));
                break;
            case "1000x700":
                frame.setSize(new Dimension(1000, 700));
                break;
            case "1200x1000":
                frame.setSize(new Dimension(1200, 1000));
                break;
        }

        if (!location.equals(Breakout.PAUSE_MENU))
            Breakout.changeCard(Breakout.MAIN_MENU);
        else
            Breakout.changeCard(Breakout.PAUSE_MENU);
    }
}