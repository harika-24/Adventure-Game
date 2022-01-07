package maze;

/**
 * A public interface created to represent the Otyughs in the dungeon. They are always present at
 * the final locations and can be killed by shooting twi arrows. They have SMELL characteristic and
 * when player enters the cave with healthy Otyugh he will be killed.
 */

public interface Enemy {

  /**
   * Returns the boolean value if the monster is alive or not.
   *
   * @return boolean value.
   */
  boolean getIsAlive();

  /**
   * Returns the health of the monster. If it is hit once its life will be 1.
   *
   * @return number of lives.
   */
  int getLives();

  /**
   * Sets if the monster is alive or not.
   *
   * @param alive boolean value.
   */
  void setAlive(boolean alive);

  /**
   * Decreases the life of the monster when the player shoot the arrow at it.s
   */
  void decreaseLife();
}

