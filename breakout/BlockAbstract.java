package breakout;

import javax.swing.JPanel;

public abstract class BlockAbstract extends JPanel {
    protected boolean isVisible;
    protected boolean isDestroyable;

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public abstract boolean destroy();
}
