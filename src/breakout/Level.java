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

    /**
     * Constructor which fine-tunes all member data of the object.
     * @param width width of the Level object
     * @param height height of the Level object
     * @param start start Position of the Level object
     * @param startTransform start Transform of the Level object
     */
    public Level (int width, int height, Position start, Transform startTransform) {
        size = new Size(width, height);
        blocks = new LinkedList<>();
        this.start = start;
        this.startTransform = startTransform;
    }

    /**
     * Creates a Level object using member data of another Level object.
     * @param level the Level object to copy
     */
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

    /**
     * Add a block to the Level object.
     * @param block the block to be added
     */
    public void addBlock(BlockAbstract block) {
        if (block.isDestroyable() && block.isVis()) {
            ++remaining;
        }

        ++maxCount;
        blocks.add(block);
    }

    /**
     * Clears a block from the blocks LinkedList.
     * @return the popped BlockAbstract
     */
    public BlockAbstract popBlock() {
        BlockAbstract block = blocks.pop();

        if (block.isDestroyable() && block.isVis()) {
            --remaining;
        }
        --maxCount;
        return block;
    }

    /**
     * Determines win state based on remaining blocks.
     * @return boolean value indicating win state
     */
    public boolean didWin() {
        return  maxCount - blocks.size() >= remaining;
    }

    /**
     * Returns the size of the Level object.
     * @return the size of the Level object
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the Level object.
     * @param size new size of the Level object
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Returns the LinkedList of blocks stored in the Level.
     * @return the LinkedList of blocks
     */
    public LinkedList<BlockAbstract> getBlocks() {
        return blocks;
    }

    /**
     * If a block is replaced in the LevelEditor, it needs to be ensured that the
     * remaining blocks is still accurate.
     * @param newBlock the new block added to the Level
     * @param oldBlock the replaced block removed from the Level
     */
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

    /**
     * Returns the remaining blocks in the Level.
     * @return the remaining blocks in the Level.
     */
    public int getRemaining() {
        return remaining;
    }

    /**
     * Returns the starting Position.
     * @return the starting Position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Returns the starting transformation.
     * @return the starting Transform
     */
    public Transform getStartTransform() {
        return startTransform;
    }
}
