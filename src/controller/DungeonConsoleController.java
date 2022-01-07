package controller;

import controller.commands.Move;
import controller.commands.PickUp;
import controller.commands.Shoot;
import maze.Location;
import maze.Maze;
import maze.Smell;

import java.io.IOException;
import java.util.Scanner;


/**
 * The DungeonConsoleController is the text based controller of this project. It takes the model and
 * user inputs from the driver and passes them to the model. The model then computes and returns the
 * values which are then displayed on the console. The text based game is being implemented
 * using the controller.
 */
public class DungeonConsoleController implements DungeonController {

  private final Maze dungeon;
  private final Appendable out;
  private final Readable scan;

  /**
   * Constructor for the controller object.
   *
   * @param in      InputStream object.
   * @param out     OutputStream object.
   * @param dungeon Dungeon model.
   */
  public DungeonConsoleController(Readable in, Appendable out, Maze dungeon) {
    this.dungeon = dungeon;
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = in;


  }


  /**
   * The function play game which starts the game and calls the MOVE, PICKUP and SHOOT functions
   * in the model.
   */
  public void playGame() {
    if (dungeon == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    CommandController cmd = null;
    Scanner input = new Scanner(this.scan);
    try {
      out.append(String.format("Type show for displaying the dungeon again"));

      while (input.hasNext() && !dungeon.isGameOver()) {
        String in = input.next();
        switch (in) {
          case "q":
          case "quit":
            return;
          case "show":
            displayDungeon(dungeon, false);
            out.append("\nWhat would you like to do? Move (M), Pickup (P), or Shoot(S)?");
            break;
          case "M":
            cmd = new Move(input, out, dungeon);
            cmd.execute();
            if (dungeon.isGameOver()) {
              break;
            }
            displayDungeon(dungeon, false);
            out.append("\nWhat would you like to do? Move (M), Pickup (P), or Shoot(S)?");
            break;
          case "P":
            cmd = new PickUp(input, out, dungeon);
            cmd.execute();
            displayDungeon(dungeon, false);
            out.append("\nWhat would you like to do? Move (M), Pickup (P), or Shoot(S)?");
            break;
          case "S":
            cmd = new Shoot(input, out, dungeon);
            cmd.execute();
            displayDungeon(dungeon, false);
            out.append("\nWhat would you like to do? Move (M), Pickup (P), or Shoot(S)?");
            break;
          default:
            out.append("\nEnter a command!!\n");
            break;

        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }


  void displayDungeon(Maze dungeon, boolean displayOnlyDungeon) {
    try {

      out.append(String.format("\nTreasure that player has : "
              + dungeon.getPlayer().printDescription()));
      out.append(String.format("\nNumber of arrows that player has : "
              + dungeon.getPlayer().getArrowCountOfPlayer()));
      if (!displayOnlyDungeon) {
        Location currentLocation = dungeon.getPlayer().getCurrentLocation();
        if (currentLocation.getIsCave()) {
          out.append(String.format("\nYou are in a cave"));
        } else if (currentLocation.getIsCave()) {
          out.append(String.format("\nYou are in a tunnel"));
        }

        Smell smell = currentLocation.checkSmellStrength();
        if (smell.equals(Smell.STRONG)) {
          out.append(String.format("\nYou smell something terrible nearby"));
        } else if (smell.equals(Smell.WEAK)) {
          out.append(String.format("\nYou smell something pungent at a distance"));
        }

        out.append(currentLocation.describeLocation());

      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }
}

