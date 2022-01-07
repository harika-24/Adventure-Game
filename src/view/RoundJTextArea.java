package view;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextArea;

/* This code has been taken from :
https://stackoverflow.com/questions/8515601/java-swing-rounded-border-for-jtextfield.
I have improvised the text field code and created the text area code. The above source is the
inspiration for it.
This is used so that my dungeon creation form has rounded text area edges. I have used this created
object of this class type in the StartView class in order to get the round edges.
*/

/**
 * This class is used to create rounded edges for the text areas.It extends the JTextArea and when
 * the object of this type is created it has rounded edges.
 */
public class RoundJTextArea extends JTextArea {
  private Shape shape;

  /**
   * Constructor for the RoundJTextArea class.
   */
  public RoundJTextArea() {
    setOpaque(false);
  }

  protected void paintComponent(Graphics g) {
    g.setColor(getBackground());
    g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
            15, 15);
    super.paintComponent(g);
  }

  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
            15, 15);
  }

  /**
   * This is used to draw the textAreaBox with round edges.
   * @param x rows
   * @param y columns
   * @return the final shape.
   */
  public boolean contains(int x, int y) {
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1,
              15, 15);
    }
    return shape.contains(x, y);
  }
}