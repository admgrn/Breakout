package breakout;

import sun.nio.cs.ext.MacThai;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameManager implements ActionListener {
    public final static int STOPPED = 0;
    public final static int RUNNING = 1;
    public final static int WIN = 2;
    public final static int LOSS = 3;
    public final static int PAUSED = 4;
    public final static int GOINGTONEXTLEVEL = 5;
    public final static int BALLPAUSE = 6;

    private BreakoutPanel mainPanel;

    private Vector<Level> levels;

    private Level currentGame;
    private Ball ball;
    private Paddle paddle;

    private int currentLevel = 0;
    private int score = 0;
    private int lives = 3;

    private int state = STOPPED;

    private Timer timer;

    private Transform ballChange = new Transform();

    public GameManager(Ball ball, Paddle paddle, BreakoutPanel mainPanel) {
        this.ball = ball;
        this.paddle = paddle;
        this.mainPanel = mainPanel;

        this.timer = new Timer(10, this);
        this.levels = LevelPackage.GetStandardLevels();
        newGame();
    }

    public void togglePauseBall() {
        if (state == BALLPAUSE) {
            state = RUNNING;
            ballChange.setTrans(currentGame.getStartTransform());
        }
    }

    public void newGame() {
        currentLevel = 0;
        currentGame = levels.elementAt(0);
        mainPanel.setLevel(currentGame);
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
    }

    public void startGame() {
        timer.start();
        state = BALLPAUSE;
    }

    public void clearGame() {
        // TODO
    }

    public void nextLevel() {
        ++currentLevel;
        currentGame = levels.elementAt(currentLevel);
        mainPanel.setLevel(currentGame);
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        startGame();
    }

    public void lostLife() {
        ball.setPosition(currentGame.getStart());
        ballChange.setTrans(new Transform(0, 0));
        state = BALLPAUSE;
    }

    public void actionPerformed(ActionEvent e) {
        if (state == WIN) {
            state = GOINGTONEXTLEVEL;
            ScheduledExecutorService stopLevel = Executors.newSingleThreadScheduledExecutor();
            stopLevel.schedule(new Runnable() {
                public void run() {
                    nextLevel();
                }
            }, 2, TimeUnit.SECONDS);
        }

        updateBallPosition();
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
            ballChange.y = Math.abs(ballChange.y) * -1;
        } else if (check == Interval.RIGHT) {
            ballChange.y = Math.abs(ballChange.y);
        }

        LinkedList<BlockAbstract> block = currentGame.getBlocks();
        for (Iterator<BlockAbstract> it = block.iterator(); it.hasNext(); ) {
            BlockAbstract b = it.next();

            if (b. isVis()) {
                pval = b.getInterval();

                if ((check = bval.checkCollision(pval)) == Interval.BOTTOM) {
                    ballChange.y = Math.abs(ballChange.y) * -1;
                    score += b.destroy();
                    if (!b.isVis())
                        it.remove();
                } else if (check == Interval.TOP) {
                    ballChange.y = Math.abs(ballChange.y);
                    score += b.destroy();
                    if (!b.isVis())
                        it.remove();
                } else if (check == Interval.LEFT) {
                    ballChange.x = Math.abs(ballChange.x) * -1;
                    score += b.destroy();
                    if (!b.isVis())
                        it.remove();
                } else if (check == Interval.RIGHT) {
                    ballChange.x = Math.abs(ballChange.x);
                    score += b.destroy();
                    if (!b.isVis())
                        it.remove();
                }
            }
        }

        pos.changeXY(ballChange.x, ballChange.y);

        if (currentGame.didWin()) {
            state = WIN;
        }
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
}