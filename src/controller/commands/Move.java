package controller.commands;

import controller.CommandController;
import maze.Direction;
import maze.Maze;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class to implement the MOVE command functionality. It allows the player to move through the
 * dungeon when the player provides a valid direction.
 */
public class Move implements CommandController {

  private final Maze dungeon;
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs an object of command MOVE.
   *
   * @param in  input stream object.
   * @param out output stream object.
   * @param d   dungeon model.
   */
  public Move(Scanner in, Appendable out, Maze d) {
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
      try {
        out.append("Enter a possible direction to move :");
        String direction = (scan).next();
        if (dungeon.getPlayer().getCurrentLocation().getDirectionBooleanMap()
                .get(Direction.valueOf(direction))) {
          if (!dungeon.movePlayer(Direction.valueOf(direction))) {
            out.append("\n Game Over!! Please restart. 👾 \n");
            dungeon.setGameOver(true);
            return;
          }

          if (dungeon.getPlayer().getCurrentLocation() == dungeon.getPlayer().getFinalLocation()) {
            out.append("You reached the end. Hurrayyyy!!");
            dungeon.setGameOver(true);
          }
        } else {
          out.append("Invalid direction, cannot move this way");
        }

      } catch (IllegalArgumentException e) {
        out.append("Please Enter one of these possible values : NORTH, SOUTH, EAST, WEST");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }
}
