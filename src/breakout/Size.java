package breakout;

public class Size {
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}