package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * The class that actually represents a node in the maze. The object of this class will basically
 * be pointing to other nodes depending on how the objects will be constructing.
 */
public class Location {

  private int rowPos;
  private int colPos;

  private boolean isCave;
  private boolean isTunnel;
  private boolean isPit;
  private int arrowCount;
  private Treasure treasure;
  private Monster monster;

  private Location locationOnNorth;
  private Location locationOnSouth;
  private Location locationOnEast;
  private Location locationOnWest;

  private List<Location> locationPath;

  private List<Treasure> treasureAtLocation;

  private boolean isStartLocation;
  private boolean isEndLocation;
  private boolean hasPlayer;


  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RESET = "\u001B[0m";


  /**
   * Constructor which would construct a location node in the dungeon by accepting the following
   * parameters.
   *
   * @param rowPos position of the row.
   * @param colPos position of the column.
   */
  public Location(int rowPos, int colPos) {
    this.rowPos = rowPos;
    this.colPos = colPos;
    this.treasureAtLocation = new ArrayList<>();
    this.arrowCount = 0;
    this.hasPlayer = false;
  }

  /**
   * Copy constructor for the Location object.
   *
   * @param location location object.
   */
  public Location(Location location) {
    rowPos = location.rowPos;
    colPos = location.colPos;
    isCave = location.isCave;
    isTunnel = location.isTunnel;
    isPit = location.isPit;
    arrowCount = location.arrowCount;
    treasure = location.treasure;
    monster = location.monster;
    locationOnNorth = location.locationOnNorth;
    locationOnSouth = location.locationOnSouth;
    locationOnEast = location.locationOnEast;
    locationOnWest = location.locationOnWest;
    locationPath = location.getLocationPath();
    treasureAtLocation = location.getTreasureAtLocation();
    isStartLocation = location.isStartLocation;
    isEndLocation = location.isEndLocation;
    hasPlayer = location.hasPlayer;
  }

  /**
   * returns the position of the row.
   *
   * @return position of the row.
   */
  public int getRowPos() {
    return this.rowPos;
  }

  /**
   * returns the position of the column.
   *
   * @return position of the column.
   */
  public int getColPos() {
    return this.colPos;
  }

  /**
   * Returns if the location is tunnel or not.
   *
   * @return boolean value.
   */
  public boolean getIsTunnel() {
    return this.isTunnel;
  }

  /**
   * Returns if the location is cave or not.
   *
   * @return boolean value.
   */
  public boolean getIsCave() {
    return this.isCave;
  }


  /**
   * Returns the arrow count present in the location.
   *
   * @return arrow count.
   */
  public int getArrowCount() {
    return this.arrowCount;
  }

  /**
   * Returns the monster present at the location.
   *
   * @return immutable monster object.
   */
  public Monster getMonster() {
    if (monster != null) {
      return new Monster(monster);
    } else {
      return null;
    }
  }

  /**
   * Setter for the monster object.
   *
   * @param monster monster object.
   */
  public void setMonster(Monster monster) {
    this.monster = monster;
  }


  /**
   * Increments the arrow count when the player picks up the arrows in the dungeon.
   */
  public void incrementArrowCount() {
    this.arrowCount++;
  }

  /**
   * Sets if the location is cave or not.
   *
   * @param value true or false
   */
  public void setIsCave(boolean value) {
    this.isCave = value;
  }

  /**
   * Sets if the location is tunnel or not.
   *
   * @param value true or false
   */
  public void setIsTunnel(boolean value) {
    this.isTunnel = value;
  }


  /**
   * Sets the treasure value.
   *
   * @param treasure treasure in the location.
   */
  public void setTreasure(Treasure treasure) {
    this.treasure = treasure;
  }

  /**
   * Returns the treasure that is present at the location which might be cave or tunnel.
   *
   * @return treasure.
   */
  public Treasure getTreasure() {
    return this.treasure;
  }


  /**
   * Returns the list of locations which is optimal from the start to that point.
   *
   * @return locations present in the dungeon.
   */
  public List<Location> getLocationPath() {
    if (this.locationPath == null) {
      return null;
    }
    return new ArrayList<>(this.locationPath);
  }

  /**
   * assigns a list of locations which is optimal from the start to that point.
   *
   * @param locationPath list of optimal path.
   */
  public void setLocationPath(List<Location> locationPath) {
    this.locationPath = locationPath;
  }

  /**
   * Clears the treasure from the location once the player has collected the treasure.
   */
  public void removeTreasure() {
    treasureAtLocation.clear();
  }

  /**
   * Clears the arrows from the locations when the player collects the arrows.
   */
  public void removeArrows() {
    arrowCount = 0;
  }

  /**
   * Returns the location of node that is connected to that direction depending on the
   * direction that is passed.
   *
   * @param direction enum direction.
   * @return location node.
   */
  public Location getDirectionOfLocation(Direction direction) {
    switch (direction) {
      case NORTH:
        return locationOnNorth;
      case SOUTH:
        return locationOnSouth;
      case EAST:
        return locationOnEast;
      case WEST:
        return locationOnWest;
      default:
        break;
    }
    return null;
  }

