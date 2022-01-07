package maze;

/**
 * A public class created to represent the Otyughs in the dungeon that implements the Enemy
 * interface. They are always present at the final locations and can be killed by shooting
 * two arrows. They have SMELL characteristic and when player enters the cave
 * with healthy Otyugh he will be killed.
 */
public class Monster implements Enemy {
  private boolean isAlive;
  private int lives;


  /**
   * Constructor to create an object of Monster.
   */
  public Monster() {
    this.isAlive = true;
    this.lives = 2;
  }

  /**
   * Copy constructor for the Monster.
   * @param monster Monster object.
   */
  public Monster(Monster monster) {
    isAlive = monster.isAlive;
    lives = monster.lives;
  }

  @Override
  public boolean getIsAlive() {
    return this.isAlive;
  }


  @Override
  public int getLives() {
    return this.lives;
  }


  @Override
  public void setAlive(boolean alive) {
    this.isAlive = alive;
  }


  @Override
  public void decreaseLife() {
    this.lives = this.lives - 1;
    if (this.lives == 0) {
      this.isAlive = false;
    }
  }
}
