package model.map.tile;


import static model.map.tile.Terrain.MotionType.ALL_PASS;

import model.game_object.GameObject;

/**
 *
 * @author ChrisMoscoso
 */
public class Terrain {

    GameObject object;
    // Collision collision;

    public enum MotionType {
        ALL_PASS,
        NO_GROUND_PASS,
        UNPASSABLE
    }
    
    private MotionType motiontype;

    /**
     * Return grass terrain by default
     */
    public Terrain(){
        this("Grass", "Please Keep Off", ALL_PASS);
    }
    
    /**
     * 
     * @param name
     * @param description
     * @param type 
     */
    public Terrain(String name, String description, MotionType type){
        

    }

    /**
     * 
     * @return 
     */
    public MotionType getMotiontype() {
        return motiontype;
    }
    
    
}
