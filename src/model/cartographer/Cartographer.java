package model.cartographer;

import model.map.GameMap;

/**
 * Maintains a list of all the different game maps of the overworld and keeps track of the
 * current active map. It provides map info to external systems.
 */
public class Cartographer {
    private static GameMap activeMap = new GameMap();
    private static Cartographer instance = new Cartographer();
    
    private Cartographer() {}

    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static Cartographer getInstance() {
        return instance;
    }

    /**
     * Sets the active map
     *
     * @param activeMap is the new map to be active.
    */
    public static void setActiveMap(GameMap activeMap) {
        Cartographer.activeMap = activeMap;
    }

    /**
     * Gets the active map
     *
     * @return the map that is active
     */
    public static GameMap getActiveMap() {
        return Cartographer.activeMap;
    }
}