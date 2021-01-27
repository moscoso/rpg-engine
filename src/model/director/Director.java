package model.director;

import controller.AvatarKeyController;
import controller.MenuKeyController;
import model.menu.Menu;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Random;

import model.cartographer.Cartographer;
import model.entity.Avatar;
import model.entity.Entity;
import model.entity.RandomAIEntity;
import model.entity.WalkInLoopAIEntity;
import model.map.GameMap;
import model.menu.Menu.MenuOption;
import view.window.GameWindow;
import view.scene.Scene;
import view.viewport.MainMenuViewPort;
import view.viewport.MapViewPort;
import view.viewport.MiniMapViewPort;
import view.viewport.PauseMenuViewPort;
import view.viewport.StatusViewPort;

/**
 * The Game Director oversees the integration of the game's various subsystems.
 * It initializes the game by creating all the game objects (model, view, and
 * controller) to start. It is also responsible for pausing and resuming gameplay
 *
 * @author ChrisMoscoso
 */
public class Director extends Observable {
    private static Director instance;

    private static Boolean paused = false;
    private static GameWindow window = new GameWindow();;
    private static Scene menuScene, gameScene;
    private static Scene activeScene;

    private static Menu mainMenu, pauseMenu;

    private static MenuKeyController mainMenuKC, pauseMenuKC;
    private static AvatarKeyController avatarKC;

    private static GameMap activeMap = Cartographer.getActiveMap();

    private static Random random = new Random();

    private Director() {
        Scene.setSceneSize(window.getSize());
        startMenuScene();
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
     * This sets up the main menu scene
     */
    public static void startMenuScene() {
        mainMenu = new Menu();
        menuScene = new Scene();

        MainMenuViewPort menuVP = new MainMenuViewPort();
        menuScene.addViewPort(menuVP);

        //Add observers to model object
        mainMenu.addObserver(menuVP);

        //Add controllers to the window
        mainMenuKC = new MenuKeyController(mainMenu, menuScene);
        window.addKeyController(mainMenuKC);

        activeScene = menuScene;
    }

    /**
     * This starts a new game programmatically.
     */
    public static void startNewGame() {
        //Create Game Scene
        gameScene = new Scene();

        GameMap activeMap = Cartographer.getActiveMap();

        activeMap.addEntity(Avatar.getInstance());

        for(int i = 0 ; i < 50; i++) {
            Entity e = new RandomAIEntity();
            activeMap.addEntity(e);
            int randomX = random.nextInt(50);
            int randomY = random.nextInt(50);
            e.setLocation(randomX, randomY);
        }   


        //Create Pause Menu 
        MenuOption[] pauseMenuOptions = {MenuOption.RESUME_GAME, MenuOption.SAVE_GAME, MenuOption.RETURN_TO_MAIN_MENU};
        pauseMenu = new Menu(pauseMenuOptions);
        paused = false;
        pauseMenu.hide();

        //Create Viewports
        MapViewPort mapVP = new MapViewPort();
        MiniMapViewPort miniMapVP = new MiniMapViewPort();
        PauseMenuViewPort pauseVP = new PauseMenuViewPort();
        StatusViewPort statusVP = new StatusViewPort();

        //Add viewports to scene
        gameScene.addViewPort(mapVP);
        gameScene.addViewPort(pauseVP);
        gameScene.addViewPort(statusVP);
        gameScene.addViewPort(miniMapVP);

        //Add viewports as observers to model objects.
        activeMap.addObserver(mapVP);
        activeMap.addObserver(miniMapVP);
        pauseMenu.addObserver(pauseVP);
        Avatar.getInstance().addObserverOfStats(statusVP);

        //Add controllers to window
        pauseMenuKC = new MenuKeyController(pauseMenu, gameScene);
        window.addKeyController(pauseMenuKC);
        avatarKC = new AvatarKeyController();
        window.addKeyController(avatarKC);
        

        //Set game scene as active scene
        activeScene = gameScene;

    }

    /**
     * This is where execution of the game logic and updating of the model takes
     * place
     */
    public void updateGame() {
        if (gameIsRunning()) {
            //The game is runnning
            tick();
            if (activeMap != null) {
                activeMap.moveGameObjects();
                activeMap.regenerateEntities();
                activeMap.checkProjectiles();
            }

        } else {
            //Some menu is active
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
     * Uses a double buffering technique to paint the image to the screen. First
     * it renders the game to a bufferedImage. Then, it takes the bufferedImage
     * and paints it to the screen.
     */
    public static void drawGame() {
        BufferedImage gameImage = activeScene.getImage();//render the game to buffer
        window.paintImageToScreen(gameImage); //paint the buffer to screen
    }

    /**
     * This returns the dimensions of the game window in pixels
     *
     * @return the dimensions of the game window in pixels
     */
    public static Dimension getSize() {
        return window.getSize();
    }

    /**
     * Pauses game play only. Menus will still work.
     */
    public static void pauseGame() {
        paused = true;
        if (pauseMenu != null) {
            pauseMenu.show();
        }
    }

    /**
     * Resumes game play.
     */
    public static void resumeGame() {
        paused = false;
        if (pauseMenu != null) {
            pauseMenu.hide();
        }
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
    public static void returnToMainMenu() {
        setActiveScene(menuScene);
    }

    /**
     * Ticks all observing game timers.
     */
    private static void tick() {
        Director.getInstance().setChanged();
        Director.getInstance().notifyObservers();
    }
}
