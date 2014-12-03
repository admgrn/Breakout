package breakout;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ball extends JPanel implements PositionInterval {
    private int diameter;
    private Position position;
    private BufferedImage image;

    public Ball() {
        super();
        this.position = new Position(0, 0);

        try {
            image = ImageIO.read(Ball.class.getResource("../images/ball.png"));
        } catch (Exception e) {
            // TODO Error handling
        }

        this.diameter = image.getHeight();

        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.fillOval(position.getX(), position.getY(), diameter, diameter);
        ((Graphics2D)g).drawImage(image, null, position.getX(), position.getY());
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