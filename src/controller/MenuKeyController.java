package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.director.Director;
import model.menu.Menu;
import view.scene.Scene;

/**
 * This is a key controller for a menu object. The way I have designed the
 * controller so far is that it only needs a reference to a menu. This cuts down
 * # of classes enormously. So far only one class in controller to get the game
 * running.
 *
 * @author ChrisMoscoso
 */
public class MenuKeyController implements KeyListener {

    private final Menu menu;
    private Scene scene;

    /**
     * The menu that the controller mutates.
     *
     * @param m the menu object that the controller object uses.
     * @param s the scene that the controller should be activated in
     */
    public MenuKeyController(Menu m, Scene s) {
        menu = m;
        scene = s;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore keyTyped event
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Director.getActiveScene().equals(scene) && menu.isVisible()) {
            this.navigateMenu(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ignore key released event
    }

    private void navigateMenu(KeyEvent e) {
        boolean inputNext = e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT;
        boolean inputPrev = e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT;
        if (inputNext) {
            menu.next();
        } else if (inputPrev) {
            menu.prev();
        }

        boolean inputConfirm = e.getKeyCode() == KeyEvent.VK_ENTER;
        if (inputConfirm) {
            switch (menu.getCurrentSelection()) {
                case NEW_GAME:
                    Director.goToGame();
                    break;
                case RESUME_GAME:
                    Director.resumeGame();
                    menu.setCurrentSelection(0);
                    break;
                case RETURN_TO_MAIN_MENU:
                    Director.goToMainMenu();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                case OPEN_SAVE_FILE:
                    break;
                case SAVE_GAME:
                    break;
                case SWITCH_TO_LOAD_MENU:
                    break;
                default:
                    break;
            }
        }
    }

}
