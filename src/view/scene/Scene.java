package view.scene;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import view.viewport.ViewPort;

/**
 * A scene is collection of {@link viewport.ViewPort}.
 *
 * @see viewport.ViewPort
 * @author ChrisMoscoso
 */
public class Scene {

    private ArrayList<ViewPort> viewports;
    private static Dimension size;

    public Scene() {
        this.viewports = new ArrayList<>();
    }

    /**
     * Adds a view port to the scene. Remember, the order that you add viewports
     * matters!
     *
     * @param view the viewport to be added
     */
    public void addViewPort(ViewPort view) {
        viewports.add(view);
    }

    /**
     * Adds an array of view ports to the scene.
     *
     * @param views the array of viewports to be added
     */
    public void addViewPorts(ViewPort[] views) {
        viewports.addAll(Arrays.asList(views));
    }

    /**
     * Iterates through the collection of viewports and draws their graphics to
     * the buffered image. 
     * 
     * Note: The layer of the viewport depends on the order in the
     * list. For example, the last viewport will be drawn on top of all the
     * other viewports and the first viewport will be draw below all the other
     * viewports.
     *
     * @return a buffered image of all of the viewports
     *
     * PRECONDITION: Viewports.getSize() > 0 
     * POSTCONDITION: BufferedImage with have altered pixel data.
     */
    public BufferedImage getImage() {
        BufferedImage i;
        if (size != null) {
            i = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        } else {
            i = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
        }
        for (ViewPort v : viewports) {
            v.draw(i.getGraphics());
        }
        return i;
    }

    /**
     * Sets the size that each scene should be
     *
     * @param size the size that all scenes should be in the window
     */
    public static void setSceneSize(Dimension size) {
        Scene.size = size;
    }

}
