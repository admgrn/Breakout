package breakout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JFrame;

public class GameOver extends JPanel implements ActionListener {
   
    /**
     * Constuctor for the GameOver JPanel, which implements all the menu options
     * and listeners for said options.
     */
    public GameOver() {
        super();

        JLabel gameOverMessage = new JLabel("Game Over. Try again?");
        JButton tryAgain = new JButton("Play Again");
        JButton quitGame = new JButton("Quit Game");

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        this.setBackground(Color.LIGHT_GRAY);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        constraints.weightx = 1;
        this.add(gameOverMessage, constraints);
        
        constraints.gridy = 2;
        this.add(Box.createVerticalStrut(10), constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 3;
        constraints.weightx = 1;
        this.add(tryAgain, constraints);
        
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 4;
        constraints.weightx = 1;
        this.add(quitGame, constraints);
        
        tryAgain.addActionListener(this);
        quitGame.addActionListener(this);
    }

    /**
     * Overridden method to listen for when a JButton is clicked. Checks which
     * JButton was clicked, then carries out the proper actions (returns to
     * MainMenu or quits the game).
     * @param e ActionEvent to determine the source of the fired event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton)e.getSource();
        switch (clicked.getText()) {
            case "Play Again":
                Breakout.changeCard(Breakout.MAIN_MENU);
                break;
            case "Quit Game":
                Container frame = clicked.getParent();
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