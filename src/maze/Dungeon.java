package maze;

import radomnumbergenerator.RandomGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * The class that constructs the dungeon. The dungeon that will be created can be wrapping or
 * non-wrapping. It will have edges assigned to it based on the interconnectivity.
 */
public class Dungeon implements Maze, ReadOnlyDungeon {
  private int rows;
  private int cols;
  private final double treasureCoverage;
  private Player player;
  private int noOfEdges;
  private final int numberOfMonsters;
  private final int noOfPits;
  private boolean isGameOver;


  private Location[][] locations;

  private List<Edge> edgeList;
  private RandomGenerator random;

  /**
   * Constructs the object of dungeon.
   *
   * @param rows              number of rows for the dungeon.
   * @param cols              number of columns for the dungeon.
   * @param toWrap            boolean value for wrapping.
   * @param interconnectivity interconnectivity value.
   * @param treasureCoverage  the percentage of treasure to allocated in the caves.
   * @param randomGenerator   The random generator object.
   * @param noOfPits          number of pits present in the dungeon.
   * @throws IllegalArgumentException when the entered values are invalid.
   */
  public Dungeon(int rows, int cols, Boolean toWrap, int interconnectivity,
                 double treasureCoverage, int numberOfMonsters, int noOfPits,
                 RandomGenerator randomGenerator)
          throws IllegalArgumentException {
    if (rows <= 0 || cols <= 0 || toWrap == null || toWrap.equals(" ") || interconnectivity < 0
            || randomGenerator == null
            || noOfPits < 0 || noOfPits
            == rows * cols) {
      throw new IllegalArgumentException("The entered parameters are invalid");
    }
    if (treasureCoverage < 0.2 || treasureCoverage > 1) {
      throw new IllegalArgumentException(" Treasure has to be between 0.2 to 1");
    }
    this.rows = rows;
    this.cols = cols;
    this.treasureCoverage = treasureCoverage;
    this.noOfEdges = 0;
    this.random = randomGenerator;
    this.numberOfMonsters = numberOfMonsters;
    this.noOfPits = noOfPits;
    this.isGameOver = false;
    locations = new Location[rows][cols];
    edgeList = new ArrayList<>();
    List<Edge> extraEdges = new ArrayList<>();

    /*
     * 1 - 2 - 3
     * |   |   |
     * 4 - 5 - 6
     * |   |   |
     * 7 - 8 - 9
     */

    for (int rowPos = 0; rowPos < rows; rowPos++) {
      for (int colPos = 0; colPos < cols; colPos++) {
        locations[rowPos][colPos] = new Location(rowPos, colPos);
      }
    }
    wrap(rows, cols, toWrap);
    createEdges();
    edgeList = randomizeEdges(edgeList);
    for (Edge edge : edgeList) {
      if (!isInSameSet(edge)) {
        edge.setDirection(edge.getDirection());
        noOfEdges++;
      } else {
        extraEdges.add(edge);
      }
    }
    for (int i = 0; i < interconnectivity; i++) {
      Edge edge = extraEdges.get(i);
      edge.setDirection(edge.getDirection());
      noOfEdges++;
    }

    setCavesAndTunnels();
    assignPits();

    allocateItems(true);
    allocateItems(false);
    createPlayer();
    allocateMonsters(numberOfMonsters);

  }

  private void assignPits() {
    int noOfPitsToBeAllocated = getNoOfPits();
    if (noOfPitsToBeAllocated > 0) {
      int allocated = 0;
      while (allocated < noOfPitsToBeAllocated) {
        Map<String, Integer> placement = random.randomPitPlacement(this.rows, this.cols);
        int randomRow = placement.get("x");
        int randomCol = placement.get("y");
        if (!locations[randomRow][randomCol].isPit()) {
          locations[randomRow][randomCol].setPit(true);
          allocated++;
        }
      }
    }
  }


