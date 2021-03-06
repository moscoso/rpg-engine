package model.map;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;

import model.combat.Combat;
import model.entity.Entity;
import model.game_object.GameObject;
import model.tile.Tile;
import model.projectile.Projectile;
import model.vector.Vector2;

/**
 * Each game map represents the space that the player travels through.
 * It is a container that groups all the game objects and tiles,
 * into a single world.
 *
 *
 * @author Hanif, ChrisMoscoso, Jason Owens
 */
public class GameMap extends Observable {

    private ArrayList<GameObject> gameObjList;
    private ArrayList<Entity> entityList;
    private ArrayList<Projectile> projectileList;
    private Tile[][] tiles;

    public GameMap() {
        Tile[][] t = new Tile[50][50];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                t[i][j] = new Tile();
            }
        }

        this.tiles = t;

        entityList = new ArrayList<>();
        projectileList = new ArrayList<>();
        gameObjList = new ArrayList<>();
    }

    public GameMap(Tile[][] tiles) {
        this.tiles = tiles;

        entityList = new ArrayList<Entity>();
    }

    public void addEntity(Entity e) {
        entityList.add(e);
        gameObjList.add(e);
    }

    public void addProjectile(Projectile p) {
        projectileList.add(p);
        gameObjList.add(p);
    }

    /**
     * Returns how wide the map is in number of tiles.
     *
     * @return the width of the map in tiles
     */
    public int getWidth() {
        return this.tiles.length;
    }

    /**
     * Returns how high the map is in number of tiles.
     *
     * @return the height of the map in tiles
     */
    public int getHeight() {
        return this.tiles[0].length;
    }

    /**
     * This should be called when you want to update the view objects observing
     * the map (in this case the MapViewPort). For the sake of encapsulation it
     * only passes the objects needed for drawing the view.
     */
    private void updateView() {
        setChanged();
        Object[] objects = {tiles, entityList, projectileList};
        notifyObservers(objects);
    }

    @Override  // Same as super but we want to update the view as soon as the view gets added as an Observer
    public void addObserver(Observer o) {
        super.addObserver(o);
        updateView();
    }

    /**
     * Regenerates all the entities on the map.
     */
    public void regenerateEntities() {
        for (Entity e : entityList) {
            e.regenerate();
        }
    }

    /**
     *
     * @param e the entity to remove from the map
     */
    public void removeEntity(Entity e) {
        entityList.remove(e);
    }

    /**
     *
     * @param p the projectile to remove from the map
     */
    public void removeProjectile(Projectile p) {
        projectileList.remove(p);
    }

    /**
     * Moves all the game objects to move.
     */
    public void moveGameObjects() {
        for (Entity e : entityList) {
            e.move();
            // boundGameObject(e);
            warpGameObject(e);
        }

        try {
            for (Projectile p : projectileList) {
                p.move();
                boundGameObject(p);
            }
        } catch (ConcurrentModificationException e) {
        }

    }

    /**
     * Checks if the object is outside of the map.
     *
     * @param o the game object to check
     * @return
     */
    public boolean isOutsideMap(GameObject o) {
        Vector2 position = o.getTransform().getPosition();
        return position.x < 0
            || position.x > this.getWidth() - 1
            || position.y > this.getHeight() - 1
            || position.y < 0;
    }

    /**
     * An entity that surpasses the edge of the map will be moved back to the edge
     *
     * @param o the game object to bound
     */
    public void boundGameObject(GameObject o) {
        Vector2 position = o.getTransform().getPosition();
        if (position.x > this.getWidth() - 1) {
            position.x = this.getWidth() - 1;
        } else if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > this.getHeight() - 1) {
            position.y = this.getHeight() - 1;
        } else if (position.y < 0) {
            position.y = 0;
        }
    }

    /**
     * An entity that surpasses the edge of the map will warp to the opposite side of the map.
     *
     * @param o the entity to warp
     */
    public void warpGameObject(GameObject o) {
        Vector2 position = o.getTransform().getPosition();
        if (position.x > this.getWidth()) {
            position.x = 0;
        } else if (position.x < 0) {
            position.x = this.getWidth();
        }
        if (position.y > this.getHeight()) {
            position.y = 0;
        } else if (position.y < 0) {
            position.y = this.getHeight();
        }
    }

    public void checkProjectiles() {
        Combat.checkCollideProjectiles(projectileList, entityList);
    }
}
