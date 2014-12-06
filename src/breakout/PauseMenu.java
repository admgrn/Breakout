package breakout;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PauseMenu extends JPanel implements ActionListener {

    private BreakoutPanel mainPanel;
    private GameManager manager;
   
    public PauseMenu(BreakoutPanel mainPanel, GameManager manager) {
        GridBagConstraints constraints = new GridBagConstraints();

        this.mainPanel = mainPanel;
        this.manager = manager;

        Font pausedFont = new Font(Font.SANS_SERIF, Font.ITALIC, 40);
        JLabel paused = new JLabel("Paused");
        JButton continueGame = new JButton("Continue");
        JButton mainMenu = new JButton("Main Menu");
        JButton saveGame = new JButton("Save Game");
        JButton options = new JButton("Options");
        JButton quitGame = new JButton("Quit Game");
        
        this.setLayout(new GridBagLayout());
       
        this.setBackground(Color.LIGHT_GRAY);
        paused.setFont(pausedFont);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.weightx = 1;
        this.add(paused, constraints);
       
        constraints.gridy = 2;
        this.add(Box.createVerticalStrut(10), constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        constraints.weightx = 1;
        this.add(continueGame, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        constraints.weightx = 1;
        this.add(mainMenu, constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 5;
        constraints.weightx = 1;
        this.add(saveGame, constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        constraints.weightx = 1;
        this.add(options, constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 7;
        constraints.weightx = 1;
        this.add(quitGame, constraints);
       
        continueGame.addActionListener(this);
        saveGame.addActionListener(this);
        options.addActionListener(this);
        quitGame.addActionListener(this);
        mainMenu.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton)e.getSource();
        switch (src.getText()) {
            case "Continue":
                mainPanel.resume();
                Breakout.changeCard(Breakout.BREAKOUT);
                manager.updateBallPos();
                break;
            case "Main Menu":
                Breakout.changeCard(Breakout.MAIN_MENU);
                break;
            case "Save Game":
                if (manager.getCanSave()) {
                    manager.save();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Game can only be saved in between levels");
                }
                break;
            case "Options":
                Options.setReturnTo(Breakout.PAUSE_MENU);
                Breakout.changeCard(Breakout.OPTIONS);
                break;
            case "Quit Game":
                Container frame = src.getParent();
                do {
                    frame = frame.getParent();
                }
                while (!(frame instanceof JFrame));
                ((JFrame)frame).dispose();
                System.exit(0);
                break;
            default:
                break;
        }
    }
}