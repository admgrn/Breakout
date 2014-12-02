package breakout;

import javax.swing.JPanel;
import java.awt.*;

public abstract class BlockAbstract extends JPanel implements PositionInterval {
    protected boolean vis;
    protected boolean destroyable;

    public Interval getInterval() {
        Rectangle rect = getBounds();
        return new Interval((int)rect.getX(), (int)rect.getY(), (int)rect.getX() + getWidth(),
                            (int)rect.getY() + getHeight());
    }

    public boolean isVis() {
        return vis;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public abstract int destroy();

    public abstract BlockAbstract copy();
}
