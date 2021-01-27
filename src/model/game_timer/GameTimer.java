package model.game_timer;

import java.util.Observable;
import java.util.Observer;
import model.director.Director;

/**
 * This game timer is based off of game engine "ticks". Every frame the
 * {@link Engine} will tell the {@link Director} to update game. This
 * will cause the GameDirector to tick.
 *
 * @see GameEngie, GameDirector
 *
 * @author Jason Owens, Christopher Moscoso
 */
public class GameTimer implements Observer{

    /**
     * A count of the number of ticks that have occured while this timer is actively running
     */
    private int ticks = 0;
    /**
     * Specifies how many ticks (or frames) the timer should wait before being triggered
     */
    private int delay;
    /**
     * This flag specifies whether or not the timer should loop by restarting after it is triggered
     */
    private boolean shouldLoop;
    /**
     * This flag is set to true when this timer is actively running, and false when it is paused
     */
    private boolean isRunning;

    /**
     * The GameTimerListener that executes when this timer's time has elapsed
     */
    private GameTimerListener gameTimerListener;

    /**
     * By default, construct a timer that lasts 30 frames (1 second) that does not loop.
     */
    public GameTimer() {
        this(30, false);
    }

    /**
     * Construct a timer that lasts "delay" number of ticks and does not loop.
     *
     * @param delay how long the timer takes to fire in game engine ticks. There
     * are 30 ticks in one second.
     */
    public GameTimer(int delay) {
        this(delay, false);
    }

    /**
     * Construct a timer that lasts "delay" number of ticks and specify whether
     * it should should loop.
     *
     * @param delay how long the timer takes to fire in number of ticks.
     * @param shouldLoop is whether or not the timer loops.
     */
    public GameTimer(int delay, boolean shouldLoop) {
        this.delay = Math.abs(delay);
        this.shouldLoop = shouldLoop;
        Director.getInstance().addObserver(this);
    }

    /**
     * Construct a timer that lasts "delay" number of seconds that does not
     * loop.
     *
     * @param delay how long the timer takes to fire in number of seconds.
     */
    /*public GameTimer(double delay) {
        this(delay, false);
    }*/

    /**
     * Construct a timer that lasts "delay" number of seconds and specify
     * whether it should loop.
     *
     * @param delay how long the timer takes to fire in number of seconds.
     * @param shouldLoop is whether or not the timer loops.
     */
    /*public GameTimer(double delay, boolean shouldLoop) {
        this.delay = (int) Math.abs(delay / (double) GameEngine.getFPS());
        this.shouldLoop = shouldLoop;
        GameDirector.getGameDirector().addObserver(this);
    }*/

    /**
     * Ticks the game timer.
     */
    private void tick() {
        if (this.isRunning() && Director.gameIsRunning()){
            ticks++;
            boolean timeIsUp = ticks == delay;
            if (timeIsUp) {
                this.fire();
                if(shouldLoop ){
                    ticks = 0;
                }else{
                    this.pause();
                    Director.getInstance().deleteObserver(this);
                }
            }
        }
    }

    /**
     * 
     * @param gameTimerListener the object that is triggered when this timer fires.
     */
    public void setGameTimerListener(GameTimerListener gameTimerListener) {
        this.gameTimerListener = gameTimerListener;
    }

    public void start() {
        isRunning = true;
    }

    public void pause() {
        isRunning = false;
    }

    public boolean isPaused() {
        return !isRunning();
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    public void reset(){
        delay = 0;
    }

    private void fire() {
        this.gameTimerListener.trigger();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(ticks <= delay){
            this.tick();
        }
    }

}
