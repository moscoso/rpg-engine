package utility;

import java.awt.Point;

/**
 * This is a class used to set up bounds that can see if it contains a point.
 * Useful for controller and view.
 *
 * @author ChrisMoscoso
 */
public class Bounds {

    public final int x, y; //x and y is the location of the bounds upper left corner on the screen
    public final int w, h;

    /*i and j refer to the index in the double array*/
    public Bounds(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Checks if a point is inside the bounds.
     * @param p the point to check
     * @return true if the point is inside the bounds
     */
    public Boolean containsPoint(Point p) {
        boolean containsX = p.getX() >= x && p.getX() <= x + w;
        boolean containsY = p.getY() >= y && p.getY() <= y + h;
        return containsX && containsY;
    }

    @Override
    public String toString() {
        return "X: " + x + " to " + (x + w) + " Y: " + y + " to " + (y + h);
    }
}
