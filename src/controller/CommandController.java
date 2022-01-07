package controller;

/**
 * The controller being implemented based on the command based pattern. All the commands that are
 * given by the user i.e MOVE,PICKUP and SHOOT will have the same go() which will implement the
 * functionality.
 */
public interface CommandController {

  /**
   * The function go which will be overridden by the classes implementing the interface to implement
   * individual functionalities.
   */
  void execute();
}