  @Override
  public boolean pickup(boolean isTreasure) {

    List<Treasure> treasureAtLocation = getPlayer().getCurrentLocation().getTreasureAtLocation();
    int arrowCount = getPlayer().getCurrentLocation().getArrowCount();

    if (isTreasure) {
      if (treasureAtLocation != null && treasureAtLocation.size() > 0) {
        getPlayer().collectTreasure();
        return true;
      } else {
        return false;
      }
    } else {
      if (arrowCount > 0) {
        getPlayer().collectArrow();
        return true;
      } else {
        return false;
      }
    }
  }


  @Override
  public Map<String, Boolean> shoot(Direction direction, int distance) {

    boolean monsterHit = false;
    boolean monsterDead = false;

    Map<String, Boolean> returnVal = new HashMap<>();

    int arrowCount = getPlayer().getArrowCountOfPlayer();

    if (arrowCount == 0) {
      returnVal.put("Monster Hit", monsterHit);
      returnVal.put("Monster Dead", monsterDead);
      return returnVal;
    }

    getPlayer().setArrowCount(arrowCount - 1);
    Location currentLocation = getPlayer().getCurrentLocation();

    Map<Direction, Direction> oppositeDirections = new HashMap<>();

    Direction north = Direction.NORTH;
    Direction south = Direction.SOUTH;
    Direction west = Direction.WEST;
    Direction east = Direction.EAST;

    oppositeDirections.put(north, south);
    oppositeDirections.put(south, north);
    oppositeDirections.put(west, east);
    oppositeDirections.put(east, west);

    while (true) {
      Location arrowLocation = currentLocation.getDirectionOfLocation(direction);

      if (arrowLocation == null) {
        if (currentLocation.getIsTunnel()) {

          if (currentLocation.getDirectionOfLocation(north) != null
                  && direction != oppositeDirections.get(north)) {
            direction = north;
            continue;
          }
          if (currentLocation.getDirectionOfLocation(south) != null
                  && direction != oppositeDirections.get(south)) {
            direction = south;
            continue;
          }
          if (currentLocation.getDirectionOfLocation(west) != null
                  && direction != oppositeDirections.get(west)) {
            direction = west;
            continue;
          }
          if (currentLocation.getDirectionOfLocation(east) != null
                  && direction != oppositeDirections.get(east)) {
            direction = east;
            continue;
          }
        }
        returnVal.put("Monster Hit", monsterHit);
        returnVal.put("Monster Dead", monsterDead);
        return returnVal;
      } else if (arrowLocation != null && arrowLocation.getIsTunnel()) {
        currentLocation = arrowLocation;
        continue;
      }

      if (arrowLocation.getIsCave()) {
        distance--;
      }

      Monster monster = arrowLocation.getMonster();
      if (monster != null && monster.getIsAlive()) {
        if (distance != 0) {
          returnVal.put("Monster Hit", monsterHit);
          returnVal.put("Monster Dead", monsterDead);
          return returnVal;
        }
        monster.decreaseLife();
        arrowLocation.setMonster(monster);
        monsterHit = true;
        if (!monster.getIsAlive()) {
          monsterDead = true;
        }
        returnVal.put("Monster Hit", monsterHit);
        returnVal.put("Monster Dead", monsterDead);
        return returnVal;
      }
      currentLocation = arrowLocation;
    }
  }


  @Override
  public void allocateMonsters(int numberOfMonsters) {
    if (numberOfMonsters == 0) {
      throw new IllegalArgumentException("Enter monsters greater than 1");
    }
    if (numberOfMonsters > getTotalNodes(getRows() * getCols(), 0)) {
      throw new IllegalArgumentException("Enter monsters less than the total number of caves.");
    }
    Location finalLocation = this.player.getFinalLocation();
    finalLocation.setMonster(new Monster());
    this.player.setFinalLocation(finalLocation);
    numberOfMonsters--;

    while (numberOfMonsters > 0) {
      Map<String, Integer> monster =
              random.randomMonsterPlacement(this.getRows(), this.getCols());
      Location location = getLocations()[monster.get("x")][monster.get("y")];
      if (location.getIsTunnel() || location.getRowPos() == getPlayer().getStartRow()
              && location.getColPos() == getPlayer().getStartCol()
              || location.getMonster() != null) {
        continue;
      }
      location.setMonster(new Monster());
      numberOfMonsters--;
    }
  }


