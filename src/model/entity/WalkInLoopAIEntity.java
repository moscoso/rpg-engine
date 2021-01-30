
package model.entity;

import model.factory.SpriteFactory;
import model.map.Direction;
import model.vector.Vector2;
import model.game_timer.GameTimer;
import model.game_timer.GameTimerListener;


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
            Vector2 position = this.getTransform().getPosition();
            int nextX = path[currentStep % path.length].dx;
            int nextY = path[currentStep % path.length].dy;
            position.x += nextX;
            position.y += nextY;
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
