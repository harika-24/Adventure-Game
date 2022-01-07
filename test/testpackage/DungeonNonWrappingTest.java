package testpackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import maze.Direction;
import maze.Dungeon;
import maze.Location;
import maze.Player;
import maze.Treasure;
import radomnumbergenerator.RandomNumberGenerator;
import radomnumbergenerator.TestRandomNumberGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * The test class that which will be testing the functionalities of the non-wrapping dungeon.
 */
public class DungeonNonWrappingTest {

  private Dungeon nonWrappingDungeon;
  private  TestRandomNumberGenerator testRandomNumberGenerator;

  @Before
  public void setUp() {
    TestRandomNumberGenerator testRandomNumberGenerator = new TestRandomNumberGenerator();
    nonWrappingDungeon = new Dungeon(6, 8, false,0, 0.3,
            3, 3, testRandomNumberGenerator );
    this.testRandomNumberGenerator = testRandomNumberGenerator;

  }

  @After
  public void afterTest() {
    testRandomNumberGenerator.resetStaticVariablesForTesting();
  }

  private Set<Location> getLocations(Dungeon dungeon) {
    Location firstLocation = dungeon.getLocations()[0][0];
    Queue<Location> q = new LinkedList<Location>();
    Set<Location> visited = new HashSet<>();
    q.add(firstLocation);
    visited.add(firstLocation);


    while (!q.isEmpty()) {
      Location location = q.poll();

      Location directionNorth = location.getDirectionOfLocation(Direction.NORTH);
      Location directionSouth = location.getDirectionOfLocation(Direction.SOUTH);
      Location directionEast = location.getDirectionOfLocation(Direction.EAST);
      Location directionWest = location.getDirectionOfLocation(Direction.WEST);

      if (directionNorth != null && !visited.contains(directionNorth)) {
        q.add(directionNorth);
        visited.add(directionNorth);
      }

      if (directionSouth != null && !visited.contains(directionSouth)) {
        q.add(directionSouth);
        visited.add(directionSouth);
      }

      if (directionEast != null && !visited.contains(directionEast)) {
        q.add(directionEast);
        visited.add(directionEast);
      }

      if (directionWest != null && !visited.contains(directionWest)) {
        q.add(directionWest);
        visited.add(directionWest);
      }

    }
    return visited;
  }

  @Test
  public void testGetRows() {
    assertEquals(6, nonWrappingDungeon.getRows());
  }

  @Test
  public void testGetColumns() {
    assertEquals(8, nonWrappingDungeon.getCols());
  }

  @Test
  public void testNoOfEdges() {
    assertEquals(47, nonWrappingDungeon.getNoOfEdges());
  }

  @Test
  public void testGetTreasureCoverage() {
    assertEquals(0.3f, nonWrappingDungeon.getTreasureCoverage(), 0.0001);
  }

  @Test
  public void testIfDungeonIsWrapping() {
    int counter = 0;
    for (int rowPos = 0; rowPos < nonWrappingDungeon.getRows(); rowPos++) {
      for (int colPos = 0; colPos < nonWrappingDungeon.getCols(); colPos++) {
        if (rowPos == 0
                && nonWrappingDungeon.getLocations()[rowPos][colPos]
                .getDirectionOfLocation(Direction.NORTH) != null) {
          counter++;
        }
        if (colPos == 0 && nonWrappingDungeon.getLocations()[rowPos][colPos]
                .getDirectionOfLocation(Direction.EAST) != null) {
          counter++;
        }
      }
    }
    assertTrue(counter > 0);
  }


  @Test
  public void testStartLocation() {
    assertEquals(nonWrappingDungeon.getLocations()[0][1],
            nonWrappingDungeon.getPlayer().getCurrentLocation());
  }

  @Test
  public void testFinalLocation() {
    List<Location> finalPathTakenByPlayer = nonWrappingDungeon.getPlayer().searchForOptimalPaths();
    finalPathTakenByPlayer.forEach(location -> {
      nonWrappingDungeon.getPlayer().setCurrentLocation(location);
    });

    assertEquals(nonWrappingDungeon.getPlayer().getCurrentLocation(),
            nonWrappingDungeon.getPlayer().getFinalLocation());
  }


