import controller.DungeonConsoleController;
import controller.DungeonController;
import controller.GuiController;
import maze.Dungeon;
import radomnumbergenerator.RandomNumberGenerator;
import view.StartView;

import java.io.InputStreamReader;


/**
 * The driver class is to demonstrate the working of the dungeon construction and the player
 * travelling through it.
 */
public class Driver {

  /**
   * Main method.
   *
   * @param args command line parameters that are being accepted are rows, columns, toWrap,
   *             interconnectivity and treasure coverage.
   */

  public static void main(String[] args) {


    if (args.length > 0 && args.length < 7) {
      System.out.println("Invalid number of arguments.");
    }
    if (args.length == 0) {
      GuiController c = new GuiController(new StartView());
      c.playGame();
    }
    else {
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      int rows = Integer.parseInt(args[0]);
      int columns = Integer.parseInt(args[1]);
      boolean toWrap = Boolean.parseBoolean(args[2]);
      int interconnectivity = Integer.parseInt(args[3]);
      double treasureCoverage = Double.parseDouble(args[4]);
      int numberOfMonsters = Integer.parseInt(args[5]);
      int noOfPits = Integer.parseInt(args[6]);
      DungeonController c = new DungeonConsoleController(input, output,
            new Dungeon(rows,columns,toWrap,interconnectivity,treasureCoverage,numberOfMonsters,
                    noOfPits, new RandomNumberGenerator()));
      c.playGame();
    }






  }


}
