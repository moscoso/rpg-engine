package model.entity;

import java.awt.Point;
import java.util.Observer;

import model.combat.Combat;
import model.factory.ProjectileFactory;
import model.factory.SpriteFactory;
import model.util.gameTimer.GameTimer;
import model.util.gameTimer.GameTimerListener;

/**
 *
 * @author ChrisMoscoso
 */
public class Avatar extends Entity {

    private static Avatar avatar = new Avatar();
    private static boolean canShoot = true;

    private Avatar() {
        super();
        super.myStats.setSpeed(3);
        super.myStats.setOffense(4);
        super.myStats.setIntellect(10);
        super.myStats.setManaRegenPerSecond(10);
        this.setSpritePath(SpriteFactory.PLAYER_SOUTH);
    }

    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static Avatar getInstance() {
        return avatar;
    }

    public static void shoot() {
        Avatar avatar = Avatar.getInstance();
        if (canShoot) {
            int manaCostToShoot = 10;
            int current = avatar.myStats.getCurrentMana();
            if (current >= manaCostToShoot) {
                // Point location = avatar.getLocation();
                Combat.spawnProjectile(ProjectileFactory.newFireBall(avatar));
                avatar.myStats.setCurrentMana(current - manaCostToShoot);
            }
            canShoot = false;
            GameTimer ability1Cooldown = new GameTimer(5);
            ability1Cooldown.setGameTimerListener(new GameTimerListener() {

                @Override
                public void trigger() {
                    canShoot = true;
                }

            });
            ability1Cooldown.start();
        }
    }

    /**
     * Adds an observer to the stats of the avatar.
     *
     * @param o the observer to add.
     */
    public void addObserverOfStats(Observer o) {
        myStats.addObserver(o);
    }

}
