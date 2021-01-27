package view.viewport;

import java.awt.Graphics;

/**
 * A viewport is a collection of graphical objects that represent a portion of
 * the game. Think of a Viewport as a screen onto which the game is projected.
 * Viewports can be added to the scene so that there are multiple surfaces to draw on.
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
