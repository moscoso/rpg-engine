package model.projectile;

import model.cartographer.Cartographer;
import model.entity.Avatar;
import model.entity.Entity;
import model.game_object.GameObject;
import model.map.Direction;
import model.game_timer.GameTimer;
import model.game_timer.GameTimerListener;

/**
 *
 * @author ChrisMoscoso
 */
public class Projectile extends GameObject{

    private Direction myDirection = Direction.EAST;
    private int speed = 1; 
    private int range = 10;
    private int distanceTraveled = 0;
    private boolean canMove = true;
    private Entity owner = null;
    
    /**
     * The default constructor should never really be called.
     */
    public Projectile(){
        this(1,1, Direction.EAST, 5, 1, Avatar.getInstance());//default
    }

    public Projectile(Entity owner) {
        this.setLocation(owner.getLocation().x, owner.getLocation().y);
        this.myDirection = owner.getDirection();
        this.owner = owner;
    }
    
    public Projectile(int x, int y, Direction direction, int range, int speed, Entity owner) {
        this.setLocation(x, y);
        myDirection = direction;
        this.range = range;
        this.speed = speed;
        this.owner = owner;
    }

    public Direction getDirection() {
        return myDirection;
    }

    /**
     * Changes the projectiles location by translating the position by the
     * velocity.
     */
    public void move() {
        if(canMove){
            getLocation().translate(myDirection.dx, myDirection.dy);
            canMove = false;
            GameTimer x = new GameTimer(speed);
            x.setGameTimerListener(new GameTimerListener() {

                @Override
                public void trigger() {
                    canMove = true;
                }

            });
            x.start();
            distanceTraveled++;
            if(endOfRangeReached()){
                this.disintegrate();
            }
        }
    }
    
    public boolean endOfRangeReached(){
        return distanceTraveled > range; 
    }
    
    public void disintegrate(){
        Cartographer.getActiveMap().removeProjectile(this);
    }
    
    public Entity getOwner() {
        return this.owner;
    }
    
    
}
