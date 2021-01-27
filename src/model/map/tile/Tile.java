package model.map.tile;

import model.entity.Entity;

/**
 * This class represents the basic display unit in-game. Tiles contain a terrain
 * and may contain a decal, area effect, and a trap.
 * 
 * 
 * @author Michael Cohen
 *
 */
public class Tile {

	Terrain terrain;
	Trap trap;
	
    /**
     * By default return an empty tile of default terrain
     */
	public Tile(){
        this(new Terrain(), null );
	}
	
    /**
     * 
     */
	public Tile(Terrain t, Trap tr){
		terrain = t;
		trap = tr;
	}
	
	/**
	 * Adds a terrain to this tile
	 * 
	 * @param t the terrain to set the tile to
	 */
	public void setTerrain(Terrain t){
		terrain = t;
	}

	/**
	 * Sets the trap to this tile
	 * 
	 * @author Michael Cohen
	 * @param t the trap to set on the tile
	 */
	public void addTrap(Trap t){
		trap = t;
	}
	
	/**
	 * Activates the Trap set on this
	 * 
	 * @param entity the entity that triggered the trap
	 */
	public void activateTrap(Entity entity){
        if(trap != null) {
            trap.activate(entity);
        }
    }
}
