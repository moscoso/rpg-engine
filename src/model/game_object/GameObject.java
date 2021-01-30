package model.game_object;

import java.awt.Image;
import model.factory.SpriteFactory;
import model.transform.Transform;

/**
 * A GameObject is a container for a name, description, sprite, and location
 */
public class GameObject {

    private final String name;
    private final String description;
    private String spritePath = null;

    private Transform transform = new Transform();

    public GameObject() {
        name = "Generic Object";
        description = "Generic description";
    }

    public GameObject(String objectName, String description) {
        this.name = objectName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    /**
     * The Transform is used to store and manipulate the position, rotation and scale of the object
     *
     * @return the Transform of the gameobject
     */
    public Transform getTransform() {
        return transform;
    }

    public Image getSprite() {
        return SpriteFactory.getSprite(spritePath);
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public boolean collides(GameObject o) {
        return transform.getPosition().isEqualTo(o.transform.getPosition());
    }
}
