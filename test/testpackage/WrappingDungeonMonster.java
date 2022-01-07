package testpackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import maze.Direction;
import maze.Dungeon;
import maze.Maze;
import maze.Monster;
import maze.Smell;
import radomnumbergenerator.TestRandomNumberGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class to test the wrapping dungeon with the monsters.
 */
public class WrappingDungeonMonster {

  private Maze dungeon;
  private TestRandomNumberGenerator testRandomNumberGenerator;

  @Before
  public void setUp() {
    TestRandomNumberGenerator testRandomNumberGenerator = new TestRandomNumberGenerator();
    dungeon = new Dungeon(6, 8, true, 0, 0.3,
            4, 3, testRandomNumberGenerator);
    this.testRandomNumberGenerator = testRandomNumberGenerator;
  }

  @After
  public void afterTest() {
    testRandomNumberGenerator.resetStaticVariablesForTesting();
  }

  @Test
  public void testMonsterAllocationCount() {
    int countMonsters = 0;
    for (int i = 0; i < dungeon.getRows(); i++) {
      for (int j = 0; j < dungeon.getCols(); j++) {
        if (dungeon.getLocations()[i][j].getMonster() != null) {
          countMonsters++;
        }
      }
    }
    assertEquals(dungeon.getNumberOfMonsters(), countMonsters);

  }

  @Test
  public void testMonsterAtEnd() {
    Monster monster = dungeon.getPlayer().getFinalLocation().getMonster();
    assertNotNull(monster);
    assertTrue(monster.getIsAlive());
  }


  @Test
  public void testPickupTreasure() {
    assertEquals(dungeon.getPlayer().getCurrentLocation().getTreasureAtLocation().isEmpty(),
            false);
    dungeon.pickup(true);
    assertEquals(dungeon.getPlayer().getCurrentLocation().getTreasureAtLocation().isEmpty(),
            true);
  }

  @Test
  public void testPickupArrow() {

    int originalArrowCount = dungeon.getPlayer().getArrowCountOfPlayer();
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][5]);
    assertTrue(dungeon.getPlayer().getCurrentLocation().getArrowCount() > 0);
    dungeon.pickup(false);
    assertEquals(0, dungeon.getPlayer().getCurrentLocation().getArrowCount());
    assertTrue(dungeon.getPlayer().getArrowCountOfPlayer() > originalArrowCount);
  }

  @Test
  public void checkPlayerArrowCountStart() {
    assertEquals(3, dungeon.getPlayer().getArrowCountOfPlayer());
  }

  @Test
  public void testMonstersInitialHealth() {
    int countMonstersWithFullHealth = 0;
    for (int i = 0; i < dungeon.getRows(); i++) {
      for (int j = 0; j < dungeon.getCols(); j++) {
        if (dungeon.getLocations()[i][j].getMonster() != null
                && dungeon.getLocations()[i][j].getMonster().getLives() == 2) {
          countMonstersWithFullHealth++;
        }
      }
    }
    assertEquals(dungeon.getNumberOfMonsters(), countMonstersWithFullHealth);
  }

  @Test
  public void checkIfMonsterAbsentAtStart() {
    assertNull(dungeon.getPlayer().getStartLocation().getMonster());
  }

  @Test
  public void testArrowCoverage() {
    double t = dungeon.getTreasureCoverage();
    double noOfCaves = 0;
    for (int rowPos = 0; rowPos < dungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < dungeon.getLocations()[rowPos].length; colPos++) {
        if (dungeon.getLocations()[rowPos][colPos].getIsCave()) {
          noOfCaves++;
        }
      }
    }
    int totalArrowCaves = Math.toIntExact(Math.round(t * noOfCaves));
    int countCavesWithArrows = 0;
    for (int rowPos = 0; rowPos < dungeon.getLocations().length; rowPos++) {
      for (int colPos = 0; colPos < dungeon.getLocations()[rowPos].length; colPos++) {
        if (dungeon.getLocations()[rowPos][colPos].getArrowCount() > 0) {
          countCavesWithArrows++;
        }
      }
    }
    assertEquals(totalArrowCaves, countCavesWithArrows);
  }

  @Test
  public void checkSmellAtStart() {
    Smell smell = dungeon.getPlayer().getCurrentLocation().checkSmellStrength();
    assertEquals(smell, Smell.NONE);
  }

  @Test
  public void checkSmellAtOneLocationBeforeEnd() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][4]);
    Smell smell = dungeon.getPlayer().getCurrentLocation().checkSmellStrength();
    assertEquals(smell, Smell.STRONG);
  }

  @Test
  public void checkSmellAtTwoLocationsBeforeEnd() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][3]);
    Smell smell = dungeon.getPlayer().getCurrentLocation().checkSmellStrength();
    assertEquals(smell, Smell.WEAK);
  }

  @Test
  public void checkMonsterLocationType() {
    int countMonstersInCaves = 0;
    for (int i = 0; i < dungeon.getRows(); i++) {
      for (int j = 0; j < dungeon.getCols(); j++) {
        if (dungeon.getLocations()[i][j].getMonster() != null
                && dungeon.getLocations()[i][j].getIsCave()) {
          countMonstersInCaves++;
        }
      }
    }
    assertEquals(dungeon.getNumberOfMonsters(), countMonstersInCaves);
  }

  @Test
  public void shootArrow() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][4]);
    assertEquals(2, dungeon.getLocations()[5][4].getMonster().getLives());
    dungeon.shoot(Direction.NORTH, 1);
    assertEquals(1, dungeon.getLocations()[5][4].getMonster().getLives());
  }

  @Test
  public void arrowDecrement() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][4]);
    assertEquals(2, dungeon.getLocations()[5][4].getMonster().getLives());
    dungeon.shoot(Direction.NORTH, 1);
    assertTrue(dungeon.getLocations()[5][4].getMonster().getIsAlive());
    assertEquals(2, dungeon.getPlayer().getArrowCountOfPlayer());
  }

  @Test
  public void checkMonsterDead() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][4]);
    assertEquals(2, dungeon.getLocations()[5][4].getMonster().getLives());
    dungeon.shoot(Direction.NORTH, 1);
    assertTrue(dungeon.getLocations()[5][4].getMonster().getIsAlive());
    dungeon.shoot(Direction.NORTH, 1);
    assertFalse(dungeon.getLocations()[5][4].getMonster().getIsAlive());
  }

  @Test
  public void escapeChanceForPlayer() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[4][4]);
    assertEquals(2, dungeon.getLocations()[5][4].getMonster().getLives());
    dungeon.shoot(Direction.SOUTH, 1);
    assertTrue(dungeon.getLocations()[5][4].getMonster().getIsAlive());
    dungeon.movePlayer(Direction.SOUTH);
    assertTrue(dungeon.getPlayer().isAlive());
  }

  @Test
  public void playerEatenByMonster() {
    dungeon.getPlayer().setCurrentLocation(dungeon.getLocations()[0][4]);
    assertEquals(2, dungeon.getLocations()[5][4].getMonster().getLives());
    dungeon.movePlayer(Direction.NORTH);
    assertFalse(dungeon.getPlayer().isAlive());
  }
}
