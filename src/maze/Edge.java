package maze;

/**
 * The class that implements edges between two points which are going to objects of type location.
 * Each edge will connect a cave to another cave or tunnel.
 */
public class Edge {
  private final Location point1;
  private final Location point2;
  private final Direction direction;


  /**
   * Constructor that constructs the object edge.
   *
   * @param point1    location node 1.
   * @param point2    location node 2.
   * @param direction direction in which the edge is being connected.
   */
  public Edge(Location point1, Location point2, Direction direction) {
    if (point1 == null || point2 == null) {
      throw new IllegalArgumentException("Locations cant be null");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Directions cant be null");
    }
    this.point1 = point1;
    this.point2 = point2;
    this.direction = direction;
  }

  /**
   * Returns the point 1 node of the edge.
   *
   * @return point 1 location node.
   */
  public Location getPoint1() {
    return this.point1;
  }

  /**
   * Returns the point 2 node of the edge.
   *
   * @return point 2 location node.
   */
  public Location getPoint2() {
    return this.point2;
  }

  /**
   * Returns the direction of the edge.
   *
   * @return direction
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Sets the direction of the edge when two points are given.
   *
   * @param direction direction of enum type.
   */
  public void setDirection(Direction direction) {
    if (point1 != null && point2 != null) {
      switch (direction) {
        case EAST:
          point1.setDirectionOfLocation(Direction.EAST, point2);
          point2.setDirectionOfLocation(Direction.WEST, point1);
          break;
        case WEST:
          point1.setDirectionOfLocation(Direction.WEST, point2);
          point2.setDirectionOfLocation(Direction.EAST, point1);
          break;
        case NORTH:
          point1.setDirectionOfLocation(Direction.NORTH, point2);
          point2.setDirectionOfLocation(Direction.SOUTH, point1);
          break;
        case SOUTH:
          point1.setDirectionOfLocation(Direction.SOUTH, point2);
          point2.setDirectionOfLocation(Direction.NORTH, point1);
          break;
        default:
          break;

      }
    }
  }


}
