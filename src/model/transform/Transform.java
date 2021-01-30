package model.transform;

import model.vector.Vector2;

/**
 * 
 * The MovableObject class represents any game object that has a transform (i.e., a position, orientation and optional scale).
 * 
 * Every object in a Scene has a Transform. It's used to store and manipulate
 * the position, rotation and scale of the object
 */
public class Transform {
    /**
     * The world space position of the Transform
     */
    Vector2 position = new Vector2();


    /**
     * Get the position of the GameObject.
     *
     * @return the position of the projectile as a point
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the position of the GameObject on the map
     *
     * @param x the x coordinate of the position
     * @param y the y coordinate of the position
     */
    public void setPosition(int x, int y) {
        position = new Vector2(x, y);
    }

}
