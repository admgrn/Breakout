package breakout;

import java.awt.*;

public class BlockWeak extends BlockAbstract {
    public int weight;

    public BlockWeak() {
        this(true, 10);
    }

    public BlockWeak(boolean visible) {
        this(visible, 10);
    }

    public BlockWeak(int weight) {
        this(true, weight);
    }

    public BlockWeak(boolean visible, int weight) {
        vis = visible;
        destroyable = true;

        if (weight > 100)
            weight = 100;

        this.weight = weight;
    }

    public int destroy() {
        vis = false;
        return 10;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = new Color(weight, weight * 2, 255);

        if (vis) {
            setBackground(color);
        } else {
            setOpaque(false);
        }
    }
}
