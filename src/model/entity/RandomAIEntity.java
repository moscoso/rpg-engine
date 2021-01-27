
package model.entity;

import java.util.Random;

import model.factory.SpriteFactory;
import model.util.gameTimer.GameTimer;
import model.util.gameTimer.GameTimerListener;

/**
 *
 * @author ChrisMoscoso
 */
public class RandomAIEntity extends Entity {
    
    private static Random random = new Random();
    
    public RandomAIEntity(){
        this.setSpritePath(SpriteFactory.RAT);
        this.myStats.setSpeed(15);
        this.myStats.setHealthRegenPerSecond(10);
    }
    
    @Override
    public void move(){
        if(canMove){
            int rx = random.nextInt(3) - 1;
            int ry = random.nextInt(3) - 1;
            
            location.translate(rx, ry);
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
