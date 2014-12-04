package breakout;

import java.io.Serializable;

public class Size implements Serializable {
    int width, height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size(Size size) {
        this.width = size.width;
        this.height = size.height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}