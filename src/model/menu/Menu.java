package model.menu;

import java.util.Observer;
import java.util.Observable;

/**
 * Maintains the states for a menu and allows navigation among the menu by
 * keeping track of the currently selected option.
 *
 * @author ChrisMoscoso
 */
public class Menu extends Observable {

    private final MenuOption[] options;
    private int currentOptionIndex = 0;
    private Boolean isVisible = true;

    public Menu() {
        MenuOption[] defaultOptions = {MenuOption.NEW_GAME, MenuOption.EXIT};
        this.options = defaultOptions;
    }

    /**
     * Initialize the menu with the options of the menu.
     *
     * @param options these are the options available in the menu
     */
    public Menu(MenuOption[] options) {
        this.options = options;
    }

    /**
     * Returns the menu options in string format.
     *
     * @return an array of strings that represent the options of the menu
     */
    public String[] getOptions() {
        String[] optionStrings = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            optionStrings[i] = options[i].toString().replace("_", " ");
        }
        return optionStrings;
    }

    /**
     *
     * @param selection
     */
    public void setCurrentSelection(int selection) {
        if (currentOptionIndex < 0) {
            currentOptionIndex = options.length - 1;
        } else if (currentOptionIndex >= options.length) {
            currentOptionIndex = 0;
        } else {
            currentOptionIndex = selection;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Go to the next option in menu.
     */
    public void next() {
        setCurrentSelection(++currentOptionIndex);
    }

    /**
     * Go to the previous option in the menu.
     */
    public void prev() {
        setCurrentSelection(--currentOptionIndex);
    }

    /**
     * Gets the current selected option as a string
     *
     * @return the currently selected MenuOption
     */
    public MenuOption getCurrentSelection() {
        return options[currentOptionIndex];
    }

    /**
     * Gets the current selection option as an index.
     *
     * @return the index of the currently selected option.
     */
    public int getCurrentSelectionIndex() {
        return currentOptionIndex;
    }

    @Override ////Same as super but we want to update immediately when observer gets added
    public void addObserver(Observer o){
        super.addObserver(o);
        setChanged();
        notifyObservers();
    }
    
    /**
     * Sets the menu to be visible. This is the default state of the menu.
     */
    public void show(){
        isVisible = true;
        setCurrentSelection(0);
        setChanged();
        notifyObservers();
    }
    
    /**
     * Sets the menu to be not visible.
     */
    public void hide(){
        isVisible = false;
        setChanged();
        notifyObservers();
    }

    /**
     * Checks if menu is visible.
     * @return if the menu is visible
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    /**
     * The list of possible menu options enumerated.
     */
    public enum MenuOption {

        /**
         * Starts a new game.
         */
        NEW_GAME,
        /**
         * Switch to the load menu.
         */
        SWITCH_TO_LOAD_MENU,
        /**
         * Saves the game to a file.
         */
         SAVE_GAME,
        /**
         * Exits the game.
         */
        EXIT,
        /**
         * Resumes the paused game.
         */
        RESUME_GAME,
        /**
         * Starts the game by loading a save file.
         */
        OPEN_SAVE_FILE,
        /**
         * Returns the game to the main menu.
         */
        RETURN_TO_MAIN_MENU
    }

}
