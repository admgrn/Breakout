package breakout;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JPanel implements MouseListener {
    private final Font titleFont = new Font(Font.DIALOG, Font.BOLD, 70);
    private final JLabel breakout = new JLabel("Breakout");
    private final JButton newGame = new JButton("New Game");
    private final JButton loadGame = new JButton("Load Game");
    private final JButton options = new JButton("Options");
    private final JButton quitGame = new JButton("Quit Game");
    
    public MainMenu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        this.setBackground(Color.LIGHT_GRAY);
        breakout.setFont(titleFont);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        this.add(breakout, constraints);
        
        constraints.gridy = 2;
        this.add(Box.createVerticalStrut(30), constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        this.add(newGame, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        this.add(loadGame, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 5;
        this.add(options, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        this.add(quitGame, constraints);
        
        newGame.addMouseListener(this);
        loadGame.addMouseListener(this);
        options.addMouseListener(this);
        quitGame.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        JButton clicked = (JButton)e.getSource();
        switch(clicked.getText()) {
            case "New Game":
                Breakout.changeCard("Breakout");
                break;
            case "Load Game":
                // TODO implement save/load game
                break;
            case "Options":
                // TODO implement options menu
                break;
            case "Quit Game":
                Container frame = clicked.getParent();
                do {
                    frame = frame.getParent();
                }
                while (!(frame instanceof JFrame));
                ((JFrame)frame).dispose();
                // Ensure this actually exits
                break;
            default:
                break;
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
