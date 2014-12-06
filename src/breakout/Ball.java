package breakout;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class Ball holds the ball of the Breakout game. Implements PositionInterval
 * interface to properly determine its position.
 */
public class Ball extends JPanel implements PositionInterval {

    private int diameter;
    private Position position;
    private BufferedImage image;
    private int offset = 0;
    private JFrame frame;
    
    /**
    * Main constructor for the Ball class. Initializes its member data and loads
    * in the image for the Ball object.
    * @param frame the base JFrame of the Ball, to help draw it in the proper location
    */
    public Ball(JFrame frame) {
        super();
        this.position = new Position(0, 0);

        try {
            image = ImageIO.read(Ball.class.getResource("/images/ball.png"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        this.diameter = image.getHeight();
        this.frame = frame;

        setOpaque(false);
    }
    
    /**
    * Overridden paintComponent method, used to draw the Ball object in the proper position.
    * @param g abstract Graphics class used to paint all necessary Components
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).drawImage(image, null, position.getX(), position.getY());
    }

    /**
     * Returns the Interval of the Ball object
     * @return the Interval of the specified object
     */
    @Override
    public Interval getInterval() {
        return new Interval(position.getX(), position.getY(), position.getX() + diameter, position.getY() + diameter);
    }

    /**
     * Returns the Position of the Ball object
     * @return the Position of the specified object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the internal Position of the Ball. Called to properly relocate the ball.
     * @param position the new Position of the specified Ball object
     */
    public void setPosition(Position position) {
        if (frame.getHeight() != 0) {
            this.position.setPosition(new Position(position.getX(),
                                 ((position.getY() - offset) + ((frame.getHeight() - 480)))));
            offset = (frame.getHeight() - 480);
        } else {
            this.position.setPosition(position);
        }
    }

    /**
     * Properly sets the offset. This is based on the resolution.
     * @param offset the new offset of the specified Ball object
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns the diameter of the Ball object
     * @return the diameter of the specified object 
     */
    public int getDiameter() {
        return diameter;
    }
}