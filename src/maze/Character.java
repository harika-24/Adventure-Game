package maze;

import java.util.List;
import java.util.Map;

/**
 * The interface to implement the player. The player can move across the dungeon and collect
 * treasure. The player can move through the caves and tunnels and collect the treasure that is
 * present in the cave.
 */
public interface Character {

  /**
   * The function implements the BFS algorithm and find the optimal path for the player
   * to go through the maze.
   *
   * @return list of the locations in the optimal path.
   */
  List<Location> searchForOptimalPaths();

  /**
   * The function that collects treasure in the path when the player is moving across the dungeon.
   */
  void collectTreasure();

  /**
   * Prints the description of the player that includes a description of
   * what treasure the player has collected.
   *
   * @return the map with treasure and the amount of treasure collected.
   */
  Map<Treasure, Integer> printDescription();


  /**
   * While the player is travelling through the dungeon, the function returns the current location
   * of the player.
   *
   * @return location.
   */
  Location getCurrentLocation();

  /**
   * Updates the current location of the player.
   *
   * @param location location node.
   */
  public void setCurrentLocation(Location location);

  /**
   * Returns the start location of player.
   *
   * @return location node.
   */
  Location getStartLocation();

  /**
   * In order to get out of the dungeon the player has to reach the final location.
   *
   * @return final location.
   */
  Location getFinalLocation();

  /**
   * Function that allows player to collect the treasure.
   */
  void collectArrow();

  /**
   * Updates the final location of the player.
   *
   * @param location location node.
   */
  void setFinalLocation(Location location);

  /**
   * Returns the list of treasure collected by the player.
   *
   * @return the list of treasure.
   */
  List<Treasure> getTreasureCollected();

  /**
   * Returns the arrow count that the player has.
   *
   * @return arrow count.
   */
  int getArrowCountOfPlayer();

  /**
   * Updates the arrow count that the player has.
   * @param arrowCount arrow count.
   */
  void setArrowCount(int arrowCount);

  /**
   * Returns boolean value based on whether the player is alive or not.
   *
   * @return boolean value.
   */
  boolean isAlive();

  /**
   * Sets the boolean value for the player's life.
   *
   * @param isAlive boolean value.
   */
  void setIsAlive(boolean isAlive);

  /**
   * Returns the start row of the starting location.
   *
   * @return start row.
   */
  int getStartRow();

  /**
   * Returns the start column of the starting location.
   *
   * @return start column.
   */
  int getStartCol();
}
