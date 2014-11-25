package breakout;

import javax.swing.JPanel;
import java.awt.Graphics;

public class Ball extends JPanel {
    private int diameter;

    private Position position;

    public Ball(int diameter, int x, int y) {
        super();
        this.diameter = diameter;
        this.position = new Position(x, y);
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(position.getX(), position.getY(), diameter, diameter);
    }

    public void UpdatePosition(int dx, int dy) {
        position.changeXY(dx, dy);
        repaint();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
}