  /**
   * Creates a player who will traverse the dungeon.
   */

  public void createPlayer() {
    int startRow;
    int startCol;
    int endRow;
    int endCol;

    while (true) {
      startRow = random.randomRowBoundedValues(this.getRows(), false);
      startCol = random.randomColBoundedValues(this.getCols(), false);

      if (getLocations()[startRow][startCol].getIsTunnel()
              || getLocations()[startRow][startCol].isPit()) {
        continue;
      }
      endRow = random.randomRowBoundedValues(this.getRows(), true);
      endCol = random.randomColBoundedValues(this.getCols(), true);

      if (startRow == endRow && startCol == endCol
              || getLocations()[endRow][endCol].getIsTunnel()) {
        continue;
      }
      this.player = new Player(startRow, startCol, endRow, endCol, this);
      break;
    }
  }

  @Override
  public boolean movePlayer(Direction direction) {
    if (direction != null) {
      if (this.player.getCurrentLocation().getDirectionOfLocation(direction) != null) {
        this.player.getCurrentLocation().setHasPlayer(false);
        this.player.setCurrentLocation(this.player.getCurrentLocation()
                .getDirectionOfLocation(direction));
        this.player.getCurrentLocation().setHasPlayer(true);
        if (this.player.getCurrentLocation().isPit()) {
          setGameOver(true);
          this.player.setIsAlive(false);
          return false;
        }

      }
    }
    double escapeChance = 100;
    if (this.player.getCurrentLocation().getMonster() != null
            && this.player.getCurrentLocation().getMonster().getLives() == 2) {
      escapeChance = 0;
    } else if (this.player.getCurrentLocation().getMonster() != null
            && this.player.getCurrentLocation().getMonster().getLives() == 1) {
      escapeChance = random.randomValue(100);
    }
    if (escapeChance < 50) {
      setGameOver(true);
      this.player.setIsAlive(false);
    }
    return escapeChance >= 50;
  }

  /**
   * Returns the player object.
   *
   * @return Player object.
   */
  public Player getPlayer() {
    return this.player;
  }

  /**
   * Function that performs wrapping on the dungeon based on the boolean value assigned to it.
   *
   * @param rows   number of rows in the dungeon.
   * @param cols   columns in the dungeon.
   * @param toWrap boolean value for wrapping.
   */
  private void wrap(int rows, int cols, Boolean toWrap) {
    if (toWrap) {
      for (int rowPos = 0; rowPos < rows; rowPos++) {
        for (int colPos = 0; colPos < cols; colPos++) {

          if (rowPos == 0) {
            Edge upwardEdge = new Edge(locations[rowPos][colPos],
                    locations[rows - 1][colPos], Direction.NORTH);
            edgeList.add(upwardEdge);
          }

          if (colPos == 0) {
            Edge leftwardEdge = new Edge(locations[rowPos][colPos],
                    locations[rowPos][cols - 1], Direction.WEST);
            edgeList.add(leftwardEdge);
          }
        }
      }
    }
  }

  /**
   * Function that allocates items in the caves, which could either be treasure or arrows.
   */
  private void allocateItems(boolean isTreasure) {
    Integer totalNodes = getRows() * getCols();
    int noOfTunnels = 0;
    totalNodes = getTotalNodes(totalNodes, noOfTunnels);
    Integer numberOfNodesToAllocate = Math.toIntExact(Math.round(treasureCoverage * totalNodes));
    int allocated = 0;
    Set allocatedNodes = new HashSet();
    allocateItem(numberOfNodesToAllocate, allocated, allocatedNodes, isTreasure);

  }


