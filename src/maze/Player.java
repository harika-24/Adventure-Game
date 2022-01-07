package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The class player implements the interface Character.The player can move across the dungeon and
 * collect treasure. The player can move through the caves and tunnels and collect the treasure that
 * is present in the cave.
 */
public class Player implements Character {
  private Location currentLocation;
  private Location endLocation;
  private final Location startLocation;
  private final int startRow;
  private final int startCol;
  private int arrowCount;
  private int minimumMoves;
  private boolean isAlive;


  private List<Treasure> treasureCollected = new ArrayList<>();

  /**
   * The constructor that constructs an object of player.
   *
   * @param startRow the row of the starting position.
   * @param startCol the column of the starting position.
   * @param endRow   the row of the final position.
   * @param endCol   the column of the final position.
   * @param dungeon  The object of the dungeon class.
   */
  public Player(int startRow, int startCol, int endRow, int endCol, Dungeon dungeon) {

    validatePositionBounds(startRow, startCol, dungeon);
    validatePositionBounds(endRow, endCol, dungeon);
    this.startRow = startRow;
    this.startCol = startCol;
    this.arrowCount = 3;
    this.isAlive = true;
    currentLocation = dungeon.getLocations()[startRow][startCol];
    endLocation = dungeon.getLocations()[endRow][endCol];

    this.startLocation = currentLocation;

    currentLocation.setStartLocation(true);
    endLocation.setEndLocation(true);
    currentLocation.setHasPlayer(true);

  }

