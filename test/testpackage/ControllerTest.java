package testpackage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.DungeonConsoleController;
import controller.GuiController;
import maze.Direction;
import maze.Dungeon;
import maze.Maze;
import maze.ReadOnlyDungeon;
import radomnumbergenerator.TestRandomNumberGenerator;
import view.IStarterView;
import view.IView;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class created to test the functionality of the controller and model together.
 */
public class ControllerTest {

  private Maze m;
  Appendable gameLog;
  StringReader input;
  DungeonConsoleController c;
  TestRandomNumberGenerator testRandomNumberGenerator;

  @Before
  public void setUp() {

    TestRandomNumberGenerator testRandomNumberGenerator = new TestRandomNumberGenerator();
    m = new Dungeon(6, 8, false,0, 0.3,
            4, 3, testRandomNumberGenerator);
    gameLog = new StringBuilder();
    this.testRandomNumberGenerator = testRandomNumberGenerator;
  }

  @After
  public void afterTest() {
    testRandomNumberGenerator.resetStaticVariablesForTesting();
  }

  @Test
  public void testPickupTreasure() {
    input = new StringReader("show P T q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    String str = "Type show for displaying the dungeon again\n" +
            "Treasure that player has : {}\n" +
            "Number of arrows that player has : 3\n" +
            "You are in a cave\n" +
            "The possible moves of the player are : \n" +
            "{NORTH=false, SOUTH=true, EAST=true, WEST=true} \n" +
            "The treasure at the current location is : {SAPPHIRE=1}\n" +
            "arrow count at location is : 1\n" +
            "\n" +
            "What would you like to do? Move (M), Pickup (P), or Shoot(S)?" +
            "What do you want to pickup? Treasure(T), or Arrows(A)? \n" +
            "Treasure that player has : {SAPPHIRE=1}\n" +
            "Number of arrows that player has : 3\n" +
            "You are in a cave\n" +
            "The possible moves of the player are : \n" +
            "{NORTH=false, SOUTH=true, EAST=true, WEST=true} \n" +
            "The treasure at the current location is : {}\n" +
            "arrow count at location is : 1\n" +
            "\n" +
            "What would you like to do? Move (M), Pickup (P), or Shoot(S)?";
    assertEquals(str,gameLog.toString());
  }

  @Test
  public void invalidMoveForDirection() {
    input = new StringReader("show M direction q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("Please Enter one of these possible "
            + "values : NORTH, SOUTH, EAST, WEST"));
  }

