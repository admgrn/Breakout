package breakout;

import java.awt.*;

public class BlockStrong extends BlockAbstract {
    public BlockStrong() {
        vis = true;
        destroyable = false;
    }

    public int destroy() {
        return 0;
    }

    public BlockAbstract copy() {
        return new BlockStrong();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.gray);
    }
}