  /**
   * Copy constructor for the player class.
   *
   * @param player player object.
   */
  public Player(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cant be null");
    }
    currentLocation = player.currentLocation;
    endLocation = player.endLocation;
    startLocation = player.startLocation;
    startRow = player.startRow;
    startCol = player.startCol;
    arrowCount = player.arrowCount;
    minimumMoves = player.minimumMoves;
    isAlive = player.isAlive;
    treasureCollected = player.getTreasureCollected();
  }


  /**
   * The method that checks of the entered start and final position are valid.
   *
   * @param row     coordinate of the row.
   * @param col     coordinate of the column.
   * @param dungeon dungeon object.
   */
  private void validatePositionBounds(int row, int col, Dungeon dungeon) {
    if (row >= dungeon.getRows() || col >= dungeon.getCols() || row < 0 || col < 0) {
      throw new IllegalArgumentException("coordinate out of bounds");
    }
  }


  @Override
  public List<Location> searchForOptimalPaths() {

    Set visited = new HashSet<>();
    Queue<Location> q1 = new LinkedList<>();


    q1.add(currentLocation);
    q1.add(null);

    visited.add(currentLocation);
    Location location;
    int pathLength = 0;

    Location firstLocation = q1.peek();
    List<Location> pathList = new ArrayList<>();
    pathList.add(firstLocation);
    firstLocation.setLocationPath(pathList);

    while (!q1.isEmpty()) {
      location = q1.poll();
      currentLocation = location;

      if (location == null) {
        pathLength++;
        q1.add(null);
        if (q1.peek() == null) {
          break;
        } else {
          continue;
        }
      }

      if (location == endLocation) {
        break;
      }

      Location directionNorth = location.getDirectionOfLocation(Direction.NORTH);
      Location directionSouth = location.getDirectionOfLocation(Direction.SOUTH);
      Location directionEast = location.getDirectionOfLocation(Direction.EAST);
      Location directionWest = location.getDirectionOfLocation(Direction.WEST);

      addPath(location, visited, q1, directionNorth);
      addPath(location, visited, q1, directionSouth);
      addPath(location, visited, q1, directionEast);
      addPath(location, visited, q1, directionWest);

    }

    minimumMoves = pathLength;

    if (this.minimumMoves < 5) {
      throw new IllegalArgumentException("Please change start and end points, minimum number"
              + "of moves to end location is less than 5");
    }
    return endLocation.getLocationPath();
  }

  /**
   * Function that is getting the optimal location path and assigning it to the node.
   *
   * @param currentLocation the location node that is calling the function.
   * @param visited         set of all visited nodes.
   * @param q               queue which maintains the nodes.
   * @param nextLocation    the next location node.
   */
  private void addPath(Location currentLocation, Set visited, Queue<Location> q,
                       Location nextLocation) {
    if (nextLocation != null && !visited.contains(nextLocation)) {
      q.add(nextLocation);
      visited.add(nextLocation);

      List<Location> locationPath = new ArrayList<>(currentLocation.getLocationPath());
      locationPath.add(nextLocation);
      nextLocation.setLocationPath(locationPath);
    }

  }

  /**
   * Function that allows player to collect the treasure.
   */
  @Override
  public void collectTreasure() {
    treasureCollected.addAll(currentLocation.getTreasureAtLocation());
    currentLocation.removeTreasure();
  }

  /**
   * Function that allows player to collect the arrows..
   */
  @Override
  public void collectArrow() {
    arrowCount += currentLocation.getArrowCount();
    currentLocation.removeArrows();

  }

  /**
   * Method that returns the description of the treasure that the player has collected till now.
   *
   * @return hash map of treasure type and values.
   */
  @Override
  public Map<Treasure, Integer> printDescription() {
    treasureCollected = treasureCollected.stream()
            .filter(Objects::nonNull).collect(Collectors.toList());
    Map<Treasure, Integer> treasureIntegerMap = new HashMap();
    treasureCollected.forEach(treasure -> {
      treasureIntegerMap.putIfAbsent(treasure, 0);
      treasureIntegerMap.put(treasure, treasureIntegerMap.get(treasure) + 1);
    });
    return treasureIntegerMap;
  }

  /**
   * Returns the current location of the player.
   *
   * @return current location.
   */
  @Override
  public Location getCurrentLocation() {
    return this.currentLocation;
  }

  /**
   * Updates the current location of the player.
   *
   * @param location location node.
   */
  @Override
  public void setCurrentLocation(Location location) {
    this.currentLocation = location;
  }

  /**
   * Returns the start location of player.
   *
   * @return location node.
   */
  @Override
  public Location getStartLocation() {
    return this.startLocation;
  }

  /**
   * Returns the start location of player.
   *
   * @return location node.
   */
  @Override
  public Location getFinalLocation() {
    return this.endLocation;
  }

  /**
   * Updates the final location of the player.
   *
   * @param location location node.
   */
  @Override
  public void setFinalLocation(Location location) {
    this.endLocation = location;
  }


  /**
   * Returns the list of treasure collected by the player.
   *
   * @return the list of treasure.
   */
  @Override
  public List<Treasure> getTreasureCollected() {
    return new ArrayList<>(this.treasureCollected);
  }


  public void setTreasureCollected(List<Treasure> list) {
    this.treasureCollected = list;
  }

  /**
   * Returns the arrow count that the player has.
   *
   * @return arrow count.
   */
  @Override
  public int getArrowCountOfPlayer() {
    return arrowCount;
  }

  /**
   * Updates the arrow count that the player has.
   *
   * @param arrowCount arrow count.
   */
  @Override
  public void setArrowCount(int arrowCount) {
    this.arrowCount = arrowCount;
  }

  /**
   * Returns boolean value based on whether the player is alive or not.
   *
   * @return boolean value.
   */
  @Override
  public boolean isAlive() {
    return this.isAlive;
  }

  /**
   * Sets the boolean value for the player's life.
   *
   * @param isAlive boolean value.
   */
  @Override
  public void setIsAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  /**
   * Returns the start row of the starting location.
   *
   * @return start row.
   */
  @Override
  public int getStartRow() {
    return startRow;
  }

  /**
   * Returns the start column of the starting location.
   *
   * @return start column.
   */
  @Override
  public int getStartCol() {
    return startCol;
  }
}
