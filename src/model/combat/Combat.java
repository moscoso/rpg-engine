
package model.combat;

import java.util.ArrayList;
import java.util.List;

import model.cartographer.Cartographer;
import model.entity.Entity;
import model.projectile.Projectile;
import model.stats.PlayerStats;

/**
 *
 * @author ChrisMoscoso
 */
public class Combat {
    private static Combat instance = new Combat();

    private Combat() {}

    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static Combat getInstance() {
        return instance;
    }

    public static void spawnProjectile(model.projectile.Projectile p) {
        Cartographer.getActiveMap().addProjectile(p);
    }
    
    public static void checkCollideProjectiles(List<Projectile> projectiles, List<Entity> entityList){
        for(Projectile projectile : new ArrayList<>(projectiles)) {
            for(Entity entity : new ArrayList<>(entityList)) {
                if(entity != projectile.getOwner() && projectile.collides(entity)){
                    int maxHealth = entity.getPlayerStats().getMaxHealth();
                    int damage = (int) Math.floor( maxHealth / 2.0 );
                    entity.getPlayerStats().modCurrentHealth(-damage);
                    projectiles.remove(projectile);
                }
            }
        }
    }    
}
