package breakout;

import javax.swing.JPanel;
import java.awt.Rectangle;

public abstract class BlockAbstract extends JPanel implements PositionInterval {

    protected boolean vis;
    protected boolean destroyable;

    /**
     * Finds the bounds of the BlockAbstract, then returns its Interval based on
     * that information.
     * @return the Interval of the specified object
     */
    @Override
    public Interval getInterval() {
        Rectangle rect = getBounds();
        return new Interval((int)rect.getX(), (int)rect.getY(), (int)rect.getX() + getWidth(),
                            (int)rect.getY() + getHeight());
    }

    /**
     * Finds out if the BlockAbstract is visible or not.
     * @return the visibility of the specified object
     */
    public boolean isVis() {
        return vis;
    }

    /**
     * Finds out if the BlockAbstract is destroyable or not.
     * @return the destructibility of the specified object
     */
    public boolean isDestroyable() {
        return destroyable;
    }

    public abstract int destroy();

    public abstract BlockAbstract copy();
}
