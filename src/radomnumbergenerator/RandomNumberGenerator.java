package radomnumbergenerator;

import maze.Edge;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * The class that implements the RandomNumber Interface.
 */
public class RandomNumberGenerator implements RandomGenerator {

  @Override
  public int randomValue(int bound) {
    Random random = new Random();
    return random.nextInt(bound);
  }

  @Override
  public Map<String, Integer> randomTreasurePlacement(int bound1, int bound2) {
    return randomMonsterPlacement(bound1, bound2);
  }

  @Override
  public Map<String, Integer> randomArrowPlacement(int bound1, int bound2) {
    return randomMonsterPlacement(bound1, bound2);
  }

  @Override
  public Map<String, Integer> randomPitPlacement(int bound1, int bound2) {
    return randomMonsterPlacement(bound1, bound2);
  }


  @Override
  public Map<String, Integer> randomMonsterPlacement(int bound1, int bound2) {
    Random random = new Random();
    Map<String, Integer> randomMonsterPlacementMap = new HashMap<>();
    randomMonsterPlacementMap.put("x",random.nextInt(bound1));
    randomMonsterPlacementMap.put("y",random.nextInt(bound2));
    return randomMonsterPlacementMap;
  }

  @Override
  public int randomRowBoundedValues(int bound, boolean isUpperBound) {
    Random random = new Random();
    return random.nextInt(bound);
  }

  @Override
  public int randomColBoundedValues(int bound, boolean isUpperBound) {
    Random random = new Random();
    return random.nextInt(bound);
  }

  @Override
  public List<Edge> randomizeEdges(List<Edge> edgeList) {
    Collections.shuffle(edgeList);
    return edgeList;
  }

}