  /**
   * Based on the direction that is passed, the current node points the location node in that
   * direction.
   *
   * @param direction enum direction.
   * @param location  location node.
   */
  public void setDirectionOfLocation(Direction direction, Location location) {
    switch (direction) {
      case NORTH:
        this.locationOnNorth = location;
        break;
      case SOUTH:
        this.locationOnSouth = location;
        break;
      case EAST:
        this.locationOnEast = location;
        break;
      case WEST:
        this.locationOnWest = location;
        break;
      default:
        break;
    }
  }

  /**
   * Describes the current location, it includes what all possible moves can be made by the player,
   * the treasure at the location.
   *
   * @return String that caries all the information.
   */
  public String describeLocation() {

    Map<Direction, Boolean> possibleMovesMap = getDirectionBooleanMap();

    Map<Treasure, Integer> treasureIntegerMap = getTreasureMap();

    return "\nThe possible moves of the player are : \n" + possibleMovesMap.toString()
            + " \n" + "The treasure at the current location is : " + treasureIntegerMap.toString()
            + "\n" + "arrow count at location is : " + getArrowCount() + "\n";

  }

  /**
   * Returns the treasure map which has the type of treasure and the amount of the treasure at the
   * location.
   *
   * @return map.
   */
  public Map<Treasure, Integer> getTreasureMap() {
    Map<Treasure, Integer> treasureIntegerMap = new HashMap();
    treasureAtLocation.forEach(treasure -> {
      treasureIntegerMap.putIfAbsent(treasure, 0);
      treasureIntegerMap.put(treasure, treasureIntegerMap.get(treasure) + 1);
    });
    return treasureIntegerMap;
  }


  /**
   * Returns a map which has the directions of the location and the boolean values which tell us
   * whether there is a possible move or not.
   *
   * @return map.
   */
  public Map<Direction, Boolean> getDirectionBooleanMap() {
    Location north = this.getDirectionOfLocation(Direction.NORTH);
    Location south = this.getDirectionOfLocation(Direction.SOUTH);
    Location east = this.getDirectionOfLocation(Direction.EAST);
    Location west = this.getDirectionOfLocation(Direction.WEST);

    Map<Direction, Boolean> possibleMovesMap = new TreeMap<>();

    if (north == null) {
      possibleMovesMap.putIfAbsent(Direction.NORTH, false);
    } else {
      possibleMovesMap.putIfAbsent(Direction.NORTH, true);
    }

    if (south == null) {
      possibleMovesMap.putIfAbsent(Direction.SOUTH, false);
    } else {
      possibleMovesMap.putIfAbsent(Direction.SOUTH, true);
    }

    if (east == null) {
      possibleMovesMap.putIfAbsent(Direction.EAST, false);
    } else {
      possibleMovesMap.putIfAbsent(Direction.EAST, true);
    }

    if (west == null) {
      possibleMovesMap.putIfAbsent(Direction.WEST, false);
    } else {
      possibleMovesMap.putIfAbsent(Direction.WEST, true);
    }
    return possibleMovesMap;
  }

  /**
   * Returns the treasure at the location.
   *
   * @return treasure.
   */
  public List<Treasure> getTreasureAtLocation() {
    return new ArrayList<>(this.treasureAtLocation);
  }

  /**
   * Sets the treasure at Location.
   *
   * @param treasures list.
   */
  public void setTreasureAtLocation(List<Treasure> treasures) {
    this.treasureAtLocation = treasures;
  }

  /**
   * Function to print the dungeon to the screen.
   *
   * @param player player.
   * @return string.
   */
  public String[][] toLocation(Player player) {
    String[][] strings = new String[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        strings[i][j] = " ";
        if (i == 0 && j == 1) {
          strings[i][j] = this.getDirectionOfLocation(Direction.NORTH) != null ? "|" : " ";
        }
        if (i == 1 && j == 0) {
          strings[i][j] = this.getDirectionOfLocation(Direction.WEST) != null ? "-" : " ";
        }
        if (i == 1 && j == 2) {
          strings[i][j] = this.getDirectionOfLocation(Direction.EAST) != null ? "-" : " ";
        }
        if (i == 2 && j == 1) {
          strings[i][j] = this.getDirectionOfLocation(Direction.SOUTH) != null ? "|" : " ";
        }
        if (i == 1 && j == 1) {
          strings[i][j] = String.valueOf(0);
          if (player != null && player.getCurrentLocation().getRowPos() == this.rowPos
                  && player.getCurrentLocation().getColPos() == this.colPos) {
            strings[i][j] = "P";
          }
          strings[i][j] = this.arrowCount != 0 ? "x" : strings[i][j];
          strings[i][j] = monster != null && monster.getIsAlive()
                  && monster.getLives() == 2 ? "👹" : strings[i][j];
          strings[i][j] = monster != null
                  && monster.getIsAlive() && monster.getLives() == 1 ? "👿" : strings[i][j];

          strings[i][j] = this.isPit ? "o" : strings[i][j];

          strings[i][j] = isStartLocation()
                  ? (ANSI_GREEN + strings[i][j] + ANSI_RESET) : strings[i][j];
          strings[i][j] = isEndLocation()
                  ? (ANSI_RED + strings[i][j] + ANSI_RESET) : strings[i][j];


        }
      }
    }

