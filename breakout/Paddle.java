package breakout;

import javax.swing.JPanel;
import java.awt.*;

public class Paddle extends JPanel {
    private Size size;
    private int offset;

    public Paddle(int width, int height, int offset) {
        super();
        this.size = new Size(width, height);
        this.offset = offset;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(offset, 5, size.getWidth(), size.getHeight());
    }

    public void setBounds(int x, int y, int width, int height) {
        if (height <= size.height + 10) {
            height = size.height + 10;
        }

        super.setBounds(x, y, width, height);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }




}
