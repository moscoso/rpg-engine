package model.engine;

import java.util.Observable;

import model.director.Director;
import view.window.GameWindow;
import java.awt.image.BufferedImage;


/**
 * The Engine is responsible for updating the game and rendering it to the
 * screen. It runs on a threaded loop that updates approximately x frames per
 * second.
 *
 * @author ChrisMoscoso
 */
public class Engine extends Observable implements Runnable {
    
    private static Engine instance = new Engine();
    private Thread thread;
    private static int framesPerSecond = 30;
    private static final boolean SHOULD_RUN_FOREVER = true;


    /**
     * This is a singleton and restricts the instantiation of a class to one "single" instance
     * @return the only instance of this class
     */
    public static Engine getInstance() {
        return instance;
    }

    private Engine() {
        this(30);
    }

    /**
     * Specify how fast the game engine should run in Frames Per Second
     * @param FramesPerSecond is how fast the game engine should tick 
     */
    private Engine(int framesPerSecond) {
        setFPS(framesPerSecond);
        thread = new Thread(this);
        start();
    }

    /**
     * Begins running the game engine
     */
    private void start() {
        thread.start();
    }

    /**
     * While the game engine runs, it continually a 2 step loop: update game,
     * draw game.
     */
    @Override
    public void run() {
        while (SHOULD_RUN_FOREVER) {
            updateGame();
            drawGame();
            try {
                Thread.sleep(33);
            } catch (InterruptedException ex) {
                // LOGGER.log(Level.WARN, "Interrupted!", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Update the game models
     */
    private void updateGame(){
        Director.getInstance().updateGame();
    }
    
    /**
     * Draws the game to the screen via a double buffering technique.
     * 
     * First it renders the game to a bufferedImage. 
     * Then, it takes the bufferedImage and paints it to the screen.
     */
    private void drawGame(){
        BufferedImage gameImage = Director.getActiveScene().getImage();//render the game to buffer
        GameWindow.getInstance().paintImageToScreen(gameImage); //paint the buffer to screen
    }

    public static void setFPS(int framesPerSecond) {
        Engine.framesPerSecond = framesPerSecond;
    }

    public static int getFPS() {
        return framesPerSecond;
    }
}
