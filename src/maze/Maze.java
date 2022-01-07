package maze;


import java.util.Map;


/**
 * The interface to implement the dungeon. The dungeon that will be created can be wrapping or
 * non-wrapping. It will have edges assigned to it based on the interconnectivity.
 */

public interface Maze {


  /**
   * Returns the player object that has been created in the dungeon. The player will be picking up
   * treasure, shooting arrows and moving through the dungeon.
   *
   * @return player object.
   */
  public Player getPlayer();

  /**
   * Moves the player in the direction passed as the argument. Based on the direction passed, the
   * player will move through the dungeon.
   *
   * @param direction the direction in which the player should move.
   * @return boolean value depending on whether the player is able to escape or not when
   *         encounters with Monster.
   */
  public boolean movePlayer(Direction direction);


  /**
   * Prints the dungeon to the screen.
   *
   * @param player player object.
   * @return String.
   */
  public String[][] printMap(Player player);

  /**
   * Returns the number of rows in the dungeon.
   *
   * @return rows of the dungeon.
   */
  public int getRows();

  /**
   * Returns the number of columns in the dungeon.
   *
   * @return columns of the dungeon.
   */
  public int getCols();



  /**
   * Returns the locations of the dungeon.
   *
   * @return locations.
   */
  public Location[][] getLocations();


  /**
   * The shooting function to kill the Otyughs, the direction in which the arrow has to be fired
   * and the distance or the number of caves it has to travel are taken as the parameters.
   *
   * @param direction enum direction.
   * @param distance  number of caves as direction.
   * @return a pair of boolean values which consists whether the monster is hit or dead.
   */
  public Map<String, Boolean> shoot(Direction direction, int distance);

  /**
   * The player uses this function to collect treasure or arrows. It is used to collect both
   * depending on the parameters that is passed to it.
   *
   * @param isTreasure Boolean value, which when true means collect treasure else collect arrow.
   * @return boolean values of which item is being collected.
   */
  boolean pickup(boolean isTreasure);


  /**
   * Allocates the monsters in the caves and makes sure that there is a monster in the end location.
   *
   * @param numberOfMonsters total number of monsters that are passed as command line.
   */
  void allocateMonsters(int numberOfMonsters);


  /**
   * Returns the number of monsters present in the dungeon.
   *
   * @return number of monsters.
   */
  int getNumberOfMonsters();

  /**
   * Returns if the game is over.
   *
   * @return boolean value.
   */
  boolean isGameOver();

  /**
   * Sets the value for Game over.
   *
   * @param gameOver boolean value.
   */
  void setGameOver(boolean gameOver);

  /**
   * Returns the treasure coverage in the dungeon.
   *
   * @return treasure coverage.
   */
  double getTreasureCoverage();

  /**
   * Returns the number of pits in the dungeon.
   *
   * @return no of pits.
   */
  int getNoOfPits();

  /**
   * Returns the number of edges used in the dungeon creation.
   *
   * @return number of edges.
   */
  int getNoOfEdges();


}
