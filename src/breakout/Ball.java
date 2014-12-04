package breakout;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Ball extends JPanel implements PositionInterval {

    private int diameter;
    private Position position;
    private BufferedImage image;
    private int offset = 0;

    private JFrame frame;

    public Ball(JFrame frame) {
        super();
        this.position = new Position(0, 0);

        try {
            image = ImageIO.read(Ball.class.getResource("/images/ball.png"));
        } catch (Exception e) {
            System.out.println("Error reading ball image");
        }

        this.diameter = image.getHeight();
        this.frame = frame;

        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).drawImage(image, null, position.getX(), position.getY());
    }

    public Interval getInterval() {
        return new Interval(position.getX(), position.getY(), position.getX() + diameter, position.getY() + diameter);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if (frame.getHeight() != 0) {
            this.position.setPosition(new Position(position.getX(),
                                 ((position.getY() - offset) + ((frame.getHeight() - 480)))));
            offset = (frame.getHeight() - 480);
        } else {
            this.position.setPosition(position);
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDiameter() {
        return diameter;
    }
}