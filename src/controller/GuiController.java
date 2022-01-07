package controller;

import maze.Direction;
import maze.Dungeon;
import maze.ReadOnlyDungeon;
import radomnumbergenerator.RandomNumberGenerator;
import view.IStarterView;
import view.IView;
import view.MenuView;

import java.util.ArrayList;
import java.util.Map;


/**
 * The DungeonConsoleController is the GUI based controller of this project. It takes the starter
 * view which gives the user inputs for the model and creates the dungeon model.The model is then
 * passed to the MenuView which displays the dungeon to the user where he can play the game.The
 * model computes the values returns them to controller and the controller returns them to the view.
 */
public class GuiController implements DungeonController {

  private ReadOnlyDungeon dungeon;
  private IView view;
  private IStarterView startView;

  /**
   * Constructor for the GUI Controller Class.
   *
   * @param v Start view object.
   */
  public GuiController(IStarterView v) {
    this.startView = v;
  }

  @Override
  public void playGame() {
    startView.addClickListener(this);

  }

  /**
   * The function that creates the dungeon model when the user enters the input in the form.
   *
   * @param row               no. of rows.
   * @param column            no. of columns.
   * @param bool              wrapping or not.
   * @param interconnectivity interconnectivity.
   * @param treasureCoverage  coverage of treasure.
   * @param noOfMonsters      number of monsters in the dungeon.
   * @param noOfPits          number of pits.
   */
  public void createDungeon(int row, int column, boolean bool, int interconnectivity,
                            double treasureCoverage, int noOfMonsters, int noOfPits) {
    this.dungeon = new Dungeon(row, column, bool, interconnectivity, treasureCoverage,
            noOfMonsters, noOfPits, new RandomNumberGenerator());
    this.startView.makeVisible(false);
    this.view = new MenuView(this.dungeon);
    this.view.addClickListener(this);
  }

  /**
   * Function that calls the move function in the model.
   *
   * @param direction direction in which the player has to move.
   * @return the value based on which user gets feedback in the view
   */
  public int moveInView(Direction direction) {


    if (!dungeon.isGameOver() || dungeon.getPlayer().isAlive()) {
      dungeon.movePlayer(direction);
      view.refresh();
      return 1;
    } else if (!dungeon.getPlayer().isAlive()) {
      view.refresh();
      return 2;
    } else if (dungeon.getPlayer().getCurrentLocation() == dungeon.getPlayer().getFinalLocation()) {
      view.refresh();
      return 3;
    } else {
      view.refresh();
      return 4;
    }

  }

  /**
   * Function that calls the pickup function in the model.
   *
   * @param bool boolean value which tells whether to pickup arrow or treasure.
   */
  public void collectTreasure(boolean bool) {
    dungeon.pickup(bool);
  }


  /**
   * Function that calls the shoot function in the model.
   *
   * @param direction direction in which the player has to shoot.
   * @param distance  distance through which the arrow has to travel.
   * @return the value based on which user gets feedback in the view
   */
  public int shootArrow(Direction direction, int distance) {
    Map<String, Boolean> monsters = dungeon.shoot(direction, distance);
    if (monsters.get("Monster Hit")) {
      return 1;
    } else if (monsters.get("Monster Dead")) {
      return 2;
    } else if (dungeon.getPlayer().getArrowCountOfPlayer() == 0) {
      return 3;
    } else if (!monsters.get("Monster Hit") && !monsters.get("Monster Dead")) {
      return 4;
    } else {
      return 5;
    }

  }

  /**
   * The function that copies the dungeon that is being created when the StartView is called
   * and is later passed to the MenuView.
   *
   * @param m ReadOnlyDungeon.
   */
  public void copyDungeonCreated(ReadOnlyDungeon m) {
    this.dungeon = m;
  }

  /**
   * The function that copies the MenuView being created in the Controller.
   *
   * @param v ReadOnlyDungeon.
   */
  public void copyViewCreated(IView v) {
    this.view = v;
  }


  /**
   * Function that would quit the game and close all the views. This is called from the view.
   */
  public void quitGame() {
    this.view.destroyView();
    this.startView.destroyView();
  }


  /**
   * Function that is used when the user wants to restart the game either with the same dungeon
   * or the old dungeon.
   *
   * @param state boolean value to check if the user wants the same or new dungeon.
   */
  public void restart(boolean state) {
    if (!state) {
      this.view.destroyView();
      this.startView.makeVisible(true);
    } else {
      this.view.destroyView();
      this.dungeon.getPlayer().setArrowCount(3);
      this.dungeon.getPlayer().setTreasureCollected(new ArrayList<>());
      this.view = new MenuView(this.dungeon);

      this.view.addClickListener(this);
    }
  }

}
