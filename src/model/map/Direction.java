
package model.map;

import java.util.Random;

/**
 *
 * @author Jason
 */
public enum Direction {
    NORTH (0,-1),
    NORTH_EAST (1, -1),
    EAST (1,0),
    SOUTH_EAST (1, 1),
    SOUTH (0, 1),
    SOUTH_WEST (-1, 1),
    WEST (-1, 0),
    NORTH_WEST(-1, -1);
    /**
     * This is the unit value for the horizontal displacement [-1, 0 ,1].
     */
    public final int dx;

    /**
     * This is the unit value for the vertical displacement [-1, 0, 1].
     */
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

}