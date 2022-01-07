package radomnumbergenerator;

import maze.Edge;

import java.util.List;
import java.util.Map;

/**
 * An interface to implement the function that would generate random values.
 */
public interface RandomGenerator {
  /**
   * Generates random value given the bound.
   *
   * @param bound integer value.
   * @return integer.
   */
  int randomValue(int bound);

  /**
   * Random generator to place the monsters in the dungeon.
   * @param bound1 number of rows.
   * @param bound2 number of columns.
   * @return random values.
   */
  Map<String, Integer> randomMonsterPlacement(int bound1, int bound2);

  /**
   * Random generator to place the treasure in the dungeon.
   * @param bound1 number of rows.
   * @param bound2 number of columns.
   * @return random values.
   */
  Map<String, Integer> randomTreasurePlacement(int bound1, int bound2);

  /**
   * Random generator to place the arrow in the dungeon.
   * @param bound1 number of rows.
   * @param bound2 number of columns.
   * @return random values.
   */
  Map<String, Integer> randomArrowPlacement(int bound1, int bound2);

  /**
   * Random generator to place the pits in the dungeon.
   * @param bound1 number of rows.
   * @param bound2 number of columns.
   * @return random values.
   */
  Map<String, Integer> randomPitPlacement(int bound1, int bound2);

  /**
   * Returns the random values for the row positions.
   * @param bound number of rows.
   * @param isUpperBound boolean for whether it is row or not.
   * @return random values.
   */
  int randomRowBoundedValues(int bound, boolean isUpperBound);

  /**
   * Returns the random values for the column positions.
   * @param bound number of columns.
   * @param isUpperBound boolean for whether it is column or not.
   * @return random values.
   */
  int randomColBoundedValues(int bound, boolean isUpperBound);

  /**
   * Returns the list of edges.
   * @param edges list of edges.
   * @return random edges.
   */
  List<Edge> randomizeEdges(List<Edge> edges);


}
