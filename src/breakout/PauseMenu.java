package breakout;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class PauseMenu extends JPanel implements MouseListener {
    private final Font pausedFont = new Font(Font.SANS_SERIF, Font.ITALIC, 40);
    private final JLabel paused = new JLabel("Paused");
    private final JButton continueGame = new JButton("Continue");
    private final JButton saveGame = new JButton("Save Game");
    private final JButton options = new JButton("Options");
    private final JButton quitGame = new JButton("Quit Game");
   
    public PauseMenu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
       
        this.setBackground(new Color(0, 0, 0, 60));
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
        this.add(saveGame, constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 5;
        constraints.weightx = 1;
        this.add(options, constraints);
       
        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridy = 6;
        constraints.weightx = 1;
        this.add(quitGame, constraints);
       
        continueGame.addMouseListener(this);
        saveGame.addMouseListener(this);
        options.addMouseListener(this);
        quitGame.addMouseListener(this);
    }
 
    public void mouseClicked(MouseEvent e) {
        JButton src = (JButton)e.getSource();
        switch (src.getText()) {
            case "Continue":
                BreakoutPanel.resume();
                Breakout.changeCard(Breakout.BREAKOUT);
                break;
            case "Save Game":
                // TODO add save game logic
                break;
            case "Options":
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
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}