package breakout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class ScorePanel extends JPanel {

    private JLabel score;
    private JLabel lives;
    private JLabel level;

    public ScorePanel() {
        super();

        JLabel scoreHeader = new JLabel("<html>Score:<br /></html>");
        JLabel livesHeader = new JLabel("<html>Lives:<br /></html>");

        JLabel instruct = new JLabel("<html><br />Press esc<br />to pause</html>");

        setBackground(new Color(119, 240, 120));
        setPreferredSize(new Dimension(100, 100));

        this.score = new JLabel("0");
        this.lives = new JLabel("3");
        this.level = new JLabel("<html>Level 1 of 4<br /></html>");

        add(scoreHeader);
        add(this.score);
        add(new JLabel(""));
        add(livesHeader);
        add(this.lives);
        add(new JLabel(""));
        add(level);
        add(new JLabel(""));
        add(instruct);
    }

    public void updateLevels(int currLevel, int maxLevel) {
        this.level.setText("<html>Level " + Integer.toString(currLevel) + " of " + Integer.toString(maxLevel) +
                           "<br /></html>");
    }

    public void updateScore(int score) {
        this.score.setText(Integer.toString(score));
    }

    public void updateLives(int lives) {
        this.lives.setText(Integer.toString(lives));
    }
}