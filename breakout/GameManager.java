package breakout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class GameManager implements ActionListener {
    private BreakoutPanel mainPanel;

    private Level[] levels;

    private Level currentGame;
    private Ball ball;
    private Paddle paddle;

    private int currentLevel = 0;
    private int score = 0;
    private int lives = 3;

    private Timer timer;

    private int[] ballChange = new int[2];

    public GameManager(Ball ball, Paddle paddle, BreakoutPanel mainPanel) {
        this.ball = ball;
        this.paddle = paddle;
        this.mainPanel = mainPanel;

        this.timer = new Timer(10, this);

        newGame();
    }


    public void newGame() {
        // TODO
        ballChange[0] = 2;
        ballChange[1] = 4;

        Level level = new Level(2, 5);
        LinkedList<BlockAbstract> b = level.getBlocks();
        b.add(new BlockWeak());
        b.add(new BlockWeak());
        b.add(new BlockWeak());
        b.add(new BlockWeak());
        b.add(new BlockWeak(false));
        b.add(new BlockWeak());
        b.add(new BlockStrong());
        b.add(new BlockWeak());
        b.add(new BlockWeak());
        b.add(new BlockWeak());
        currentGame = level;
        mainPanel.setLevel(level);
    }

    public void startGame() {
        timer.start();
    }

    public void clearGame() {
        // TODO
    }

    public void actionPerformed(ActionEvent e) {
        paddle.updatePosition();
        updateBallPosition();
        mainPanel.repaint();
    }

    private void updateBallPosition() {
        int maxX = mainPanel.getWidth();
        int maxY = mainPanel.getHeight();
        int quickX = 0;
        int quickY = 0;

        Position pos = ball.getPosition();

        if (pos.getX() + ball.getDiameter() >= maxX || pos.getX() <= 0) {
            ballChange[0] = ballChange[0] * -1;
            if (quickX == 0)
                quickX += ballChange[0];
        }

        if (pos.getY() <= 0) {
            ballChange[1] = ballChange[1] * -1;
            if (quickY == 0)
                quickY += ballChange[1];
        } else if (pos.getY() + ball.getDiameter() >= maxY) {
            // Game over
        }

        Interval bval = ball.getInterval();
        Interval pval = paddle.getInterval();
        int check;

        if ((check = bval.checkCollision(pval)) == Interval.TOPBOTTOM) {
            ballChange[1] = ballChange[1] * -1;
            if (quickY == 0)
                quickY += ballChange[1];
        } else if (check == Interval.SIDE) {
            ballChange[0] = ballChange[0] * -1;
            if (quickX == 0)
                quickX += ballChange[0];
        }
        LinkedList<BlockAbstract> block = currentGame.getBlocks();

        for (BlockAbstract b : block) {
            if (b.isVis()) {
                pval = b.getInterval();

                if ((check = bval.checkCollision(pval)) == Interval.TOPBOTTOM) {
                    ballChange[1] = ballChange[1] * -1;
                    if (quickY == 0)
                        quickY += ballChange[1];
                    b.destroy();
                } else if (check == Interval.SIDE) {
                    ballChange[0] = ballChange[0] * -1;
                    if (quickX == 0)
                        quickX += ballChange[0];
                    b.destroy();
                }
            }
        }

        pos.changeXY(ballChange[0] + quickX, ballChange[1] + quickY);
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

    public Level[] getLevels() {
        return levels;
    }

    public void setLevels(Level[] levels) {
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
}