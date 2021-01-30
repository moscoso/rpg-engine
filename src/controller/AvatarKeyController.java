package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.director.Director;
import model.entity.Avatar;
import model.map.Direction;
import view.scene.Scene;

/**
 *
 * @author ChrisMoscoso
 */
public class AvatarKeyController implements KeyListener {

    Avatar avatar = Avatar.getInstance();
    Scene scene; 

    public AvatarKeyController(Scene s) {
        scene = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore because we are not interested in the key Typed event
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Director.gameIsRunning() && Director.getActiveScene().equals(scene)) {
            
            movePlayer(e);

            if (e.getKeyCode() == KeyEvent.VK_1) {
                Avatar.shoot();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Director.pauseGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!Director.gameIsPaused()) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                avatar.isMovingY(false);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                avatar.isMovingX(false);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                avatar.isMovingX(false);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                avatar.isMovingY(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Director.pauseGame();
            }
        }

    }

    private void movePlayer(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            avatar.setDirection(Direction.NORTH);
            avatar.isMovingY(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            avatar.setDirection(Direction.EAST);
            avatar.isMovingX(true);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            avatar.setDirection(Direction.WEST);
            avatar.isMovingX(true);

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            avatar.setDirection(Direction.SOUTH);
            avatar.isMovingY(true);
        }
    }

}
