package breakout;

import javax.swing.*;
import java.awt.*;

public class BreakoutPanel extends JPanel {
    private Ball ball;
    private Paddle paddle;

    private JLayeredPane layers;

    public BreakoutPanel(Ball ball, Paddle paddle) {
        super();
        this.ball = ball;
        this.paddle = paddle;

        setLayout(new BorderLayout());

        layers = new JLayeredPane();
        layers.setBackground(Color.BLUE);

        layers.add(this.ball, new Integer(30));
        layers.add(paddle, new Integer(20));

        add(layers, BorderLayout.CENTER);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.ball.setBounds(0, 0, getWidth(), getHeight());
        this.paddle.setBounds(0, getHeight() - this.paddle.getHeight(), getWidth(), this.paddle.getHeight());
    }
}