  @Test
  public void testConnectivityForNonWrappingDungeon() {
    Set<Location> visited = getLocations(nonWrappingDungeon);
    assertEquals(visited.size(), nonWrappingDungeon.getRows() * nonWrappingDungeon.getCols());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMinimumPathFails() {
    Dungeon smallDungeon = new Dungeon(2, 3, false, 0,
            0.3,6, 4,new RandomNumberGenerator());
    smallDungeon.createPlayer();
    smallDungeon.getPlayer().searchForOptimalPaths();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructionWithNegativeRows() {
    Dungeon smallDungeon = new Dungeon(-2, 3, false, 0,
            0.3,5,3, new RandomNumberGenerator());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructionWithNegativeColumns() {
    Dungeon smallDungeon = new Dungeon(2, 0, false, 0,
            0.3, 4,2,new RandomNumberGenerator());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructionWithEmptyToWrap() {
    Dungeon smallDungeon = new Dungeon(2, 7, null, 0,
            0.3,7, 1,new RandomNumberGenerator());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructionWithTreasureCoverage() {
    Dungeon smallDungeon = new Dungeon(2, 7, false, 0,
            0.1, 4,2,new RandomNumberGenerator());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDungeonConstructionWithCoverage() {
    Dungeon smallDungeon = new Dungeon(2, 7, false, 0,
            2, 4,1,new RandomNumberGenerator());
  }


  @Test
  public void testTreasureCoverage() {
    double t = nonWrappingDungeon.getTreasureCoverage();
    double noOfCaves = 0;
    for (int rowPos = 0; rowPos < nonWrappingDungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < nonWrappingDungeon.getLocations()[rowPos].length; colPos++) {
        if (nonWrappingDungeon.getLocations()[rowPos][colPos].getIsCave()) {
          noOfCaves++;
        }
      }
    }
    int totalTreasure = Math.toIntExact(Math.round(t * noOfCaves));
    int countTreasure = 0;
    for (int rowPos = 0; rowPos < nonWrappingDungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < nonWrappingDungeon.getLocations()[rowPos].length; colPos++) {
        if (nonWrappingDungeon.getLocations()[rowPos][colPos].getTreasureAtLocation().size() != 0) {
          countTreasure++;
        }
      }
    }
    assertEquals(totalTreasure, countTreasure);
  }


  @Test
  public void testPickUpTreasure() {
    Player player = nonWrappingDungeon.getPlayer();
    List<Treasure> testTreasure = new ArrayList<Treasure>(
            player.getStartLocation().getTreasureAtLocation());
    player.collectTreasure();
    assertEquals(player.getStartLocation().getTreasureAtLocation().size(), 0);
    assertEquals(testTreasure, player.getTreasureCollected());

  }

  @Test
  public void testPrintDescription() {
    Map<Treasure, Integer> treasureMap = new HashMap();

    List<Location> finalPathTakenByPlayer = nonWrappingDungeon.getPlayer().searchForOptimalPaths();
    finalPathTakenByPlayer.forEach(location -> {
      location.getTreasureAtLocation().forEach(treasure -> {
        treasureMap.putIfAbsent(treasure, 0);
        treasureMap.put(treasure, treasureMap.get(treasure) + 1);
      });
    });

    finalPathTakenByPlayer.forEach(location -> {
      nonWrappingDungeon.getPlayer().setCurrentLocation(location);
      nonWrappingDungeon.getPlayer().collectTreasure();
    });

    assertEquals(treasureMap.toString(),
            nonWrappingDungeon.getPlayer().printDescription().toString());
  }

  @Test
  public void testPossibleMoves() {
    Player player = nonWrappingDungeon.getPlayer();
    boolean north = player.getCurrentLocation().getDirectionOfLocation(Direction.NORTH) != null;
    boolean south = player.getCurrentLocation().getDirectionOfLocation(Direction.SOUTH) != null;
    boolean east = player.getCurrentLocation().getDirectionOfLocation(Direction.EAST) != null;
    boolean west = player.getCurrentLocation().getDirectionOfLocation(Direction.WEST) != null;

    Map<Direction, Boolean> directionBooleanMap =
            player.getCurrentLocation().getDirectionBooleanMap();

    assertEquals(directionBooleanMap.get(Direction.NORTH), north);
    assertEquals(directionBooleanMap.get(Direction.SOUTH), south);
    assertEquals(directionBooleanMap.get(Direction.EAST), east);
    assertEquals(directionBooleanMap.get(Direction.WEST), west);
  }
}
