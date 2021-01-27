package model.map.tile;

import model.entity.Entity;
import model.game_object.GameObject;

/**
 * A trap activates an effect when touched by an entity. 
 * 
 * 
 */
public abstract class Trap extends GameObject{
	
	Trap(){}
	
	/**
	 * Method to be overridden by subclasses to provide unique functionality
	 * when the trap is touched.
	 * 
	 * @author Aidan Pace
	 * @param caller The entity that activated the trap
	 * @see Entity
	 */
	abstract void activate(Entity caller);
}