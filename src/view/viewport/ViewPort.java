package view.viewport;

import java.awt.Graphics;

/**
 * A viewport is a collection of graphical objects that represent a portion of
 * the game.
 *
 * @author ChrisMoscoso
 */
public interface ViewPort {
   
    /**
     * This is where the viewport makes draw calls.
     * @param g the graphics object for the viewport to call draw commands on
     */
    public void draw(Graphics g);
}
