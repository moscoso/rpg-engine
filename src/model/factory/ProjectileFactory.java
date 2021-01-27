package model.factory;

import model.entity.Avatar;
import model.entity.Entity;
import model.map.Direction;
import model.projectile.Projectile;

/**
 * A projectile factory should be able to turn projectileIDs into projectiles
 * @author ChrisMoscoso
 */
public class ProjectileFactory {
    
    private static final int DEFAULT_RANGE = 10;
    private static final int DEFAULT_SPEED = 1;


    private ProjectileFactory() {}
    
    /**
     * Creates a new fireball at the specified location in the specified
     * direction.
     *
     * @param x the x location of the projectile
     * @param y the y location of the projectile
     * @param direction the direction of the projectile
     * @param s the player's stats from the entity that spawn the fireball
     * @return a new fireball projectile.
     */
    public static Projectile newFireBall(int x, int y, Direction direction) {
        Projectile p = new Projectile(x, y, direction, DEFAULT_RANGE, DEFAULT_SPEED, Avatar.getInstance());
        String sprite = getSpriteForDirection(p.getDirection());
        p.setSpritePath(sprite);
        return p;
    }


    /**
     * Creates a new Fireball by the Entity that spawned it
     * @param entity the entity that spawned the FireBall
     * @return a new fireball projectile.
     */
    public static Projectile newFireBall(Entity e) {
        Projectile p = new Projectile(e);
        String sprite = getSpriteForDirection(p.getDirection());
        p.setSpritePath(sprite);
        return p;
    }

    private static String getSpriteForDirection(Direction d) {
        switch (d) {
            case NORTH:
                return SpriteFactory.FIREBALL_NORTH;
            case EAST:
                return SpriteFactory.FIREBALL_EAST;
            case SOUTH:
                return SpriteFactory.FIREBALL_SOUTH;
            case WEST:
                return SpriteFactory.FIREBALL_WEST;
            default:
                return SpriteFactory.FIREBALL_NORTH;
        }
    }
}
