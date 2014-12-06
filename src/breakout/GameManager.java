package breakout;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
public class GameManager implements ActionListener, Serializable {

    public final static transient int STOPPED = 0;
    public final static transient int RUNNING = 1;
    public final static transient int WIN = 2;
    public final static transient int LOSS = 3;
    public final static transient int PAUSED = 4;
    public final static transient int GOINGTONEXTLEVEL = 5;
    public final static transient int BALLPAUSE = 6;
 
    private transient BreakoutPanel mainPanel;
    private transient ScorePanel scorePanel;
 
    private Vector<Level> levels;
 
    private transient Level currentGame;
    private transient Ball ball;
    private transient Paddle paddle;
 
    private int currentLevel = 0;
    private transient int score = 0;
    private transient int startLives = 3;
    private transient int lives = startLives;
    private transient int nextLife = 1000;
    private int savedScore = 0;
    private int savedLives = 0;
    
    private transient boolean canSave = false;
 
    private transient int state = STOPPED;
 
    private transient Timer timer;
 
    private transient Transform ballChange = new Transform();
 
    /**
     * Main constructor for the GameManager. Initializes all variables and starts
     * the Timer which drives the action in the game.
     * @param ball Ball object used in the game
     * @param paddle Paddle object used in the game
     * @param mainPanel BreakoutPanel used by the game
     * @param scorePanel ScorePanel used by the game
     */
    public GameManager(Ball ball, Paddle paddle, BreakoutPanel mainPanel, ScorePanel scorePanel) {
        this.ball = ball;
        this.paddle = paddle;
        this.mainPanel = mainPanel;
        this.scorePanel = scorePanel;
 
        this.timer = new Timer(10, this);
        newGame();
    }
 
    /**
     * togglePauseBall is used to correctly set the state of the Ball object.
     */
    public void togglePauseBall() {
        if (state == BALLPAUSE) {
            state = RUNNING;
            ballChange.setTrans(currentGame.getStartTransform());
            paddle.setDelta(0);
            mainPanel.removeCascade();
        }
    }
    
    /**
     * Called when the player pauses the game by hitting the escape key. Properly
     * sets the state to ensure nothing happens while the player peruses the PauseMenu.
     */
    public void togglePaused() {
        if (state == RUNNING) {
            paddle.setDelta(0);
            mainPanel.removeCascade();
            timer.stop();
            state = PAUSED;
        }
    }
 
    /**
     * Sets up the initial game logic.
     */
    public void newGame() {
        canSave = true;

        this.levels = LevelPackage.getCurrentLevels(false);
        
        nextLife = 1000;
        lives = startLives;
        score = 0;
        scorePanel.updateScore(score);
        scorePanel.updateLives(lives);
        scorePanel.updateLevels(1, levels.size());
        
        currentLevel = 0;
        currentGame = new Level(levels.elementAt(0));
        mainPanel.setLevel(currentGame);
        ball.setOffset(0);
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        paddle.setDelta(0);
        mainPanel.removeCascade();
    }
 
    /**
     * Initializes the game.
     */
    public void startGame() {
        timer.start();
        state = BALLPAUSE;
    }
   
    /**
     * Resumes the game after returning from the PauseMenu.
     */
    public void resume() {
        timer.start();
    }
    
    /**
     * Stores data from the GameManager class in a file named "breakout.sav"
     */
    public void save() {
        try {
            savedLives = lives;
            savedScore = score;
            
            OutputStream file = new FileOutputStream("breakout.sav");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            
            output.writeObject(this);
            output.close();
            JOptionPane.showMessageDialog(mainPanel, "Game Saved");
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(mainPanel, "Error saving game.");
        }
    }
    
    /**
     * Restores the data saved in "breakout.sav"
     * @throws IOException caught by MainMenu
     * @throws ClassNotFoundException caught by MainMenu
     */
    public void load() throws IOException, ClassNotFoundException {
        GameManager load;
        InputStream file = new FileInputStream("breakout.sav");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
            
        load = (GameManager)input.readObject();
        input.close();
            
        if (load != null) {
            this.state = STOPPED;
            this.lives = load.savedLives;
            this.score = load.savedScore;
                
            this.currentLevel = load.currentLevel;
            LevelPackage.setLevels(load.levels);
            this.levels = LevelPackage.getCurrentLevels(true);
            currentGame = new Level(levels.elementAt(currentLevel));
            mainPanel.setLevel(currentGame);
                
            scorePanel.updateScore(score);
            scorePanel.updateLives(lives);
            scorePanel.updateLevels(currentLevel + 1, this.levels.size());

            ball.setOffset(0);
            ball.setPosition(currentGame.getStart());
            ballChange.setTrans(new Transform(0, 0));
            paddle.setDelta(0);
            mainPanel.removeCascade();
            startGame();
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Error opening save file.");
        }
    }
    
    /**
     * Displays the next Level in the levels Vector. Displays the win game message
     * if there are no more levels.
     */
    public void nextLevel() {
        ++currentLevel;
        if (levels.size() > currentLevel) {
            canSave = true;
            currentGame = new Level(levels.elementAt(currentLevel));
            scorePanel.updateLevels(currentLevel + 1, levels.size());
            mainPanel.setLevel(currentGame);
            mainPanel.setPaused(true);
            ball.setOffset(0);
            ball.setPosition(currentGame.getStart());
            ballChange.setTrans(new Transform(0, 0));
            paddle.setDelta(0);
            mainPanel.removeCascade();
            startGame();
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(mainPanel, "You win!\n" +
                    "Score: " + score);
            mainPanel.clearControls();
            paddle.setDelta(0);
            mainPanel.removeCascade();
            Breakout.changeCard("Main Menu");

        }
    }
 
