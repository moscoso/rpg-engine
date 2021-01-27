package model.game_object;

import java.awt.Image;
import java.awt.Point;
import model.factory.SpriteFactory;

public class GameObject {

    private final String name;
    private final String description;
    private String spritePath = null;

    //Added this, necessary to save game state. 
    private Point location;

    //This constructor should be called only after the subclass constructor is called
    public GameObject() {
        name = "Generic Object";
        description = "Generic description";
        location = new Point(1, 1); // default constructor, (0 , 0)
    }

    public GameObject(String objectName, String description, int ID) {
        this.name = objectName;
        this.description = description;
        location = new Point(1, 1);
    }

    /*
     * Get's the game objects current state. By default the return value is 0. 
     * Each game object that actually has multiple states should override
     * this method and use logic to return different states. The int return 
     * value should be specified in a a public enum in that game object 
     * called States
     */
    public int getState() {
        return 0;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the location of the GameObject.
     *
     * @return the location of the projectile as a point (unit is tiles)
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Sets the location of the GameObject on the map
     *
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public void setLocation(int x, int y) {
        location = new Point(x, y);
    }

    public Image getSprite() {
        return SpriteFactory.getSprite(spritePath);
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public boolean collides(GameObject o) {
        return this.getLocation().equals(o.getLocation());
    }
}