    return strings;

  }

  /**
   * Returns the boolean value if the location is start location or not.
   *
   * @return boolean value.
   */
  public boolean isStartLocation() {
    return isStartLocation;
  }

  /**
   * Sets the boolean value true or false for the start location.
   *
   * @param startLocation location node.
   */
  public void setStartLocation(boolean startLocation) {
    isStartLocation = startLocation;
  }

  /**
   * Returns the boolean value if the location is end location or not.
   *
   * @return true or false.
   */
  public boolean isEndLocation() {
    return isEndLocation;
  }

  /**
   * Sets the boolean value true or false for the end location.
   *
   * @param endLocation location node.
   */
  public void setEndLocation(boolean endLocation) {
    isEndLocation = endLocation;
  }

  /**
   * Returns if the location has a player or not.
   * @return boolean value
   */
  public boolean hasPlayer() {
    return hasPlayer;
  }

  /**
   * Sets if the location has a player or not.
   * @param hasPlayer boolean value.
   */
  public void setHasPlayer(boolean hasPlayer) {
    this.hasPlayer = hasPlayer;
  }


  /**
   * Based on the movement of the player in the dungeon this function will tell if the monster is
   * nearby and will give the Smell strength.There is a weak smell when they are two caves away
   * and strong smell when they are present one cave away.
   * @return Smell Strength.
   */
  public Smell checkSmellStrength() {
    Map<Integer, Integer> smellyMonsters = checkForMonsters();

    Integer noOfMonstersOnePosAway = smellyMonsters.get(1);
    Integer noOfMonstersTwoPosAway = smellyMonsters.get(2);

    if (noOfMonstersOnePosAway > 0 || noOfMonstersTwoPosAway > 1) {
      return Smell.STRONG;
    }
    if (noOfMonstersTwoPosAway == 1) {
      return Smell.WEAK;
    }
    return Smell.NONE;
  }

  /**
   * This function implements a BFS algorithm, and checks if the monster is one or two positions
   * away from the player's position.
   * @return a map which maintains the count of monsters that are one and two positions away.
   */
  public Map<Integer, Integer> checkForMonsters() {

    Map<Integer, Integer> monsterMap = new HashMap<>();

    int onePosMonsterCount = 0;
    int twoPosMonsterCount = 0;

    Location checkLocation = this;

    Queue<Location> q = new LinkedList<>();
    q.add(checkLocation);
    q.add(null);
    Set<Location> visited = new HashSet<>();
    visited.add(checkLocation);


    int pathLength = 0;

    while (!q.isEmpty()) {
      Location location = q.poll();

      checkLocation = location;

      if (location == null) {
        pathLength++;
        if (pathLength == 3) {
          monsterMap.put(1, onePosMonsterCount);
          monsterMap.put(2, twoPosMonsterCount);
          return monsterMap;
        }
        q.add(null);
        if (q.peek() == null) {
          break;
        } else {
          continue;
        }
      }

      if (checkLocation.getMonster() != null && checkLocation.getMonster().getIsAlive()
              && pathLength == 1) {
        onePosMonsterCount++;
      } else if (checkLocation.getMonster() != null && checkLocation.getMonster().getIsAlive()
              && pathLength == 2) {
        twoPosMonsterCount++;
      }

      Location directionNorth = checkLocation.getDirectionOfLocation(Direction.NORTH);
      Location directionSouth = checkLocation.getDirectionOfLocation(Direction.SOUTH);
      Location directionEast = checkLocation.getDirectionOfLocation(Direction.EAST);
      Location directionWest = checkLocation.getDirectionOfLocation(Direction.WEST);

      addToQueue(q, visited, directionNorth);
      addToQueue(q, visited, directionSouth);
      addToQueue(q, visited, directionEast);
      addToQueue(q, visited, directionWest);

    }
    monsterMap.put(1, onePosMonsterCount);
    monsterMap.put(2, twoPosMonsterCount);
    return monsterMap;
  }

  private void addToQueue(Queue<Location> q, Set<Location> visited, Location directionNorth) {
    if (directionNorth != null && !visited.contains(directionNorth)) {
      q.add(directionNorth);
      visited.add(directionNorth);
    }

  }

  /**
   * Returns if the current location is a pit or not.
   *
   * @return boolean value.
   */
  public boolean isPit() {
    return isPit;
  }

  /**
   * Sets the current location isPit value to the boolean value passed.
   *
   * @param pit boolean value.
   */
  public void setPit(boolean pit) {
    isPit = pit;
  }
}

