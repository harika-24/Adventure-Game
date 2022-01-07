package view;

import controller.GuiController;
import maze.Direction;
import maze.Location;
import maze.ReadOnlyDungeon;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

/**
 * This class is used to create the view which would display the dungeon and the panel with the
 * location and the player information.
 */
public class MenuView extends JFrame implements IView, ActionListener, KeyListener {
  private final Set<Integer> pressedKeys;
  private final JMenuBar menu;
  private final JPanel displayPanel;
  private final JPanel textPanel;
  private final JSplitPane splitPane;
  private final ReadOnlyDungeon d;
  private GuiController listener;
  private CellLocation[][] location;
  private final JLabel label1;

  /**
   * Constructor for the view class. It takes in the Maze object.
   *
   * @param d ReadOnlyDungeon object.
   */
  public MenuView(ReadOnlyDungeon d) {
    if (d == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.setSize(new Dimension(1400, 900));
    this.setLayout(new FlowLayout());
    this.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    displayPanel = new JPanel(new FlowLayout());
    displayPanel.setSize(new Dimension(960, 900));
    textPanel = new JPanel(new FlowLayout());
    textPanel.setSize(new Dimension(200, 1000));
    menu = new JMenuBar();
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    mapSplitPane();
    label1 = new JLabel();
    pressedKeys = new HashSet<>();
    this.setVisible(true);
    this.setSize(1440, 1000);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.d = d;
    createMenu();
    displayLocation(d);

  }

  /**
   * Creates the menu bar at the top of the frame which gives the player the option to restart
   * with same or new dungeon or quit the game.
   */
  public void createMenu() {
    JMenu restart = new JMenu(" Restart ");
    JMenu quit = new JMenu(" Quit ");
    restart.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    restart.setVisible(true);
    JMenuItem newDungeon = new JMenuItem("New Dungeon");
    newDungeon.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    JMenuItem oldDungeon = new JMenuItem("Same Dungeon");
    oldDungeon.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    restart.add(newDungeon);
    newDungeon.addActionListener(e -> {
      listener.restart(false);
    });
    restart.add(oldDungeon);
    oldDungeon.addActionListener(e -> {
      listener.restart(true);
    });
    menu.add(restart);
    JMenuItem quitGame = new JMenuItem("Exit");
    quit.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    quitGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    quit.add(quitGame);
    quitGame.addActionListener(e -> {
      listener.quitGame();
    });
    menu.add(quit);
    menu.setVisible(true);
    this.setJMenuBar(menu);
  }


  /**
   * Function that adds the dungeon cells to the panel and splits the frame to two different panels.
   * Display panel for the dungeon and the text panel for the information about the location and the
   * player information.
   *
   * @param d Maze object.
   */
  public void displayLocation(ReadOnlyDungeon d) {
    textContent();

    location = new CellLocation[d.getRows()][d.getCols()];

    for (int i = 0; i < d.getRows(); i++) {
      for (int j = 0; j < d.getCols(); j++) {
        location[i][j] = new CellLocation(d.getLocations()[i][j]);
        Location currentLocation = d.getPlayer().getCurrentLocation();
        if (currentLocation.getRowPos() == i && currentLocation.getColPos() == j) {
          location[i][j].setVisibility();
        }
        displayPanel.add(location[i][j]);
      }
    }
    displayPanel.setLayout(new GridLayout(d.getRows(), d.getCols()));
    splitPane.setLeftComponent(displayPanel);
    splitPane.setRightComponent(textPanel);
    splitPane.setDividerLocation(1000);
    this.setVisible(true);
    this.addKeyListener(this);
  }

  /**
   * Function that displays the location and player information.
   */
  public void textContent() {
    String res = d.getPlayer().getCurrentLocation().getDirectionBooleanMap().toString();
    String treasureText = d.getPlayer().getCurrentLocation().getTreasureMap().toString();
    String arrow = String.valueOf(d.getPlayer().getCurrentLocation().getArrowCount());
    String playerTreasure = d.getPlayer().printDescription().toString();
    String arrowCount = String.valueOf(d.getPlayer().getArrowCountOfPlayer());
    label1.setText("<html><body><br>Location Description : <br> <br> Possible Moves: <br>"
            + res + "<br><br> Treasure at Location:" + treasureText
            + "<br><br> Arrows at the location:" + arrow + "<br><br><br>"
            + "Player Description : <br> <br> "
            + "Treasure the player has: <br>"
            + playerTreasure + "<br><br> Arrow’s that the player has:"
            + arrowCount + "<br></body><html>");
    label1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    textPanel.add(label1);
  }

  @Override
  public void addClickListener(GuiController listener) {
    this.listener = listener;
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {

        super.mouseClicked(e);
        CellLocation a = (CellLocation) e.getSource();
        int colPos = a.getX() / a.getWidth();
        int rowPos = a.getY() / a.getHeight();

        if (d.getPlayer().getCurrentLocation().getDirectionOfLocation(Direction.NORTH) != null) {
          if ((rowPos == d.getPlayer().getCurrentLocation().getRowPos() - 1 && colPos
                  == d.getPlayer().getCurrentLocation().getColPos()) || (rowPos == d.getPlayer()
                  .getCurrentLocation().getRowPos() + (d.getRows() - 1) && colPos
                  == d.getPlayer().getCurrentLocation().getColPos())) {
            int moveValue = listener.moveInView(Direction.NORTH);
            dialogPane(moveValue);
            location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                    .getCurrentLocation().getColPos()].setVisibility();
            textContent();
            displayPanel.repaint();
          }
        }
        if (d.getPlayer().getCurrentLocation().getDirectionOfLocation(Direction.SOUTH) != null) {
          if ((rowPos == d.getPlayer().getCurrentLocation().getRowPos() + 1 && colPos
                  ==  d.getPlayer().getCurrentLocation().getColPos()) || (rowPos == d.getPlayer()
                  .getCurrentLocation().getRowPos() - (d.getRows() - 1) && colPos
                  == d.getPlayer().getCurrentLocation().getColPos())) {
            int moveValue = listener.moveInView(Direction.SOUTH);
            dialogPane(moveValue);
            textContent();
            location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                    .getCurrentLocation().getColPos()].setVisibility();
          }
        }
        if (d.getPlayer().getCurrentLocation().getDirectionOfLocation(Direction.EAST) != null) {
          if ((rowPos == d.getPlayer().getCurrentLocation().getRowPos() && colPos
                  == d.getPlayer().getCurrentLocation().getColPos() + 1) || (rowPos == d.getPlayer()
                  .getCurrentLocation().getRowPos() && colPos
                  == d.getPlayer().getCurrentLocation().getColPos() - (d.getCols() - 1))) {
            int moveValue = listener.moveInView(Direction.EAST);
            dialogPane(moveValue);
            textContent();
            location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                    .getCurrentLocation().getColPos()].setVisibility();
          }
        }
        if (d.getPlayer().getCurrentLocation().getDirectionOfLocation(Direction.WEST) != null) {
          if ((rowPos == d.getPlayer().getCurrentLocation().getRowPos() && colPos
                  == d.getPlayer().getCurrentLocation().getColPos() - 1) || (rowPos == d.getPlayer()
                  .getCurrentLocation().getRowPos() && colPos
                  == d.getPlayer().getCurrentLocation().getColPos() + (d.getCols() - 1))) {
            int moveValue = listener.moveInView(Direction.WEST);
            dialogPane(moveValue);
            textContent();
            location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                    .getCurrentLocation().getColPos()].setVisibility();
          }
        }

      }
    };
    for (int i = 0; i < d.getRows(); i++) {
      for (int j = 0; j < d.getCols(); j++) {
        location[i][j].addMouseListener(clickAdapter);

      }
    }
  }


  private void dialogPane(int val) {
    switch (val) {
      case 2:
        if (d.getPlayer().getCurrentLocation().isPit()) {
          JOptionPane.showMessageDialog(this, "Oooff..Mind your "
                  + "step next time");
        } else if (d.getPlayer().getCurrentLocation().getMonster().getIsAlive()) {
          JOptionPane.showMessageDialog(this, "Can't win against "
                  + "the beast..Try again!");
        }
        break;
      case 3:
        JOptionPane.showMessageDialog(this,
                "Hurray you have reached the end!!");
        break;
      default:
        break;
    }
  }


  private void shootPopUp(int val) {
    switch (val) {
      case 1:
        JOptionPane.showMessageDialog(this, "You hit the monster!");
        break;
      case 2:
        JOptionPane.showMessageDialog(this, "You killed the monster");
        break;
      case 3:
        JOptionPane.showMessageDialog(this,
                "You used up you all your arrows 😞!!");
        break;
      case 4:
        JOptionPane.showMessageDialog(this, "You shot into darkness 😞!!");
        break;
      default:
        break;
    }
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void addKeysListener(KeyListener listener) {
    addKeyListener(listener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // there is no action being performed.
  }


  @Override
  public void keyTyped(KeyEvent e) {
    //There is no key typed.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    Direction shootDirection = null;
    int distance = 0;
    pressedKeys.add(e.getKeyCode());
    if (!pressedKeys.isEmpty()) {
      if (pressedKeys.contains(KeyEvent.VK_S)) {
        if (pressedKeys.size() > 3) {
          pressedKeys.clear();
        } else {
          if (pressedKeys.size() == 3) {
            pressedKeys.remove(KeyEvent.VK_S);
            if (pressedKeys.contains(KeyEvent.VK_UP) || pressedKeys.contains(KeyEvent.VK_DOWN)
                    || pressedKeys.contains(KeyEvent.VK_LEFT)
                    || pressedKeys.contains(KeyEvent.VK_RIGHT)) {
              if (pressedKeys.contains(KeyEvent.VK_UP)) {
                shootDirection = Direction.NORTH;
                pressedKeys.remove(KeyEvent.VK_UP);
              } else if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                shootDirection = Direction.SOUTH;
                pressedKeys.remove(KeyEvent.VK_DOWN);
              } else if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
                shootDirection = Direction.WEST;
                pressedKeys.remove(KeyEvent.VK_LEFT);
              } else if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
                shootDirection = Direction.EAST;
                pressedKeys.remove(KeyEvent.VK_RIGHT);
              }
              for (Iterator<Integer> it = pressedKeys.iterator(); it.hasNext(); ) {
                int check = it.next();
                if (check >= KeyEvent.VK_0 && check <= KeyEvent.VK_9) {
                  distance = check - KeyEvent.VK_0;
                  int value = listener.shootArrow(shootDirection, distance);
                  shootPopUp(value);
                }

              }
            }
            pressedKeys.clear();
          }
        }
      } else {
        int keycode = e.getKeyCode();
        Direction direction = null;
        switch (keycode) {
          case KeyEvent.VK_UP:
            direction = Direction.NORTH;
            break;
          case KeyEvent.VK_DOWN:
            direction = Direction.SOUTH;
            break;
          case KeyEvent.VK_RIGHT:
            direction = Direction.EAST;
            break;
          case KeyEvent.VK_LEFT:
            direction = Direction.WEST;
            break;
          default:
            break;
        }
        location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                .getCurrentLocation().getColPos()].repaint();
        int moveValue = listener.moveInView(direction);
        dialogPane(moveValue);
        textContent();
        location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                .getCurrentLocation().getColPos()].setVisibility();

        if (e.getKeyChar() == 't') {
          listener.collectTreasure(true);
          location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                  .getCurrentLocation().getColPos()].repaint();
        }
        if (e.getKeyChar() == 'a') {
          listener.collectTreasure(false);
          location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                  .getCurrentLocation().getColPos()].repaint();
        }
        location[d.getPlayer().getCurrentLocation().getRowPos()][d.getPlayer()
                .getCurrentLocation().getColPos()].repaint();
        pressedKeys.clear();
      }

    }

    this.refresh();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //No keys released are being checked.
  }


  @Override
  public void makeVisible(boolean visible) {
    setVisible(visible);
  }

  /**
   * This function is used to split the frame into two different panels to display dungeon and the
   * information.
   */
  public void mapSplitPane() {
    GroupLayout layout = new GroupLayout(this.getContentPane());
    this.getContentPane().setLayout(layout);

    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 1450,
                            GroupLayout.PREFERRED_SIZE).addGap(0, 352, Short.MAX_VALUE)));

    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                    .addComponent(splitPane, GroupLayout.PREFERRED_SIZE, this.getHeight(),
                            GroupLayout.PREFERRED_SIZE).addGap(0, 352, Short.MAX_VALUE)));


  }

  /**
   * Used to quit the view and close them.
   */
  public void destroyView() {
    makeVisible(false);
    dispose();
  }
}