  @Test
  public void invalidArrowPickUp() {
    input = new StringReader("show M SOUTH P A q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("There is no Arrow, invalid move"));

  }

  @Test
  public void invalidTreasurePickUp() {
    input = new StringReader("show M SOUTH P T q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("There is no Treasure, invalid move"));
  }

  @Test
  public void invalidDirection() {
    input = new StringReader("show S d 1 q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("Invalid direction, cannot move this way"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void modelNull() {
    input = new StringReader("show M q");
    c = new DungeonConsoleController(input, gameLog, null);
    c.playGame();
  }

  @Test
  public void testArrowCount() {
    input = new StringReader("show M EAST M EAST M EAST  P A q");
    assertEquals(3,m.getPlayer().getArrowCountOfPlayer());
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertEquals(4,m.getPlayer().getArrowCountOfPlayer());
  }

  @Test
  public void testArrowCount1() {
    input = new StringReader("show M EAST  S SOUTH 1 S SOUTH 1 S "
           + "SOUTH 1 S SOUTH 1 S SOUTH 1 q");
    assertEquals(3,m.getPlayer().getArrowCountOfPlayer());
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("There are not enough arrows available!"));
  }

  @Test
  public void pickupTreasure() {
    input = new StringReader("show P T q");
    assertEquals("{}",m.getPlayer().printDescription().toString());
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertEquals("{SAPPHIRE=1}",m.getPlayer().printDescription().toString());
  }

  @Test
  public void mockViewCheck() {
    testRandomNumberGenerator.resetStaticVariablesForTesting();
    input = new StringReader("show S SOUTH 1");
    StringBuilder log = new StringBuilder();
    IStarterView view = new MockView(log);
    IView view1 = new MockView(log);
    ReadOnlyDungeon mock = new MockModel(log, "abc");
    GuiController a = new GuiController(view);
    a.copyViewCreated(view1);
    a.copyDungeonCreated(mock);
    a.playGame();
    a.moveInView(Direction.EAST);
    a.collectTreasure(true);
    a.collectTreasure(false);
    a.shootArrow(Direction.WEST,2);
    a.moveInView(Direction.SOUTH);
    String str = "\nClicks are happening\n"
            + "\n"
            + "The player is moving in EAST direction.\n"
            + "refresh\n"
            + "\n"
            + "The player is picking up treasure.\n"
            + "The player is picking up arrow.\n"
            + "Player is shooting in WEST direction and from  2 caves\n"
            + "The player is moving in SOUTH direction.\n"
            + "refresh\n";
    assertEquals(str,log.toString());
  }

  @Test
  public void mockCheck() {
    testRandomNumberGenerator.resetStaticVariablesForTesting();
    input = new StringReader("show S SOUTH 1");
    StringBuilder log = new StringBuilder();
    MockModel mock = new MockModel(log, "abc");
    c = new DungeonConsoleController(input, gameLog,mock);
    c.playGame();
    assertEquals(new StringBuilder("Input: SOUTH 1").toString(), log.toString());
  }

  @Test
  public void shootMonster() {
    input = new StringReader("show M EAST M EAST M EAST M EAST S SOUTH 1 q");
    assertEquals(3,m.getPlayer().getArrowCountOfPlayer());
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("You hear a great howl in the distance!"));
    assertEquals(2,m.getPlayer().getArrowCountOfPlayer());
  }

  @Test
  public void playerWinning() {
    input = new StringReader("show M EAST M EAST M EAST S SOUTH 1 S "
            + "SOUTH 1 M SOUTH M SOUTH M SOUTH M SOUTH M SOUTH q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("You hear a great howl in the distance!"));
    assertTrue(gameLog.toString().contains("You reached the end. Hurrayyyy!!"));

  }

  @Test
  public void playerKilled() {
    input = new StringReader("show M EAST M EAST M EAST M EAST "
            + "M SOUTH M SOUTH M SOUTH M SOUTH M SOUTH q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertFalse(m.getPlayer().isAlive());
    assertTrue(gameLog.toString().contains("Game Over!! Please restart. 👾"));
  }

  @Test
  public void checkWeakSmellStrength() {
    input = new StringReader("show M EAST M EAST M EAST M EAST "
            + "M SOUTH M SOUTH M SOUTH q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("You smell something pungent at a distance"));
  }


  @Test
  public void checkStrongSmellStrength() {
    input = new StringReader("show M EAST M EAST M EAST M EAST "
            + "M SOUTH M SOUTH M SOUTH M SOUTH q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("You smell something terrible nearby"));
  }

  @Test
  public void escapeWhenMonsterHurt() {
    input = new StringReader("show M EAST M EAST M EAST "
            + "M SOUTH M SOUTH M SOUTH M SOUTH S SOUTH 1 M SOUTH q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertEquals(1,m.getPlayer().getFinalLocation().getMonster().getLives());
    assertTrue(m.getPlayer().isAlive());
    assertTrue(gameLog.toString().contains("You reached the end. Hurrayyyy!!"));
  }

  @Test
  public void fireInBlank() {
    input = new StringReader("show M EAST "
            + "S SOUTH 1 q");
    c = new DungeonConsoleController(input, gameLog, m);
    c.playGame();
    assertTrue(gameLog.toString().contains("You shoot the arrow into the darkness!!"
            + " You lost one arrow!"));
  }

}
