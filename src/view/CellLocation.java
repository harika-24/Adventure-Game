package view;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze.Direction;
import maze.Location;
import maze.Smell;
import maze.Treasure;

/**
 * This class has been created so that my display panel in the MenuView can be populated with the
 * images of caves, treasure, monsters, arrows present in the dungeon.
 */
public class CellLocation extends JPanel {

  private final Location location;
  private BufferedImage cellImage;
  private boolean visibility;

  /**
   * Constructor of the CellLocation class that accepts a particular location in the dungeon.
   * @param location Location in the dungeon.
   */
  public CellLocation(Location location) {
    this.location = location;
    visibility = false;
  }

  protected void checkLocation() throws IOException {
    String imagePath = "";
    if (location.getDirectionOfLocation(Direction.NORTH) != null) {
      imagePath += "N";
    }
    if (location.getDirectionOfLocation(Direction.SOUTH) != null) {
      imagePath += "S";
    }
    if (location.getDirectionOfLocation(Direction.EAST) != null) {
      imagePath += "E";
    }
    if (location.getDirectionOfLocation(Direction.WEST) != null) {
      imagePath += "W";
    }

    cellImage = ImageIO.read(getClass().getResourceAsStream("/images/" + imagePath + ".png"));


  }

  protected void checkArrow() throws IOException {
    if (location.getArrowCount() > 0) {
      cellImage = overlay(cellImage, "/images/arrow-white.png", 15,20);
    }
  }

  protected void checkRuby() throws IOException {
    int size = location.getTreasureAtLocation().size();
    for (int i = 0; i < size; i++) {
      if (location.getTreasureAtLocation().get(i).equals(Treasure.RUBY)) {
        cellImage = overlay(cellImage, "/images/ruby_1.png", 20,10);
      }
    }
  }

  protected void checkPlayer() throws IOException {
    if (location.hasPlayer()) {
      cellImage = overlay(cellImage, "/images/player.png",30,25);
    }
  }

  protected void checkDiamond() throws IOException {
    int size = location.getTreasureAtLocation().size();
    for (int i = 0; i < size; i++) {
      if (location.getTreasureAtLocation().get(i).equals(Treasure.DIAMOND)) {
        cellImage = overlay(cellImage, "/images/diamond_1.png", 30,10);
      }
    }
  }

  protected void checkEmerald() throws IOException {
    int size = location.getTreasureAtLocation().size();
    for (int i = 0; i < size; i++) {
      if (location.getTreasureAtLocation().get(i).equals(Treasure.SAPPHIRE)) {
        cellImage = overlay(cellImage, "/images/emerald_1.png", 40,10);
      }
    }
  }

  protected void checkMonster() throws IOException {
    if (location.getMonster() != null && location.getMonster().getIsAlive()) {
      cellImage = overlay(cellImage, "/images/otyugh.png", 25,27);
    }
  }

  protected void checkSmell() throws IOException {

    if (location.checkSmellStrength().equals(Smell.WEAK)) {
      cellImage = overlay(cellImage, "/images/weak.png", 25,27);
    }

    if (location.checkSmellStrength().equals(Smell.STRONG)) {
      cellImage = overlay(cellImage, "/images/strong.png", 25, 27);
    }
  }

  protected void checkPits() throws IOException {
    if (location.isPit()) {
      cellImage = overlay(cellImage, "/images/pit.png",30,30);
    }
  }

  private BufferedImage overlay(BufferedImage starting, String fPath, int x, int y)
          throws IOException {
    BufferedImage overlay = ImageIO.read(getClass().getResourceAsStream(fPath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    g.drawImage(overlay, x, y, null);
    return combined;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image image = null;
    try {
      checkLocation();
      checkPlayer();
      checkSmell();
      checkPits();
      checkRuby();
      checkDiamond();
      checkEmerald();
      checkArrow();
      checkMonster();
      image = cellImage.getScaledInstance(this.getWidth(), this.getHeight(),
            java.awt.Image.SCALE_SMOOTH);

    } catch (IOException e) {
      e.printStackTrace();
    }
    if (image != null) {
      if (visibility) {
        g.drawImage(image, 0, 0, this);
      }
    }

  }

  /**
   * Sets the visibility of the panels.
   */
  public void setVisibility() {
    this.visibility = true;
  }


}
