package rogue.view.menu;

import java.io.IOException;

import javafx.scene.input.KeyEvent;
import rogue.controller.GameController;

/**
 * An interface for controlling the game's Menu.
 *
 */
public interface MenuView {

    /**
     * Pass the MenuController to the MenuView.
     * @param controller for the MenuView
     */
    void init(GameController controller);

    /**
     * Method that let's the user enter his name.
     * @param event that triggered the method.
     * @throws IOException
     */
    void onNameEnter(KeyEvent event) throws IOException;

    /**
     * Get the name given by the user.
     * @return the name entered by the user.
     */
    String getPlayerName();
}
