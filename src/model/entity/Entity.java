package model.entity;

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
import model.vector.Vector2;
import model.game_timer.GameTimer;
import model.game_timer.GameTimerListener;


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

    private int isMovingY;
    private int isMovingX;
    private boolean canRegenHealth = true;
    private boolean canRegenMana = true;

    private static final int REGEN_UPDATES_PER_SECOND = 5;

    public Entity() {
        myDirection = Direction.SOUTH;
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
    public void isMovingX(boolean b) {
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
    public void isMovingY(boolean b) {
        isMovingY = b ? 1 : 0;
    }

    public PlayerStats getPlayerStats() {
        return myStats;
    }

    /**
     * Changes the entity's location by translating the position by the
     * velocity.
     */
    public void move() {
        if (canMove) {
            Vector2 position = this.getTransform().getPosition();
            position.x += isMovingX * myDirection.dx;
            position.y += isMovingY * myDirection.dy;
            canMove = false;
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
