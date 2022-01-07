package radomnumbergenerator;

import maze.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * The class that implements the RandomGenerator interface and is passed for testing purposes.
 */
public class TestRandomNumberGenerator implements RandomGenerator {


  private static int lowerBound = -1;
  private static Integer upperBound = Integer.MAX_VALUE;

  private static HashSet<Map<String, Integer>> hashSetMonsters = new HashSet<>();
  private static HashSet<Map<String, Integer>> hashSetTreasure = new HashSet<>();
  private static HashSet<Map<String, Integer>> hashSetArrow = new HashSet<>();
  private static HashSet<Map<String, Integer>> hashSetPits = new HashSet<>();


  @Override
  public int randomValue(int bound) {
    return bound - 1;
  }


  @Override
  public Map<String, Integer> randomMonsterPlacement(int bound1, int bound2) {
    Map<String, Integer> pair;
    for (int i = bound1 - 1; i >= 0; i--) {
      for (int j = bound2 - 1; j >= 0; j--) {
        pair = new HashMap<>();
        pair.put("x", i);
        pair.put("y", j);
        if (hashSetMonsters.contains(pair)) {
          continue;
        }
        hashSetMonsters.add(pair);

        return pair;
      }
    }
    pair = new HashMap<>();
    pair.put("x", bound1 - 1);
    pair.put("y", bound2 - 1);
    return pair;
  }

  @Override
  public int randomRowBoundedValues(int bound, boolean isUpper) {
    if (isUpper) {
      if (upperBound == Integer.MAX_VALUE) {
        upperBound = bound;
      }
      upperBound = upperBound - 1;
      return upperBound;
    } else {
      lowerBound = lowerBound + 1;
      return lowerBound;
    }
  }

  @Override
  public int randomColBoundedValues(int bound, boolean isUpper) {
    if (isUpper) {
      if (upperBound == Integer.MAX_VALUE) {
        upperBound = bound;
      }
      upperBound = upperBound - 1;
      return upperBound;
    } else {
      lowerBound = lowerBound + 1;
      return lowerBound;
    }
  }

  @Override
  public List<Edge> randomizeEdges(List<Edge> edgeList) {
    return edgeList;
  }


  @Override
  public Map<String, Integer> randomTreasurePlacement(int bound1, int bound2) {
    Map<String, Integer> pair;
    for (int i = 0; i < bound1; i++) {
      for (int j = 0; j < bound2; j++) {
        pair = new HashMap<>();
        pair.put("x", i);
        pair.put("y", j);
        if (hashSetTreasure.contains(pair)) {
          continue;
        }
        hashSetTreasure.add(pair);
        return pair;
      }
    }
    pair = new HashMap<>();
    pair.put("x", 0);
    pair.put("y", 0);
    return pair;
  }

  @Override
  public Map<String, Integer> randomArrowPlacement(int bound1, int bound2) {
    Map<String, Integer> pair;
    for (int i = 0; i < bound1; i++) {
      for (int j = 0; j < bound2; j++) {
        pair = new HashMap<>();
        pair.put("x", i);
        pair.put("y", j);
        if (hashSetArrow.contains(pair)) {
          continue;
        }
        hashSetArrow.add(pair);
        return pair;
      }
    }
    pair = new HashMap<>();
    pair.put("x", 0);
    pair.put("y", 0);
    return pair;
  }

  @Override
  public Map<String, Integer> randomPitPlacement(int bound1, int bound2) {
    Map<String, Integer> pair;
    for (int i = bound1 - 1; i >= 0; i--) {
      for (int j = bound2 - 1; j >= 0; j--) {
        pair = new HashMap<>();
        pair.put("x", i);
        pair.put("y", j);
        if (hashSetPits.contains(pair)) {
          continue;
        }
        hashSetPits.add(pair);
        return pair;
      }
    }
    pair = new HashMap<>();
    pair.put("x", 0);
    pair.put("y", 0);
    return pair;
  }


  /**
   * Resets the static variable values.
   */
  public void resetStaticVariablesForTesting() {
    hashSetMonsters.clear();
    hashSetTreasure.clear();
    hashSetArrow.clear();
    lowerBound = -1;
    upperBound = Integer.MAX_VALUE;
  }


}


