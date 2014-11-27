package breakout;

import java.util.LinkedList;

public class Level {

    private Size size;
    private LinkedList<BlockAbstract> blocks;

    public Level (int width, int height) {
        size = new Size(width, height);
        blocks = new LinkedList<BlockAbstract>();
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public LinkedList<BlockAbstract>  getBlocks() {
        return blocks;
    }

    public void setBlocks(LinkedList<BlockAbstract> blocks) {
        this.blocks = blocks;
    }
}
