package testpackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
 * The test class that which will be testing the functionalities of the wrapping dungeon.
 */
public class DungeonTest {
  private Dungeon wrappingDungeon;

  TestRandomNumberGenerator testRandomNumberGenerator;

  @Before
  public void setUp() {
    TestRandomNumberGenerator testRandomNumberGenerator = new TestRandomNumberGenerator();
    wrappingDungeon = new Dungeon(6, 7, true, 0,
            0.2, 5, 3,new TestRandomNumberGenerator());

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
  public void testConnectivityForWrappingDungeon() {
    Set<Location> visited = getLocations(wrappingDungeon);
    assertEquals(visited.size(), wrappingDungeon.getRows() * wrappingDungeon.getCols());

  }

  @Test
  public void testInterconnectivity() {
    int edges = wrappingDungeon.getNoOfEdges();
    int interconnectivity = 3;
    testRandomNumberGenerator.resetStaticVariablesForTesting();
    Dungeon d = new Dungeon(6, 7, true, interconnectivity, 0.2,
           5, 3,new RandomNumberGenerator());
    assertEquals(edges + interconnectivity, d.getNoOfEdges());
  }


  @Test
  public void testGetRows() {
    assertEquals(6, wrappingDungeon.getRows());
  }

  @Test
  public void testGetColumns() {
    assertEquals(7, wrappingDungeon.getCols());
  }

  @Test
  public void testNoOfEdges() {
    assertEquals(41, wrappingDungeon.getNoOfEdges());
  }

  @Test
  public void testGetTreasureCoverage() {
    assertEquals(0.2f, wrappingDungeon.getTreasureCoverage(), 0.0001);
  }

  @Test
  public void testIfDungeonIsWrapping() {
    int counter = 0;
    for (int rowPos = 0; rowPos < wrappingDungeon.getRows(); rowPos++) {
      for (int colPos = 0; colPos < wrappingDungeon.getCols(); colPos++) {
        if (rowPos == 0 && wrappingDungeon.getLocations()[rowPos][colPos]
                .getDirectionOfLocation(Direction.NORTH) != null) {
          counter++;
        }
        if (colPos == 0 && wrappingDungeon.getLocations()[rowPos][colPos]
                .getDirectionOfLocation(Direction.EAST) != null) {
          counter++;
        }
      }
    }
    assertTrue(counter > 0);
  }

  @Test
  public void testStartLocation() {
    assertEquals(wrappingDungeon.getLocations()[0][1],
            wrappingDungeon.getPlayer().getCurrentLocation());
  }






  @Test
  public void testTreasureCoverage() {
    double t = wrappingDungeon.getTreasureCoverage();
    double noOfCaves = 0;
    for (int rowPos = 0; rowPos < wrappingDungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < wrappingDungeon.getLocations()[rowPos].length; colPos++) {
        if (wrappingDungeon.getLocations()[rowPos][colPos].getIsCave()) {
          noOfCaves++;
        }
      }
    }
    int totalTreasure = Math.toIntExact(Math.round(t * noOfCaves));
    int countTreasure = 0;
    for (int rowPos = 0; rowPos < wrappingDungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < wrappingDungeon.getLocations()[rowPos].length; colPos++) {
        if (wrappingDungeon.getLocations()[rowPos][colPos].getTreasureAtLocation().size() != 0) {
          countTreasure++;
        }
      }
    }
    assertEquals(totalTreasure, countTreasure);
  }

  @Test
  public void testPickUpTreasure() {
    Player player = wrappingDungeon.getPlayer();
    List<Treasure> testTreasure = new ArrayList<Treasure>(
            player.getStartLocation().getTreasureAtLocation());
    player.collectTreasure();
    assertEquals(player.getStartLocation().getTreasureAtLocation().size(), 0);
    assertEquals(testTreasure, player.getTreasureCollected());

  }



  @Test
  public void testPossibleMoves() {
    Player player = wrappingDungeon.getPlayer();
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