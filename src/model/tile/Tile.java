package model.tile;

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

    String spritePath; 
	Terrain terrain;
	
    /**
     * By default return an empty tile of default terrain
     */
	public Tile(){
        this(new Terrain() );
	}
	
    /**
     * 
     */
	public Tile(Terrain t){
		terrain = t;
	}
	
	/**
	 * Adds a terrain to this tile
	 * 
	 * @param t the terrain to set the tile to
	 */
	public void setTerrain(Terrain t){
		terrain = t;
	}
}
