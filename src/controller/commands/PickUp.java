package controller.commands;

import controller.CommandController;
import maze.Maze;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class to implement the PICKUP command functionality. It allows the player to
 * collect treasure or arrows when the player provides a valid input.
 */
public class PickUp implements CommandController {

  private final Maze dungeon;
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs an object of command PICKUP.
   *
   * @param in  input stream object.
   * @param out output stream object.
   * @param d   dungeon model.
   */
  public PickUp(Scanner in, Appendable out, Maze d) {
    this.dungeon = d;
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = in;
  }

  @Override
  public void execute() {
    if (dungeon == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    try {

      out.append("What do you want to pickup? Treasure(T), or Arrows(A)? ");
      String item = (scan).next();
      if ((item.equals("T") || item.equals("t"))) {
        boolean treasure = dungeon.pickup(true);
        if (!treasure) {
          out.append("There is no Treasure, invalid move");
        }
      } else if (item.equals("A") || item.equals("a")) {
        boolean arrow = dungeon.pickup(false);
        if (!arrow) {
          out.append("There is no Arrow, invalid move");
        }
      } else {
        out.append("Please select either treasure or arrow to pickup");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
