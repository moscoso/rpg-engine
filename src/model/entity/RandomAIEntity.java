
package model.entity;

import java.util.Random;

import model.factory.SpriteFactory;
import model.game_timer.GameTimer;
import model.game_timer.GameTimerListener;
import model.vector.Vector2;


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
            Vector2 position = this.getTransform().getPosition();
            position.x += rx;
            position.y += ry;
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
