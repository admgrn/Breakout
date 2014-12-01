package breakout;
 
import javax.swing.*;
import java.awt.*;
 
public class ScorePanel extends JPanel {
 
    private final JLabel scoreHeader = new JLabel("<html>Score:<br></br></html>");
    private final JLabel livesHeader = new JLabel("<html>Lives:<br></br></html>");
    private JLabel score;
    private JLabel lives;
 
    public ScorePanel() {
        super();
        setBackground(new Color(119, 240, 120));
 
        setPreferredSize(new Dimension(100, 100));
        this.score = new JLabel("0");
        this.lives = new JLabel("3");
        add(this.scoreHeader);
        add(this.score);
        add(new JLabel("")); // find a better way to do this spacing
        add(this.livesHeader);
        add(this.lives);
       
    }
 
    public void updateScore(int score) {
        this.score.setText(Integer.toString(score));
    }
   
    public void updateLives(int lives) {
        this.lives.setText(Integer.toString(lives));
    }
}