package breakout;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class GameOver extends JPanel implements MouseListener {
    private final JLabel gameOverMessage = new JLabel("Game Over. Try again?");
    private final JButton tryAgain = new JButton("Play Again");
    private final JButton quitGame = new JButton("Quit Game");
   
    public GameOver() {
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
        
        tryAgain.addMouseListener(this);
        quitGame.addMouseListener(this);
    }
 
    public void mouseClicked(MouseEvent me) {
        JButton clicked = (JButton)me.getSource();
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
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
}