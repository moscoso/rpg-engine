package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.director.Director;
import model.entity.Avatar;
import model.map.Direction;

/**
 *
 * @author ChrisMoscoso
 */
public class AvatarKeyController implements KeyListener {

    Avatar avatar;

    public AvatarKeyController() {
        avatar = model.entity.Avatar.getInstance();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore because we are not interested in the key Typed event
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Director.gameIsRunning()) {
            
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
            avatar.setMoveHasBeenCommanded();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            avatar.setDirection(Direction.EAST);
            avatar.isMovingX(true);
            avatar.setMoveHasBeenCommanded();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            avatar.setDirection(Direction.WEST);
            avatar.isMovingX(true);
            avatar.setMoveHasBeenCommanded();

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            avatar.setDirection(Direction.SOUTH);
            avatar.isMovingY(true);
            avatar.setMoveHasBeenCommanded();
        }
    }

}
