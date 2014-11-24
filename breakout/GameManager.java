package breakout;

public class GameManager {

    private Level[] levels;

    private Level currentGame;
    private int currentLevel = 0;
    private int score = 0;
    private int lives = 3;


    public GameManager() {
        newGame();
    }


    public void newGame() {
        // TODO
    }

    public void clearGame() {
        // TODO
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