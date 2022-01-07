package view;

import java.awt.event.KeyListener;

import controller.GuiController;

/**
 * The interface is implemented by class MenuView create the view which would display the dungeon
 * and the panel with the location and the player information.
 */
public interface IView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(GuiController listener);

  /**
   * Used to refresh the view.
   */
  void refresh();

  /**
   * Set up the controller to handle key events in this view.
   * @param listener object of the KeyListener interface.
   */
  void addKeysListener(KeyListener listener);

  /**
   * Used to quit the view and close them.
   */
  void destroyView();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible(boolean visible);
}
