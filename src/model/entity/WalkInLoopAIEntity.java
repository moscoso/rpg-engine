
package model.entity;


import model.factory.SpriteFactory;
import model.map.Direction;
import model.util.gameTimer.GameTimer;
import model.util.gameTimer.GameTimerListener;

/**
 *
 * @author ChrisMoscoso
 */
public class WalkInLoopAIEntity extends Entity {
    
    private Direction[] path;
    private int currentStep = 0;
    
    public WalkInLoopAIEntity(){
        this.setSpritePath(SpriteFactory.RAT);
        Direction[] simpleLoop = {Direction.EAST, Direction.EAST, Direction.SOUTH, Direction.SOUTH, Direction.WEST, Direction.WEST, Direction.NORTH, Direction.NORTH};
        path = simpleLoop;
        this.myStats.setSpeed(5);
        this.myStats.setHealthRegenPerSecond(10);
    }
    
    /**
     * 
     * @param path 
     */
    public void setPath(Direction[] path){
        this.path = path;  
    }
    
    @Override
    public void move(){
        if(canMove){
            location.translate(path[currentStep % path.length].dx, path[currentStep % path.length].dy);
            currentStep++;
            canMove = false;
            GameTimer movement = new GameTimer(myStats.getSpeed());
            movement.setGameTimerListener(new GameTimerListener(){

                @Override
                public void trigger() {
                    canMove = true;
                }
                
            });
            movement.start();
        }
    }
}
