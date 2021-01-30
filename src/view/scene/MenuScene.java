package view.scene;

import controller.MenuKeyController;
import model.menu.Menu;
import view.viewport.MainMenuViewPort;
import view.window.GameWindow;

public class MenuScene extends Scene {
    
    Menu mainMenu;
    MenuKeyController menuController;

    public MenuScene() {
        mainMenu = new Menu();
        MainMenuViewPort menuVP = new MainMenuViewPort();
        mainMenu.addObserver(menuVP);

        this.addViewPort(menuVP);


        //Add controllers to the window
        menuController = new MenuKeyController(mainMenu, this);

        GameWindow.getInstance().addKeyController(menuController);

    } 
}
