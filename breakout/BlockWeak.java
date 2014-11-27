package breakout;

import java.awt.*;

public class BlockWeak extends BlockAbstract {

    public BlockWeak() {
        this(true);
    }

    public BlockWeak(boolean visible) {
        vis = visible;
        destroyable = true;
    }

    public int destroy() {
        vis = false;
        return 10;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vis) {
            setBackground(Color.BLUE);
        } else {
            setOpaque(false);
        }
    }
}
