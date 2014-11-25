package breakout;

public class GameManager {

    private Level[] levels;

    private Level currentGame;
    private Ball ball;
    private Paddle paddle;

    private int currentLevel = 0;
    private int score = 0;
    private int lives = 3;

    public GameManager(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;

        newGame();
    }


    public void newGame() {
        // TODO
    }

    public void clearGame() {
        // TODO
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