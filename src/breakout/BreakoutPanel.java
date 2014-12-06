package breakout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
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
 
    /**
     * BreakoutPanel's only constructor. Initializes all member data, and sets up
     * the BorderLayout which controls the main BreakoutPanel.
     * @param ball the Ball object which will be used in the game
     * @param paddle the Paddle object which will be used in the game
     */
    public BreakoutPanel(Ball ball, Paddle paddle) {
        super();
        this.ball = ball;
        this.paddle = paddle;
        this.blocks = new JPanel();
        this.instruct = new JLabel("Press Spacebar to begin");
        this.instruct.setOpaque(false);
        this.instruct.setHorizontalAlignment(SwingConstants.CENTER);
        this.scoreCascade = new LinkedList<>();

        setLayout(new BorderLayout());
 
        layers = new JLayeredPane();
        layers.setBackground(Color.BLUE);
 
        layers.add(this.ball, new Integer(30));
        layers.add(paddle, new Integer(20));
        layers.add(blocks, new Integer(10));
 
        add(layers, BorderLayout.CENTER);
        addKeyListener(this);
    }
 
    /**
     * setLevel changes the BreakoutPanel to display the proper Level object.
     * Called each time the player proceeds to the next level, a game is loaded,
     * or when a new game is created.
     * @param level the Level object to be loaded into the BreakoutPanel
     */
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
 
    /**
     * Listener behind the controls of the game. Listens for arrow keys to move
     * the paddle, the space bar to start the game, and the escape key to pause
     * the game.
     * @param e KeyEvent used to determine the key pressed
     */
    @Override
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
            case KeyEvent.VK_SPACE:
                isPaused = false;
                manager.setCanSave(false);
                manager.togglePauseBall();
                layers.remove(instruct);
                break;
            case KeyEvent.VK_ESCAPE:
                leftDown = false;
                rightDown = false;
                if (!isPaused) {
                    isPaused = true;
                    manager.togglePaused();
                    Breakout.changeCard(Breakout.PAUSE_MENU);
                } else if (manager.getState() == GameManager.BALLPAUSE) {
                    isPaused = true;
                    Breakout.changeCard(Breakout.PAUSE_MENU);
                } 
                break;
            default:
                break;
        }
    }
 
    /**
     * Overridden keyReleased method to properly read the input of the arrow keys.
     * @param e KeyEvent used to determine the key released
     */
    @Override
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

    /**
     * Used to clear the leftDown and rightDown variables. Called when the game
     * is finished.
     */
    public void clearControls() {
        leftDown = false;
        rightDown = false;
    }
 
    /**
     * Pauses the Ball object and adds the instruction message. When the space bar
     * is pressed, this message dissipates.
     */
    public void ballPause() {
        isPaused = true;
        layers.add(instruct, new Integer(80));
    }
    
    /**
     * Called after the user hits continue in the PauseMenu, or when the user
     * hits escape in the PauseMenu.
     */
    public void resume() {
        isPaused = false;
        if (manager.getState() != GameManager.BALLPAUSE) {
            manager.setState(GameManager.RUNNING);
            manager.resume();
        }
    }
 
    /**
     * Called to properly swap to the GameOver card after the player loses all
     * of their lives.
     */
    public void gameOver() {
        removeCascade();
        clearControls();
        Breakout.changeCard(Breakout.GAME_OVER);
    }
   
    /**
     * Called upon destruction of a BlockWeak. Displays the score received for
     * breaking said BlockWeak and then displays it in a newly generated JLabel.
     * @param value the score received for destroying a BlockWeak
     * @param rect the size of the BlockWeak, to properly center the score text
     */
    public void madeScore(int value, Rectangle rect) {
        String text = Integer.toString(value);
 
        JLabel visibleLabel = new JLabel(text);
        visibleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        visibleLabel.setBounds(rect);
 
        this.scoreCascade.add(visibleLabel);
 
        layers.add(visibleLabel, new Integer(80));
    }
 
    /**
     * Overridden paintComponent method to properly draw all of the objects contained
     * in the BreakoutPanel, including Ball, Paddle, blocks, and instruction message
     * @param g abstract Graphics class used to paint all necessary Components
     */
    @Override
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
 
    /**
     * Used to draw the cascading JLabel which shows the score; displays as a falling
     * score after breaking a BlockWeak.
     */
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

    /**
     * Used to remove the scoreCascade.
     */
    public void removeCascade() {
        for (Iterator<JLabel> it = scoreCascade.iterator(); it.hasNext(); ) {
            JLabel lab = it.next();
            layers.remove(lab);
            it.remove();
        }
    }
 
    /**
     * Set the state of the BreakoutPanel.
     * @param isPaused state of the BreakoutPanel
     */
    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
 
    /**
     * Set the GameManager driving the BreakoutPanel.
     * @param manager manager of the BreakoutPanel
     */
    public void setManager(GameManager manager) {
        this.manager = manager;
    }
    
    // Unused
    @Override
    public void keyTyped(KeyEvent e) {}
}