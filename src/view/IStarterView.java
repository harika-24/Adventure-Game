package view;

import controller.GuiController;

/**
 * The interface that is implemented by the class StartView that appears as soon as the driver is
 * run. Welcome screen and then when the player chooses the game settings a form is displayed which
 * takes in the dungeon inputs and returns the values to the GUI Controller.
 */
public interface IStarterView {
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
   * Used to quit the view and close them.
   */
  void destroyView();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible(boolean visible);
}
