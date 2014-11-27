package breakout;

import java.util.Vector;

public class LevelPackage {
    public static Vector<Level> GetStandardLevels() {
        Vector<Level> levels = new Vector<Level>();

        Level level = new Level(2, 5, new Position(30, 300), new Transform(2 ,4));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockStrong());
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(100));
        level.addBlock(new BlockWeak(false));
        levels.add(level);

        level = new Level(3, 5, new Position(40, 300), new Transform(-2 ,4));
        level.addBlock(new BlockWeak(50));
        level.addBlock(new BlockWeak(20));
        level.addBlock(new BlockWeak(30));
        level.addBlock(new BlockWeak(40));
        level.addBlock(new BlockWeak(false));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockStrong());
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(100));
        level.addBlock(new BlockWeak(70));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockStrong());
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(100));
        level.addBlock(new BlockWeak(70));
        levels.add(level);

        level = new Level(3, 5, new Position(40, 300), new Transform(-2 ,4));
        level.addBlock(new BlockWeak(50));
        level.addBlock(new BlockWeak(20));
        level.addBlock(new BlockWeak(30));
        level.addBlock(new BlockWeak(40));
        level.addBlock(new BlockWeak(5));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(5));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(100));
        level.addBlock(new BlockWeak(70));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(5));
        level.addBlock(new BlockWeak(10));
        level.addBlock(new BlockWeak(100));
        level.addBlock(new BlockWeak(70));
        levels.add(level);

        return levels;
    }
}
