package breakout;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Breakout {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(740, 480);
        frame.setFocusable(false);
        frame.setLayout(new BorderLayout());

        Ball ball = new Ball(20);
        Paddle paddle = new Paddle(100, 15, 50);

        BreakoutPanel breakoutPanel = new BreakoutPanel(ball, paddle);
        ScorePanel scorePanel = new ScorePanel();

        GameManager manager = new GameManager(ball, paddle, breakoutPanel, scorePanel);
        manager.startGame();

        breakoutPanel.setManager(manager);
        breakoutPanel.setFocusable(true);

        frame.add(breakoutPanel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.EAST);
        frame.setVisible(true);

        breakoutPanel.requestFocusInWindow();
    }
}
/*
class BreakoutPanel extends JPanel implements KeyListener {
    int xPosition = 285;
    int yPosition = 430;

    public BreakoutPanel() {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                xPosition -= 7;
                repaint();
                break;
            case KeyEvent.VK_RIGHT:
                xPosition += 7;
                repaint();
                break;
            default:
                repaint();
                break;
        }

        System.out.println("x: " + xPosition + "y: " + yPosition);
    }

    public void keyReleased(KeyEvent e) {
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setColor(Color.BLACK);
        g2d.fill(new Rectangle(xPosition, yPosition, 60, 10));
    }

}*/