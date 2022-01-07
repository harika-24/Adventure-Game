package controller;

/**
 * An  interface for the DungeonConsoleController class.It is used to implement the controller
 * feature The text based game is being implemented  using the controller.
 */

public interface DungeonController {

  /**
   * The function play game which starts the game and calls the MOVE, PICKUP and SHOOT functions
   * in the model.
   */
  void playGame();
}


