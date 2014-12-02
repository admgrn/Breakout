package breakout;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(Position position) {
        x = position.x;
        y = position.y;
    }
    public void changeXY(int dx, int dy) {
        x += dx;
        y += dy;
    }

}
