package model.game_timer;

/**
 * The interface for a GameTimerListener acts as a container for a Trigger function that is to 
 * be executed when the specified time of a GameTimer has elapsed.
 * @author ChrisMoscoso
 */
public interface GameTimerListener {

    public void trigger();
}
