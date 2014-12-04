package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class BlockWeak extends BlockAbstract {

    public int weight;
    public boolean editible = false;

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
        return weight;
    }

    public BlockAbstract copy() {
        return new BlockWeak(vis, weight);
    }

    public BlockWeak setEditible(boolean editible) {
        this.editible = editible;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color color = Color.getHSBColor((float)weight / 100f, 0.89f, 0.6f);

        if (vis) {
            setBackground(color);
        } else if (!editible) {
            setOpaque(false);
        } else {
            setBackground(new Color(200, 200, 200));
        }
    }
}