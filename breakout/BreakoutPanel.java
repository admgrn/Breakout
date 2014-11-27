package breakout;

import sun.awt.image.ImageWatched;
import sun.jvm.hotspot.opto.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class BreakoutPanel extends JPanel implements KeyListener {
    private Ball ball;
    private Paddle paddle;
    private JPanel blocks;
    private boolean leftDown = false;
    private boolean rightDown = false;

    private JLayeredPane layers;

    public BreakoutPanel(Ball ball, Paddle paddle) {
        super();
        this.ball = ball;
        this.paddle = paddle;
        this.blocks = new JPanel();

        setLayout(new BorderLayout());

        layers = new JLayeredPane();
        layers.setBackground(Color.BLUE);

        layers.add(this.ball, new Integer(30));
        layers.add(paddle, new Integer(20));
        layers.add(blocks, new Integer(10));

        add(layers, BorderLayout.CENTER);
        addKeyListener(this);
    }

    public void setLevel(Level level) {
        blocks = new JPanel(new GridLayout(level.getSize().getWidth(), level.getSize().getHeight(), 5, 5));

        LinkedList<BlockAbstract> blockList = level.getBlocks();

        for (BlockAbstract b : blockList) {
            blocks.add(b);
        }
        layers.remove(blocks);
        layers.add(blocks, new Integer(10));
    }

    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                paddle.changeDelta(-1);
                leftDown = true;
                break;
            case KeyEvent.VK_RIGHT:
                paddle.changeDelta(1);
                rightDown = true;
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                leftDown = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightDown = false;
                break;
            default:
                break;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        revalidate();

        this.ball.setBounds(0, 0, getWidth(), getHeight());
        this.paddle.setBounds(0, getHeight() - this.paddle.getHeight(), getWidth(), this.paddle.getHeight());
        this.blocks.setBounds(0, 0, getWidth(), getHeight() / 3);
        if (this.rightDown) {
            this.paddle.changeDelta(1);
        } else if (this.leftDown) {
            this.paddle.changeDelta(-1);
        }
    }
}
