package breakout;

import javax.swing.JPanel;
import java.awt.Graphics;

public class Ball extends JPanel implements PositionInterval {
    private int diameter;

    private Position position;

    public Ball(int diameter) {
        super();
        this.diameter = diameter;
        this.position = new Position(0, 0);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillOval(position.getX(), position.getY(), diameter, diameter);
    }

    public void UpdatePosition(int dx, int dy) {
        position.changeXY(dx, dy);
    }

    public Interval getInterval() {
        return new Interval(position.getX(), position.getY(), position.getX() + diameter, position.getY() + diameter);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position.setPosition(position);
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}