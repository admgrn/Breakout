package breakout;

import javax.swing.JPanel;
import java.awt.*;

public class Paddle extends JPanel implements PositionInterval {
    private Size size;
    private int offset;
    private int delta = 0;
    private int deltaInc = 10;
    private float deltaMax = 20;
    private int padding = 10;

    public Paddle(int width, int height, int offset) {
        super();
        this.size = new Size(width, height);
        this.offset = offset;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(offset, padding, size.getWidth(), size.getHeight());
    }

    public void setBounds(int x, int y, int width, int height) {
        if (height <= size.height + padding * 2) {
            height = size.height + padding * 2;
        }

        super.setBounds(x, y, width, height);
    }
    public Interval getInterval() {
        Rectangle rect = getBounds();
        int x = (int)rect.getX();
        int y = (int)rect.getY();

        x += offset;
        y += padding;

        return new Interval(x, y, x + size.getWidth(), y + size.getHeight());
    }

    public void changeDelta(int d) {
        if (Math.abs(delta) <= deltaMax)
            delta += d * deltaInc;
    }

    public void updatePosition() {
        if (offset <= 0 && delta < 0) {
            delta = delta / -2;
            offset = 0;
            return;
        } else if (offset + size.getWidth() >= getWidth() && delta > 0) {
            delta = delta / -2;
            offset = getWidth() - size.getWidth();
            return;
        } else if (offset + size.getWidth() > getWidth()) {
            offset = getWidth() - size.getWidth();
        }

        if (delta > 0)
            delta -= 2;
        else if (delta < 0)
            delta += 2;

        offset += (delta / 3);
    }

    public int getDelta() {
        return delta / 3;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
