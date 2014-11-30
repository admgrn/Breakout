package breakout;
 
import org.w3c.dom.css.Rect;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
 
public class BreakoutPanel extends JPanel implements KeyListener {
    private Ball ball;
    private GameManager manager = null;
    private Paddle paddle;
    private JPanel blocks;
    private JLabel instruct;
    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean isPaused = true;
 
    private LinkedList<JLabel> scoreCascade;
 
    private JLayeredPane layers;
 
    public BreakoutPanel(Ball ball, Paddle paddle) {
        super();
        this.ball = ball;
        this.paddle = paddle;
        this.blocks = new JPanel();
        this.instruct = new JLabel("Press Spacebar to begin");
        this.instruct.setOpaque(false);
        this.instruct.setHorizontalAlignment(SwingConstants.CENTER);
        this.scoreCascade = new LinkedList<JLabel>();
 
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
        JPanel oldBlock = blocks;
 
        blocks = new JPanel(new GridLayout(level.getSize().getWidth(), level.getSize().getHeight(), 5, 5));
        blocks.setOpaque(false);
        LinkedList<BlockAbstract> blockList = level.getBlocks();
 
        for (BlockAbstract b : blockList) {
            blocks.add(b);
        }
        layers.remove(oldBlock);
        layers.add(blocks, new Integer(10));
 
        layers.add(instruct, new Integer(70));
    }
 
    public void keyTyped(KeyEvent e) {}
 
 
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (!isPaused) {
                    paddle.changeDelta(-1);
                    leftDown = true;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!isPaused) {
                    paddle.changeDelta(1);
                    rightDown = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                isPaused = false;
                manager.togglePauseBall();
                layers.remove(instruct);
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("Escape key");
                manager.setState(manager.PAUSED);
                Breakout.changeCard("Pause Menu");
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
 
    public void ballPause() {
        layers.add(instruct, new Integer(80));
    }
    
    public void pauseMenu() {
        // TODO
    }
 
    public void gameOver() {
        Breakout.changeCard("Game Over");
    }
   
    public void madeScore(int value, Rectangle rect) {
        String text = Integer.toString(value);
 
        JLabel visibleLabel = new JLabel(text);
        visibleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        visibleLabel.setBounds(rect);
 
        this.scoreCascade.add(visibleLabel);
 
        layers.add(visibleLabel, new Integer(80));
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        revalidate();
        this.ball.setBounds(0, 0, getWidth(), getHeight());
        this.paddle.setBounds(0, getHeight() - this.paddle.getHeight(), getWidth(), this.paddle.getHeight());
        this.blocks.setBounds(0, 0, getWidth(), getHeight() / 3);
        this.instruct.setBounds(0, 0, getWidth(), getHeight());
 
        if (this.rightDown) {
            this.paddle.changeDelta(1);
        } else if (this.leftDown) {
            this.paddle.changeDelta(-1);
        }
        this.paddle.updatePosition();
 
    }
 
    public void updateCascade() {
        for (Iterator<JLabel> it = scoreCascade.iterator(); it.hasNext(); ) {
            JLabel lab = it.next();
            Rectangle rect = lab.getBounds();
            rect.translate(0, 4);
            lab.setBounds(rect);
 
            if (rect.getY() > getHeight()) {
                layers.remove(lab);
                it.remove();
            }
 
        }
    }
 
    public boolean isPaused() {
        return isPaused;
    }
 
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
 
    public void setManager(GameManager manager) {
        this.manager = manager;
    }
}