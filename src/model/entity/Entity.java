package model.entity;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import model.ability.Ability;
import model.cartographer.Cartographer;
import model.engine.Engine;
import model.factory.SpriteFactory;
import model.game_object.GameObject;
import model.map.Direction;
import model.stats.PlayerStats;
import model.util.gameTimer.GameTimer;
import model.util.gameTimer.GameTimerListener;

/**
 * The class Entity defines a common type for all entities (beings) in the game.
 *
 * @author Matthew Kroeze, Chris Moscoso
 * @version 1.0.0 2015-03-14
 */
public class Entity extends GameObject implements Observer {

    protected TreeMap<String, Ability> abilities = new TreeMap<>();

    protected Direction myDirection;
    protected PlayerStats myStats;

    protected boolean canMove = true;
    protected boolean moveHasBeenCommanded = false;

    protected Point location;

    private int isMovingY;
    private int isMovingX;
    private boolean canRegenHealth = true;
    private boolean canRegenMana = true;

    private static final int REGEN_UPDATES_PER_SECOND = 5;

    public Entity() {
        myDirection = Direction.SOUTH;
        location = new Point(1, 1);
        myStats = new PlayerStats();
        myStats.addObserver(this);
    }

    public Direction getDirection() {
        return myDirection;
    }

    /**
     * Kill the entity in the game.
     */
    public void die() {
        Cartographer.getActiveMap().removeEntity(this);
    }

    /**
     * Sets the direction that the entity will face and move in.
     *
     * @param d the direction it will face.
     *
     * POSTCONDITION: Entity's myDirection will equal d. The spritePath will
     * reset to direction d.
     */
    public void setDirection(Direction d) {
        myDirection = d;
        switch (d) {
            case NORTH:
                this.setSpritePath(SpriteFactory.PLAYER_NORTH);
                break;
            case EAST:
                this.setSpritePath(SpriteFactory.PLAYER_EAST);
                break;
            case SOUTH:
                this.setSpritePath(SpriteFactory.PLAYER_SOUTH);
                break;
            case WEST:
                this.setSpritePath(SpriteFactory.PLAYER_WEST);
                break;
            default: 
                break;
        }
    }

    /**
     * This specifies if the player is moving in the x direction.
     *
     * @param b if true the player will move in the x direction
     *
     * POSTCONDITION: isMovingY will be equal to 0 or 1. If 1, the player will
     * move in the x direction. If 0, the player will not move in the x
     * direction.
     */
    public void isMovingX(Boolean b) {
        isMovingX = b ? 1 : 0;
    }

    /**
     * This specifies if the player is moving in the y direction.
     *
     * @param b if true the player will move in the Y direction
     *
     * POSTCONDITION: isMovingY will be equal to 0 or 1. If 1, the player will
     * move in the y direction. If 0, the player will not move in the y
     * direction.
     */
    public void isMovingY(Boolean b) {
        isMovingY = b ? 1 : 0;
    }

    public PlayerStats getPlayerStats() {
        return myStats;
    }

    /**
     * Get the location of the entity.
     *
     * @return the location of the entity as a point (unit is tiles)
     */
    @Override
    public Point getLocation() {
        return location;
    }

    /**
     * Sets the location of the entity on the map
     *
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     */
    public void setLocation(int x, int y) {
        location = new Point(x, y);
    }

    /**
     * Changes the entity's location by translating the position by the
     * velocity.
     */
    public void move() {
        if (canMove && moveHasBeenCommanded) {
            location.translate(isMovingX * myDirection.dx, isMovingY * myDirection.dy);
            canMove = false;
            moveHasBeenCommanded = false;
            GameTimer x = new GameTimer(this.myStats.getSpeed());
            x.setGameTimerListener(new GameTimerListener() {

                @Override
                public void trigger() {
                    canMove = true;
                }

            });
            x.start();
        }
    }

    /**
     * Regenerates a small portion of the entity's health and mana
     */
    public void regenerate() {
        if (myStats.getCurrentHealth() < myStats.getMaxHealth() && canRegenHealth) {
            this.regenerateHealth();
        }

        if (myStats.getCurrentMana() < myStats.getMaxMana() && canRegenMana) {
            this.regenerateMana();
        }
    }

    public boolean moveHasBeenCommanded() {
        return moveHasBeenCommanded;
    }

    private void setMoveHasBeenCommanded(boolean moveHasBeenCommanded) {
        this.moveHasBeenCommanded = moveHasBeenCommanded;
    }

    /**
     * Public method to set move has been commanded.
     */
    public void setMoveHasBeenCommanded() {
        setMoveHasBeenCommanded(true);
    }

    @Override
    //Called when stats object updates
    public void update(Observable o, Object arg) {
        if (myStats.getCurrentHealth() <= 0) {
            this.die();
        }
    }

    private void regenerateHealth() {
        if (canRegenHealth) {

            canRegenHealth = false;
            GameTimer globalRegenTimer = new GameTimer(Engine.getFPS() / REGEN_UPDATES_PER_SECOND);
            globalRegenTimer.setGameTimerListener(new GameTimerListener() {

                @Override
                public void trigger() {
                    canRegenHealth = true;
                    myStats.modCurrentHealth(myStats.getHealthRegenPerSecond() / REGEN_UPDATES_PER_SECOND);
                }

            });

            globalRegenTimer.start();
        }
    }

    /**
     * 
     */
    private void regenerateMana() {
        if (canRegenMana) {
            

            canRegenMana = false;
            GameTimer globalRegenTimer = new GameTimer(Engine.getFPS() / REGEN_UPDATES_PER_SECOND);
            globalRegenTimer.setGameTimerListener(new GameTimerListener() {

                @Override
                public void trigger() {
                    canRegenMana = true;
                    myStats.modCurrentMana(myStats.getManaRegenPerSecond() / REGEN_UPDATES_PER_SECOND);
                }

            });

            globalRegenTimer.start();
        }
    }
}