    /**
     * Updates the ScorePanel and determines whether the player has lost the game.
     * If so, displays the GameOver panel.
     */
    public void lostLife() {
        --lives;
        ball.setOffset(0);
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        paddle.setDelta(0);
        mainPanel.removeCascade();
        scorePanel.updateLives(lives);
        
        if (lives <= 0) {
            state = LOSS;
            mainPanel.gameOver();
        } else {
            state = BALLPAUSE;
            mainPanel.ballPause();
        }
    }
 
    /**
     * Used by the Timer object to properly update the Ball object's position
     * in the panel.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == WIN) {
            state = GOINGTONEXTLEVEL;
            ScheduledExecutorService stopLevel = Executors.newSingleThreadScheduledExecutor();
            stopLevel.schedule(new Runnable() {
                @Override
                public void run() {
                    nextLevel();
                }
            }, 100, TimeUnit.MILLISECONDS);
        }
 
        updateBallPosition();
        mainPanel.updateCascade();
        mainPanel.repaint();
    }
 
    /**
     * Determines the next location for the Ball object and paints it in said
     * location. Also determines when score is made, when a life is lost, and whether
     * or not a BlockWeak needs to be removed.
     */
    private void updateBallPosition() {
        int maxX = mainPanel.getWidth();
        int maxY = mainPanel.getHeight();
 
        Position pos = ball.getPosition();
 
        if (pos.getX() + ball.getDiameter() >= maxX) {
            ballChange.x = Math.abs(ballChange.x) * -1;
        } else if (pos.getX() <= 0) {
            ballChange.x = Math.abs(ballChange.x);
        }
 
        if (pos.getY() <= 0) {
            ballChange.y = ballChange.y * -1;
        } else if (pos.getY() + ball.getDiameter() >= maxY && state == RUNNING) {
            lostLife();
        }
 
        Interval bval = ball.getInterval();
        Interval pval = paddle.getInterval();
        int check;
 
        if ((check = bval.checkCollision(pval)) == Interval.BOTTOM) {
            ballChange.y = Math.abs(ballChange.y) * -1;
        } else if (check == Interval.TOP) {
            ballChange.y = Math.abs(ballChange.y);
         } else if (check == Interval.LEFT) {
            ballChange.x = paddle.getDelta() - 1;
        } else if (check == Interval.RIGHT) {
            ballChange.x = paddle.getDelta() + 1;
        }
 
        int val;
 
        LinkedList<BlockAbstract> block = currentGame.getBlocks();
        for (Iterator<BlockAbstract> it = block.iterator(); it.hasNext(); ) {
            BlockAbstract b = it.next();
 
            if (b.isVis()) {
                pval = b.getInterval();
 
                if ((check = bval.checkCollision(pval)) == Interval.BOTTOM) {
                    ballChange.y = Math.abs(ballChange.y) * -1;
                    val = b.destroy();
                    score += val;
 
                    if (b.isDestroyable()) {
                        mainPanel.madeScore(val, b.getBounds());
                        it.remove();
                    }
                } else if (check == Interval.TOP) {
                    ballChange.y = Math.abs(ballChange.y);
                    val = b.destroy();
                    score += val;
 
                    if (b.isDestroyable()) {
                        mainPanel.madeScore(val, b.getBounds());
                        it.remove();
                    }
                } else if (check == Interval.LEFT) {
                    ballChange.x = Math.abs(ballChange.x) * -1;
                    val = b.destroy();
                    score += val;
 
                    if (b.isDestroyable()) {
                        mainPanel.madeScore(val, b.getBounds());
                        it.remove();
                    }
                } else if (check == Interval.RIGHT) {
                    ballChange.x = Math.abs(ballChange.x);
                    val = b.destroy();
                    score += val;
 
                    if (b.isDestroyable()) {
                        mainPanel.madeScore(val, b.getBounds());
                        it.remove();
                    }
                }
            }
        }
        
        if (score >= nextLife) {
            nextLife *= 2;
            ++lives;
            scorePanel.updateLives(lives);
        }
        scorePanel.updateScore(score);
        pos.changeXY(ballChange.x, ballChange.y);
 
        if (currentGame.didWin() && state != WIN && state != GOINGTONEXTLEVEL) {
            state = WIN;
        }
    }

    /**
     * Sets the Ball object's position properly when continuing the game.
     */
    public void updateBallPos() {
        ball.setPosition(ball.getPosition());
    }
 
    /** 
     * Returns the state of the GameManager object.
     * @return the state of the specified object
     */
    public int getState() {
        return state;
    }
   
    /**
     * Sets the state of the GameManager object.
     * @param s state for the GameManager to be set
     */
    public void setState(int s) {
        state = s;
    }
    
    /**
     * Returns whether or not the user can save (users can only save in between
     * levels).
     * @return boolean value indicating the viability of saving
     */
    public boolean getCanSave() {
        return canSave;
    }
    
    /**
     * Sets the canSave boolean
     * @param b the canSave boolean value
     */
    public void setCanSave(boolean b) {
        canSave = b;
    }

    /**
     * Returns the amount of lives the player starts with.
     * @return the amount of lives the player starts with
     */
    public int getStartLives() {
        return startLives;
    }

    /**
     * Sets the amount of lives the player starts with.
     * @param startLives the amount of lives the player starts with
     */
    public void setStartLives(int startLives) {
        this.startLives = startLives;
    }
}