package breakout;

public class BlockWeak extends BlockAbstract {

    public boolean destroy() {
        isVisible = false;
        return true;
    }
}
