package breakout;
 
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
 
    private Vector<Level> levels; // saved
 
    private transient Level currentGame; // saved
    private transient Ball ball;
    private transient Paddle paddle;
 
    private int currentLevel = 0; // saved
    private transient int score = 0; // saved

    private transient int startLives = 3;
    private transient int lives = startLives; // saved
    private int savedScore = 0;
    private int savedLives = 0;
    
    private transient boolean canSave = false;
 
    private transient int state = STOPPED;
 
    private transient Timer timer;
 
    private transient Transform ballChange = new Transform();
 
    public GameManager(Ball ball, Paddle paddle, BreakoutPanel mainPanel, ScorePanel scorePanel) {
        this.ball = ball;
        this.paddle = paddle;
        this.mainPanel = mainPanel;
        this.scorePanel = scorePanel;
 
        this.timer = new Timer(10, this);
        newGame();
    }
 
    public void togglePauseBall() {
        if (state == BALLPAUSE) {
            state = RUNNING;
            ballChange.setTrans(currentGame.getStartTransform());
            paddle.setDelta(0);
            mainPanel.removeCascade();
        }
    }
    
    public void togglePaused() {
        if (state == RUNNING) {
            paddle.setDelta(0);
            mainPanel.removeCascade();
            timer.stop();
            state = PAUSED;
            // mainPanel.ballPause();
        }
    }
 
    public void newGame() {
        canSave = true;
        
        // LevelPackage.resetLevels();
        this.levels = LevelPackage.getCurrentLevels(false);
        
        lives = startLives;
        score = 0;
        scorePanel.updateScore(score);
        scorePanel.updateLives(lives);
        
        currentLevel = 0;
        currentGame = new Level(levels.elementAt(0));
        mainPanel.setLevel(currentGame);
        
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        paddle.setDelta(0);
        mainPanel.removeCascade();
    }
 
    public void startGame() {
        timer.start();
        state = BALLPAUSE;
    }
   
    public void resume() {
        timer.start();
    }
    
    public void save() {
        try {
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
    
    public void load() {
        GameManager load = null;
        try {
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
                // LevelPackage.resetLevels();
                
                LevelPackage.setLevels(load.levels);
                // Currently this ends up setting the level in the state in
                // which it was saved, instead of its default state (without
                // any blocks broken). Score and lives are saving correctly.
                // ^No longer true because users can only save in between levels
                
                this.levels = LevelPackage.getCurrentLevels(true);
                currentGame = new Level(levels.elementAt(currentLevel));
                mainPanel.setLevel(currentGame);
                
                scorePanel.updateScore(score);
                scorePanel.updateLives(lives);
                
                ball.setPosition(currentGame.getStart());
                ballChange.setTrans(new Transform(0, 0));
                paddle.setDelta(0);
                mainPanel.removeCascade();
                startGame();
            }
            else {
                JOptionPane.showMessageDialog(mainPanel, "Error opening save file.");
            }
        }
        catch(IOException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(mainPanel, "Error opening save file.");
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(mainPanel, "Error opening save file.");
        }
    }
    
    public void nextLevel() {
        ++currentLevel;
        if (levels.size() > currentLevel) {
            canSave = true;
            savedScore = score;
            savedLives = lives;
            currentGame = new Level(levels.elementAt(currentLevel));
            mainPanel.setLevel(currentGame);
            mainPanel.setPaused(true);
            ball.setPosition(currentGame.getStart());
            ballChange.setTrans(new Transform(0, 0));
            paddle.setDelta(0);
            mainPanel.removeCascade();
            startGame();
        }
        else {
            timer.stop();
            JOptionPane.showMessageDialog(mainPanel, "You win!\n" +
                    "Score: " + score);
            mainPanel.clearControls();
            paddle.setDelta(0);
            mainPanel.removeCascade();
            Breakout.changeCard("Main Menu");

        }
    }
 
    public void lostLife() {
        --lives;
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        paddle.setDelta(0);
        mainPanel.removeCascade();
        scorePanel.updateLives(lives);
        
        if (lives <= 0) {
            state = LOSS;
            mainPanel.gameOver();
        }
        else {
            state = BALLPAUSE;
            mainPanel.ballPause();
        }
    }
 
    public void actionPerformed(ActionEvent e) {
        if (state == WIN) {
            state = GOINGTONEXTLEVEL;
            ScheduledExecutorService stopLevel = Executors.newSingleThreadScheduledExecutor();
            stopLevel.schedule(new Runnable() {
                public void run() {
                    nextLevel();
                }
            }, 100, TimeUnit.MILLISECONDS);
        }
 
        updateBallPosition();
        mainPanel.updateCascade();
        mainPanel.repaint();
    }
 
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
        
        if (score % 1000 == 0 && score != 0) {
            ++lives;
            scorePanel.updateLives(lives);
        }
        scorePanel.updateScore(score);
        pos.changeXY(ballChange.x, ballChange.y);
 
        if (currentGame.didWin() && state != WIN && state != GOINGTONEXTLEVEL) {
            state = WIN;
        }
    }

    public void updateBallPos() {
        ball.setPosition(ball.getPosition());
    }
 
    public Ball getBall() {
        return ball;
    }
 
    public void setBall(Ball ball) {
        this.ball = ball;
    }
 
    public Paddle getPaddle() {
        return paddle;
    }
 
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
 
    public Level getCurrentGame() {
        return currentGame;
    }
 
    public void setCurrentGame(Level currentGame) {
        this.currentGame = currentGame;
    }
 
    public Vector<Level> getLevels() {
        return levels;
    }
 
    public void setLevels(Vector<Level> levels) {
        this.levels = levels;
    }
 
    public void setScore(int score) {
        this.score = score;
    }
 
    public int getScore() {
        return score;
    }
 
    public void setLives(int lives) {
        this.lives = lives;
    }
 
    public int getLives() {
        return lives;
    }
 
    public int getCurrentLevel() {
        return currentLevel;
    }
 
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
 
    public int getState() {
        return state;
    }
   
    public void setState(int s) {
        state = s;
    }
    
    public boolean getCanSave() {
        return canSave;
    }
    
    public void setCanSave(boolean b) {
        canSave = b;
    }

    public int getStartLives() {
        return startLives;
    }

    public void setStartLives(int startLives) {
        this.startLives = startLives;
    }
}