  private void allocateItem(Integer numberOfNodesToAllocate, int allocated,
                            Set allocatedNodes, boolean isTreasure) {
    while (allocated < numberOfNodesToAllocate) {
      Map<String, Integer> placement =
              isTreasure ? random.randomTreasurePlacement(getRows(), getCols())
                      : random.randomArrowPlacement(getRows(), getCols());
      int randomRow = placement.get("x");
      int randomCol = placement.get("y");
      Treasure treasureType = Treasure.values()[random.randomValue(Treasure.values().length)];
      if (getLocations()[randomRow][randomCol].getIsTunnel()) {
        continue;
      }
      if (!allocatedNodes.contains(getLocations()[randomRow][randomCol])) {
        allocatedNodes.add(getLocations()[randomRow][randomCol]);
        if (isTreasure) {
          List<Treasure> treasureAtLocation =
                  getLocations()[randomRow][randomCol].getTreasureAtLocation();
          treasureAtLocation.add(treasureType);
          getLocations()[randomRow][randomCol].setTreasureAtLocation(treasureAtLocation);
        } else {
          getLocations()[randomRow][randomCol].incrementArrowCount();
        }
      } else {
        if (isTreasure) {
          List<Treasure> treasureAtLocation =
                  getLocations()[randomRow][randomCol].getTreasureAtLocation();
          treasureAtLocation.add(treasureType);
          getLocations()[randomRow][randomCol].setTreasureAtLocation(treasureAtLocation);
        } else {
          getLocations()[randomRow][randomCol].incrementArrowCount();
        }
        continue;
      }
      allocated++;
    }
  }

  private Integer getTotalNodes(Integer totalNodes, int noOfTunnels) {
    for (int i = 0; i < locations.length; i++) {
      for (int j = 0; j < locations[i].length; j++) {
        if (locations[i][j].getIsTunnel()) {
          noOfTunnels++;
        }
      }
    }
    totalNodes = totalNodes - noOfTunnels;
    return totalNodes;
  }

  /**
   * Function that sets the location nodes in the dungeon as caves and tunnels.
   */
  private void setCavesAndTunnels() {
    for (int i = 0; i < getRows(); i++) {
      for (int j = 0; j < getCols(); j++) {
        int count = 0;
        if (getLocations()[i][j].getDirectionOfLocation(Direction.WEST) != null) {
          count++;
        }
        if (getLocations()[i][j].getDirectionOfLocation(Direction.EAST) != null) {
          count++;
        }
        if (getLocations()[i][j].getDirectionOfLocation(Direction.SOUTH) != null) {
          count++;
        }
        if (getLocations()[i][j].getDirectionOfLocation(Direction.NORTH) != null) {
          count++;
        }
        if (count == 2) {
          getLocations()[i][j].setIsCave(false);
          getLocations()[i][j].setIsTunnel(true);
        } else {
          getLocations()[i][j].setIsCave(true);
          getLocations()[i][j].setIsTunnel(false);
        }
      }
    }
  }

  /**
   * Returns the rows in the dungeon.
   *
   * @return rows.
   */
  public int getRows() {
    return this.rows;
  }

  /**
   * Returns the columns in the dungeon.
   *
   * @return columns.
   */
  public int getCols() {
    return this.cols;
  }

  /**
   * Returns the 2D array of locations.
   *
   * @return 2D array.
   */
  public Location[][] getLocations() {
    return this.locations;
  }

  /**
   * Returns the number of edges used in the dungeon creation.
   */
  public int getNoOfEdges() {
    return this.noOfEdges;
  }

  /**
   * Returns the treasure coverage of the dungeon.
   *
   * @return treasure coverage.
   */
  @Override
  public double getTreasureCoverage() {
    return this.treasureCoverage;
  }


  private List<Edge> createEdges() {
    for (int rowPos = 0; rowPos < rows; rowPos++) {
      for (int colPos = 0; colPos < cols; colPos++) {
        Edge rightwardEdge = colPos + 1 < cols ? new Edge(locations[rowPos][colPos],
                locations[rowPos][colPos + 1], Direction.EAST) : null;
        Edge downwardEdge = rowPos + 1 < rows ? new Edge(locations[rowPos][colPos],
                locations[rowPos + 1][colPos], Direction.SOUTH) : null;
        if (rightwardEdge != null) {
          edgeList.add(rightwardEdge);
        }
        if (downwardEdge != null) {
          edgeList.add(downwardEdge);
        }
      }
    }
    return edgeList;
  }

