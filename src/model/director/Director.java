package model.director;

import java.util.Observable;

import model.cartographer.Cartographer;

import model.map.GameMap;
import view.window.GameWindow;
import view.scene.GameScene;
import view.scene.MenuScene;
import view.scene.Scene;

/**
 * The Game Director oversees the integration of the game's various subsystems.
 * It initializes the game by creating all the game objects (model, view, and
 * controller) to start. It is also responsible for pausing and resuming gameplay
 *
 * @author ChrisMoscoso
 */
public class Director extends Observable {
    private static Director instance;

    private static MenuScene menuScene = new MenuScene();
    private static GameScene gameScene = new GameScene();
    private static Scene activeScene;


    /**
     * This flag determines whether the game should update every frame or not
     */
    private static Boolean paused = false;

    private Director() {
        Scene.setSceneSize(GameWindow.getSize());
        setActiveScene(menuScene);
    }

    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static Director getInstance() {
        if(instance == null) {
            instance = new Director();
        }
        return instance;
    }


    /**
     * This is where execution of the game logic and updating of the model takes
     * place
     */
    public void updateGame() {
        if (gameIsRunning()) {
            tick();
            GameMap activeMap = Cartographer.getActiveMap();
            activeMap.moveGameObjects();
            activeMap.regenerateEntities();
            activeMap.checkProjectiles();
        }
    }

    /**
     * This sets the active scene which to be drawn.
     */
    private static void setActiveScene(Scene s) {
        activeScene = s;
    }

    /**
     * This gets which scene is currently active.
     *
     * @return the active scene.
     */
    public static Scene getActiveScene() {
        return activeScene;
    }

    /**
     * Pauses game play only. Menus will still work.
     */
    public static void pauseGame() {
        paused = true;
        gameScene.showPauseMenu();
    }

    /**
     * Resumes game play.
     */
    public static void resumeGame() {
        paused = false;
        gameScene.hidePauseMenu();
    }

    /**
     * Checks if game is paused
     *
     * @return true if the game is paused
     */
    public static boolean gameIsPaused() {
        return paused;
    }

    /**
     * Checks if the game is running
     *
     * @return true if the game is not paused
     */
    public static boolean gameIsRunning() {
        return !paused;
    }


    /**
     * Sets the main menu scene to be active.
     */
    public static void goToMainMenu() {
        setActiveScene(menuScene);
    }

    /**
     * Sets the Game scene to be active.
     */
    public static void goToGame() {
        setActiveScene(gameScene);
    }


    /**
     * Ticks all observing game timers.
     */
    private static void tick() {
        Director.getInstance().setChanged();
        Director.getInstance().notifyObservers();
    }
}
