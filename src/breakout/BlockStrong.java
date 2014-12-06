package breakout;

import java.awt.Color;
import java.awt.Graphics;

public class BlockStrong extends BlockAbstract {

    /**
     * Empty constructor for a BlockStrong object. BlockStrong objects cannot
     * be destroyed.
     */
    public BlockStrong() {
        vis = true;
        destroyable = false;
    }

    /**
     * Returns a failure of destruction.
     * @return zero to indicate failure to destroy
     */
    @Override
    public int destroy() {
        return 0;
    }

    /**
     * Copies the BlockStrong object. Since BlockStrong objects do not have much
     * information, this is as trivial as creating a new BlockStrong
     * @return a new BlockStrong object, since all BlockStrong objects are identical
     */
    @Override
    public BlockAbstract copy() {
        return new BlockStrong();
    }

    /**
     * Overridden paintComponent method to paint the BlockStrong. All BlockStrong objects
     * are gray.
     * @param g abstract Graphics class used to paint all necessary Components
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.gray);
    }
}