package breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Options extends JPanel implements MouseListener {

    private GameManager game;
    private JFrame frame;

    private JLabel title = new JLabel("Options menu");
    private JLabel resTitle = new JLabel("Resolution");
    private JLabel startLivesTitle = new JLabel("Staring Lives");
    private JComboBox<Object> resolution = new JComboBox<>();
    private JComboBox<Object> startingLives = new JComboBox<>();
    private JButton done = new JButton("Done");

    private static String location;

    public Options(GameManager game, JFrame frame) {
        super();
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());

        this.game = game;
        this.frame = frame;

        resolution.addItem("720x480");
        resolution.addItem("1000x700");
        resolution.addItem("1200x1000");

        switch (frame.getHeight()) {
            case 480:
                resolution.setSelectedItem("720x480");
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

        done.addMouseListener(this);
    }

    public static void setReturnTo(String location) {
        Options.location = location;
    }

    public void mouseReleased(MouseEvent e) {
        game.setStartLives((int)startingLives.getSelectedItem());

        switch ((String)resolution.getSelectedItem()) {
            case "720x480":
                frame.setSize(new Dimension(720, 480));
                break;
            case "1000x700":
                frame.setSize(new Dimension(1000, 700));
                break;
            case "1200x1000":
                frame.setSize(new Dimension(1200, 1000));
                break;
        }

        if (location != Breakout.PAUSE_MENU)
            Breakout.changeCard(Breakout.MAIN_MENU);
        else
            Breakout.changeCard(Breakout.PAUSE_MENU);
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) { }
}
