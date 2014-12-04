package breakout;

import java.io.Serializable;
import java.util.LinkedList;

public class Level implements Serializable {

    private Size size;
    private LinkedList<BlockAbstract> blocks;
    private Position start;
    private Transform startTransform;
    private int remaining = 0;
    private int maxCount = 0;


    public Level (int width, int height, Position start, Transform startTransform) {
        size = new Size(width, height);
        blocks = new LinkedList<>();
        this.start = start;
        this.startTransform = startTransform;
    }

    public Level(Level level) {
        size = new Size(level.size);
        start = new Position(level.start);
        startTransform = new Transform(level.startTransform);
        remaining = level.remaining;
        maxCount = level.maxCount;
        blocks = new LinkedList<>();

        for (BlockAbstract b : level.blocks) {
            blocks.add(b.copy());
        }
    }

    public void addBlock(BlockAbstract block) {
        if (block.isDestroyable() && block.isVis()) {
            ++remaining;
        }

        ++maxCount;
        blocks.add(block);
    }

    public BlockAbstract popBlock() {
        BlockAbstract block = blocks.pop();

        if (block.isDestroyable() && block.isVis()) {
            --remaining;
        }
        --maxCount;
        return block;
    }

    public boolean didWin() {
        return  maxCount - blocks.size() >= remaining;
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

    public void updateRemainingAdd(BlockAbstract newBlock, BlockAbstract oldBlock) {
        if (newBlock.isDestroyable() && newBlock.isVis()) {
            if (!oldBlock.isDestroyable() || !oldBlock.isVis()) {
                ++remaining;
            }
        } else {
            if(oldBlock.isDestroyable() && oldBlock.isVis()) {
                --remaining;
            }
        }
    }

    public int getRemaining() {
        return remaining;
    }

    public Position getStart() {
        return start;
    }

    public Transform getStartTransform() {
        return startTransform;
    }
}
