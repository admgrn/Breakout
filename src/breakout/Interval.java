package breakout;

import java.awt.Point;

public class Interval {

    public final static int NONE = 0;
    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int TOP = 3;
    public final static int BOTTOM = 4;

    private int x1, x2;
    private int y1, y2;

    /**
     * Constructor for the Interval class.
     * @param x1 initial x value
     * @param y1 initial y value
     * @param x2 terminal x value
     * @param y2 terminal y value
     */
    public Interval(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    /**
     * Determines whether a collision with another object has occurred.
     * @param interval the Interval with which to compare
     * @return integer value indicating collision status
     */
    public int checkCollision(Interval interval) {
        int width;
        int height;

        if (x1 <= interval.x2 && x2 >= interval.x1) {

            width = Math.min(x2, interval.x2) - Math.max(x1, interval.x1);
        } else {
            return NONE;
        }

        if (y1 <= interval.y2 && y2 >= interval.y1) {
            height = Math.min(y2, interval.y2) - Math.max(y1, interval.y1);
        } else {
            return NONE;
        }

        if (width > height) {
            int a = (y1 + y2) / 2;
            int b = (interval.y1 + interval.y2) / 2;
            if (a > b) {
                return TOP;
            } else {
                return BOTTOM;
            }
        } else {
            int a = (x1 + x2) / 2;
            int b = (interval.x1 + interval.x2) / 2;
            if (a > b) {
                return RIGHT;
            } else {
                return LEFT;
            }
        }
    }

    /**
     * Used by level editor to check if the mouse pointer is clicked inside
     * of a BlockAbstract / BlockStrong / BlockWeak object.
     * @param point the position of the mouse pointer
     * @return boolean value indicating status
     */
    public boolean isPointInside(Point point) {
        return x1 <= point.x && x2 >= point.x && y1 <= point.y && y2 >= point.y;
    }

    /**
     * Returns y2 value.
     * @return y2 value
     */
    public int getY2() {
        return y2;
    }

    /**
     * Returns x1 value.
     * @return x1 value
     */
    public int getX1() {
        return x1;
    }

    /**
     * Returns x2 value.
     * @return x2 value
     */
    public int getX2() {
        return x2;
    }

    /**
     * Returns y1 value.
     * @return y1 value
     */
    public int getY1() {
        return y1;
    }
}