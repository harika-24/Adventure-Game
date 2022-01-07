package controller.commands;

import controller.CommandController;
import maze.Direction;
import maze.Maze;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


/**
 * Class to implement the SHOOT command functionality. It allows the player to
 * shoot the Otyughs when the distance and direction are provided.
 */
public class Shoot implements CommandController {

  private final Maze dungeon;
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs an object of command SHOOT.
   * @param in input stream object.
   * @param out output stream object.
   * @param d dungeon model.
   */
  public Shoot(Scanner in, Appendable out, Maze d) {
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
      out.append("Specify the direction and the distance to be shot : (Eg. NORTH)");
      String direction = (scan).next();
      Direction arrowDirection = null;
      Integer arrowDistance = null;
      try {
        arrowDirection = Direction.valueOf(direction);
      } catch (IllegalArgumentException e) {
        out.append("Invalid direction, cannot move this way");
        return;
      }
      out.append("Specify the distance (number of caves) the arrow travels : ");
      String distance = scan.next();
      try {
        arrowDistance = Integer.valueOf(distance);
      } catch (NumberFormatException e) {
        out.append("Please enter an integer value for distance : ");
      }
      if (dungeon.getPlayer().getArrowCountOfPlayer() == 0) {
        out.append("\nThere are not enough arrows available!\n");
        return;
      }
      Map<String, Boolean> monsters = dungeon.shoot(arrowDirection, arrowDistance);

      if (monsters.get("Monster Hit")) {
        out.append("You hear a great howl in the distance!\n");
      }
      else if (monsters.get("Monster Dead")) {
        out.append("The monster wails in pain and takes last breath!!\n");
      }
      else if (!monsters.get("Monster Hit") && !monsters.get("Monster Dead")) {
        out.append("You shoot the arrow into the darkness!! You lost one arrow!");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}