  private List<Edge> randomizeEdges(List<Edge> edges) {
    return random.randomizeEdges(edges);
  }

  private boolean isInSameSet(Edge edge) {

    Location point1 = edge.getPoint1();
    Location point2 = edge.getPoint2();

    Set<Location> visited = new HashSet<>();

    Queue<Location> q = new LinkedList<>();

    visited.add(point1);

    if (point1.getDirectionOfLocation(Direction.NORTH) != null) {
      q.add(point1.getDirectionOfLocation(Direction.NORTH));
      visited.add(point1.getDirectionOfLocation(Direction.NORTH));
    }
    if (point1.getDirectionOfLocation(Direction.SOUTH) != null) {
      q.add(point1.getDirectionOfLocation(Direction.SOUTH));
      visited.add(point1.getDirectionOfLocation(Direction.SOUTH));
    }
    if (point1.getDirectionOfLocation(Direction.EAST) != null) {
      q.add(point1.getDirectionOfLocation(Direction.EAST));
      visited.add(point1.getDirectionOfLocation(Direction.EAST));
    }
    if (point1.getDirectionOfLocation(Direction.WEST) != null) {
      q.add(point1.getDirectionOfLocation(Direction.WEST));
      visited.add(point1.getDirectionOfLocation(Direction.WEST));
    }

    if (q.isEmpty()) {
      return false;
    }

    while (!q.isEmpty()) {
      point1 = q.poll();

      Location directionNorth = point1.getDirectionOfLocation(Direction.NORTH);
      Location directionSouth = point1.getDirectionOfLocation(Direction.SOUTH);
      Location directionEast = point1.getDirectionOfLocation(Direction.EAST);
      Location directionWest = point1.getDirectionOfLocation(Direction.WEST);


      if (directionNorth != null) {
        if (directionNorth == point2) {
          return true;
        } else {
          if (!visited.contains(directionNorth)) {
            q.add(directionNorth);
            visited.add(directionNorth);
          }
        }
      }

      if (directionSouth != null) {
        if (directionSouth == point2) {
          return true;
        } else {
          if (!visited.contains(directionSouth)) {
            q.add(directionSouth);
            visited.add(directionSouth);
          }
        }
      }

      if (directionEast != null) {
        if (directionEast == point2) {
          return true;
        } else {
          if (!visited.contains(directionEast)) {
            q.add(directionEast);
            visited.add(directionEast);
          }
        }
      }

      if (directionWest != null) {
        if (directionWest == point2) {
          return true;
        } else {
          if (!visited.contains(directionWest)) {
            q.add(directionWest);
            visited.add(directionWest);
          }
        }
      }
    }
    return false;
  }


  /**
   * To display the dungeon for debugging process.
   *
   * @param player player object.
   * @return string output.
   */
  public String[][] printMap(Player player) {
    String[][] originalMap = new String[getRows()][getCols()];
    String[][] output = new String[getRows() * 3][getCols() * 3];
    for (int i = 0; i < originalMap.length; i++) {
      for (int j = 0; j < originalMap[i].length; j++) {
        int startRow = i * 3;
        int startCol = j * 3;
        String[][] miniMap = getLocations()[i][j].toLocation(player);
        for (int r = 0; r < 3; r++) {
          for (int c = 0; c < 3; c++) {
            output[startRow + r][startCol + c] = miniMap[r][c];
          }
        }
      }
    }
    return output;
  }


  public int getNumberOfMonsters() {
    return numberOfMonsters;
  }


  public boolean isGameOver() {
    return isGameOver;
  }

  public void setGameOver(boolean gameOver) {
    isGameOver = gameOver;
  }

  public int getNoOfPits() {
    return this.noOfPits;
  }

}
