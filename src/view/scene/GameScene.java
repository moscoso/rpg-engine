package view.scene;

import java.util.Random;

import controller.AvatarKeyController;
import controller.MenuKeyController;
import model.cartographer.Cartographer;
import model.entity.Avatar;
import model.entity.Entity;
import model.entity.RandomAIEntity;
import model.menu.Menu;
import model.menu.Menu.MenuOption;
import view.viewport.MapViewPort;
import view.viewport.MiniMapViewPort;
import view.viewport.PauseMenuViewPort;
import view.viewport.StatusViewPort;
import view.window.GameWindow;

public class GameScene extends Scene {
    Menu pauseMenu;
    boolean paused;
    Random random = new Random();

    MapViewPort mapVP = new MapViewPort();
    MiniMapViewPort miniMapVP = new MiniMapViewPort();
    PauseMenuViewPort pauseVP = new PauseMenuViewPort();
    StatusViewPort statusVP = new StatusViewPort();

    public GameScene() {
        initViewPorts();
        initPauseMenu();
        initEntities();
    }

    public void  initViewPorts() {
        addViewPort(mapVP);
        addViewPort(pauseVP);
        addViewPort(statusVP);
        addViewPort(miniMapVP);

        Cartographer.getActiveMap().addObserver(mapVP);
        Cartographer.getActiveMap().addObserver(miniMapVP);
    }

    public void initPauseMenu() {
        //Create Pause Menu 
        MenuOption[] pauseMenuOptions = {MenuOption.RESUME_GAME, MenuOption.SAVE_GAME, MenuOption.RETURN_TO_MAIN_MENU};
        pauseMenu = new Menu(pauseMenuOptions);
        pauseMenu.addObserver(pauseVP);
        pauseMenu.hide();
        GameWindow.getInstance().addKeyController(new MenuKeyController(pauseMenu, this));
    }

    public void initEntities() {
        Cartographer.getActiveMap().addEntity(Avatar.getInstance());

        for(int i = 0 ; i < 50; i++) {
            Entity e = new RandomAIEntity();
            Cartographer.getActiveMap().addEntity(e);
            int randomX = random.nextInt(50);
            int randomY = random.nextInt(50);
            e.getTransform().getPosition().x = randomX;
            e.getTransform().getPosition().y = randomY;
        }   
        
        Avatar.getInstance().addObserverOfStats(statusVP);
        GameWindow.getInstance().addKeyController(new AvatarKeyController(this));
    }

    public void showPauseMenu() {
        pauseMenu.show();
    }

    public void hidePauseMenu() {
        pauseMenu.hide();
    }
}
