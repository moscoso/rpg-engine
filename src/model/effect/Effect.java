package model.effect;

import model.entity.Entity;

/**
 * Provides an interface for Effects to be applied to Entities
 * 
 * @author Jason Owens
 */
public interface Effect {    
    
    /**
     * Applies the effect to the entity
     * @param entity the entity to apply the effect to
     */
    public void apply(Entity entityToEffect);  
}