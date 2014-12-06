package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class BlockWeak extends BlockAbstract {

    public int weight;
    public boolean editible = false;

    /**
     * Constructor which sets visibility of a BlockWeak, and sets weight to 10.
     * @param visible visibility of the BlockWeak object
     */
    public BlockWeak(boolean visible) {
        this(visible, 10);
    }

    /**
     * Constructor based on weight of a BlockWeak. Defaults visibility to true.
     * @param weight weight of the BlockWeak object
     */
    public BlockWeak(int weight) {
        this(true, weight);
    }

    /**
     * Constructor used to manually set the visibility and weight of a BlockWeak object.
     * Weight is limited to 100. All BlockWeak objects are destroyable.
     * @param visible visibility of the BlockWeak object
     * @param weight weight of the BlockWeak object
     */
    public BlockWeak(boolean visible, int weight) {
        vis = visible;
        destroyable = true;

        if (weight > 100)
            weight = 100;

        this.weight = weight;
    }

    /**
     * "Destroys" the BlockWeak by setting its visibility to false.
     * @return weight of the specified object
     */
    @Override
    public int destroy() {
        vis = false;
        return weight;
    }

    /**
     * Copies the specified BlockWeak object.
     * @return a new BlockWeak object which is the same as the calling object
     */
    @Override
    public BlockAbstract copy() {
        return new BlockWeak(vis, weight);
    }

    /**
     * Sets the editabliity of a BlockWeak.
     * @param editible editability of the BlockWeak object
     * @return the calling object
     */
    public BlockWeak setEditible(boolean editible) {
        this.editible = editible;
        return this;
    }

    /**
     * Returns the weight of the BlockWeak object.
     * @return the weight of the specified object.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Overridden paintComponent method to draw the BlockWeak according to its
     * weight, based on a gradient.
     * @param g abstract Graphics class used to paint all necessary Components
     */
    @Override
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