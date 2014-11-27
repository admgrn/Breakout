package breakout;

import java.util.LinkedList;

public class Level {

    private Size size;
    private LinkedList<BlockAbstract> blocks;
    private Position start;


    public Level (int width, int height, Position start) {
        size = new Size(width, height);
        blocks = new LinkedList<BlockAbstract>();
        this.start = start;
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

    public Position getStart() {
        return start;
    }

    public void setStart(Position start) {
        this.start = start;
    }

}
