package breakout;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JLabel score;

    public ScorePanel() {
        super();
        setBackground(new Color(119, 240, 120));

        setPreferredSize(new Dimension(100, 100));
        this.score = new JLabel("0");
        add(this.score);
    }

    public void updateScore(int score) {
        this.score.setText(Integer.toString(score));
    }
}
