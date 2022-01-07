package testpackage;

import java.util.HashMap;
import java.util.Map;

import maze.Direction;
import maze.Dungeon;
import maze.Location;
import maze.Maze;
import maze.Player;
import maze.ReadOnlyDungeon;
import radomnumbergenerator.RandomNumberGenerator;


/**
 * Mock class to test the model for the inputs.
 */
public class MockModel implements Maze, ReadOnlyDungeon {

  private StringBuilder log;


  /**
   * Constructs the mock model object.
   *
   * @param log   string input.
   * @param check error check.
   */
  public MockModel(StringBuilder log, String check) {
    this.log = log;

  }


  @Override
  public Player getPlayer() {

    return new Player(1, 1, 5, 5,
            new Dungeon(6, 6, false, 0, 0.3,
                    4, 3, new RandomNumberGenerator()));
  }

  @Override
  public boolean movePlayer(Direction direction) {
    log.append("\nThe player is moving in " + direction + " direction.");
    return false;
  }

  @Override
  public String[][] printMap(Player player) {
    String[][] output = new String[getRows() * 3][getCols() * 3];
    for (int i = 0; i < getRows() * 3; i++) {
      for (int j = 0; j < getCols() * 3; j++) {
        output[i][j] = "*";
      }
    }
    return output;
  }

  @Override
  public int getRows() {
    return 6;
  }

  @Override
  public int getCols() {
    return 8;
  }

  @Override
  public Location[][] getLocations() {
    return new Location[0][];
  }

  @Override
  public Map<String, Boolean> shoot(Direction direction, int distance) {
    log.append("\nPlayer is shooting in " + direction + " direction and from  "
            + distance + " caves");
    Map<String, Boolean> returnVal = new HashMap<>();
    returnVal.put("Monster Hit", true);
    returnVal.put("Monster Dead", true);
    return returnVal;

  }

  @Override
  public boolean pickup(boolean isTreasure) {
    if (isTreasure) {
      log.append("\nThe player is picking up treasure.");
    }
    else {
      log.append("\nThe player is picking up arrow.");
    }
    return false;
  }

  @Override
  public void allocateMonsters(int numberOfMonsters) {
    return;
  }

  @Override
  public int getNumberOfMonsters() {
    return 3;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public void setGameOver(boolean gameOver) {
    return;
  }

  @Override
  public double getTreasureCoverage() {
    return 0;
  }

  @Override
  public int getNoOfPits() {
    return 1;
  }

  @Override
  public int getNoOfEdges() {
    return 1;
  }


}
