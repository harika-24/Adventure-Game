package view;

import controller.GuiController;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


/**
 * The view that appears as soon as the driver is run. Welcome screen and then when the player
 * chooses the game settings a form is displayed which takes in the dungeon inputs and returns the
 * values to the GUI Controller.
 */
public class StartView extends JFrame implements IStarterView {

  private final JFrame frame;
  private final JFrame formFrame;
  private JButton reset;
  private JButton submit;
  private JTextField rows;
  private JTextField columns;
  private JTextField wrapLabel;
  private JTextField incLabel;
  private JTextField treasureLabel;
  private JTextField monsterLabel;
  private JTextField pitLabel;
  private JTextArea displayMessage;

  /**
   * Constructor for the StartView Class.
   */
  public StartView() {
    frame = new JFrame("GUI Game");
    frame.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    formFrame = new JFrame("Dungeon Creation");
    formFrame.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    displayMessage = new RoundJTextArea();
    menuBar();
    frame.setVisible(true);
    frame.setSize(1000, 1000);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    try {
      imageForm();
    } catch (IOException e) {
      e.printStackTrace();
    }
    formContent();
  }

  /**
   * Displays the text on the welcome screen.
   */
  public void formContent() {
    Container a = frame.getContentPane();
    a.setBackground(new Color(204, 153, 102));
    a.setLayout(null);
    JLabel mainHeading = new JLabel("Welcome to Adventure World!!");
    mainHeading.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
    mainHeading.setBounds(250, 60, 500, 30);
    JLabel text = new JLabel("Click on the game settings to start.");
    text.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
    text.setBounds(320, 120, 400, 30);
    a.add(mainHeading);
    a.add(text);
  }

  /**
   * Displays the image on the welcome screen.
   */
  public void imageForm() throws IOException {
    BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass()
            .getResource("/images/maze.png")));
    JLabel imageLabel = new JLabel(new ImageIcon(image));
    imageLabel.setBounds(225, 150, 500, 500);
    frame.add(imageLabel);
  }

  /**
   * Displays the menu bar on the welcome screen.
   */
  public void menuBar() {
    JMenuBar mb = new JMenuBar();
    JMenu gameSettings = new JMenu("Game Settings");
    gameSettings.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    JMenuItem setParams = new JMenuItem("Enter Parameters");
    setParams.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    gameSettings.add(setParams);
    setParams.addActionListener(e -> {
      formFrame.setVisible(true);
      formFrame.setSize(1000, 1000);
      formFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    });
    dungeonCreationForm();
    mb.add(gameSettings);
    JMenu quit = new JMenu("Quit");
    quit.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    JMenuItem quitGame = new JMenuItem("Exit");
    quitGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
    quit.add(quitGame);
    quitGame.addActionListener(e -> {
      destroyView();
    });
    mb.add(quit);
    frame.setJMenuBar(mb);
  }

  /**
   * The function that creates the form for creating the dungeon.
   */
  public void dungeonCreationForm() {
    Container c = formFrame.getContentPane();
    c.setBackground(new Color(204, 153, 102));
    c.setLayout(null);
    JLabel heading = new JLabel("Create your Dungeon!");
    heading.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
    heading.setBounds(200, 20, 400, 30);
    c.add(heading);

    JLabel text = new JLabel("Enter the value in the form to create your dungeon and have fun"
            + " uncovering in the dungeon");
    text.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    text.setBounds(50, 60, 800, 30);
    c.add(text);

    JLabel rowLabel = new JLabel("Enter Rows:");
    rowLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    rowLabel.setBounds(50, 120, 150, 30);
    c.add(rowLabel);

    rows = new RoundJTextField(15);
    rows.setBounds(400, 120, 100, 30);
    c.add(rows);

    JLabel columnLabel = new JLabel("Enter Columns:");
    columnLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    columnLabel.setBounds(50, 160, 150, 30);
    c.add(columnLabel);

    columns = new RoundJTextField(20);
    columns.setBounds(400, 160, 100, 30);
    c.add(columns);

    JLabel wrapping = new JLabel("Do you want to wrap? (Y/N)");
    wrapping.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    wrapping.setBounds(50, 200, 200, 30);
    c.add(wrapping);

    wrapLabel = new RoundJTextField(25);
    wrapLabel.setBounds(400, 200, 100, 30);
    c.add(wrapLabel);

    JLabel interconnectivity = new JLabel("Enter Interconnectivity:");
    interconnectivity.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    interconnectivity.setBounds(50, 240, 200, 30);
    c.add(interconnectivity);

    incLabel = new RoundJTextField(30);
    incLabel.setBounds(400, 240, 100, 30);
    c.add(incLabel);

    JLabel treasureCoverage = new JLabel("Enter Treasure Coverage [0.2,0.5..1]:");
    treasureCoverage.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    treasureCoverage.setBounds(50, 280, 300, 30);
    c.add(treasureCoverage);

    treasureLabel = new RoundJTextField(30);
    treasureLabel.setBounds(400, 280, 100, 30);
    c.add(treasureLabel);

    JLabel noOfMonsters = new JLabel("Enter number of Monsters:");
    noOfMonsters.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    noOfMonsters.setBounds(50, 320, 200, 30);
    c.add(noOfMonsters);

    monsterLabel = new RoundJTextField(30);
    monsterLabel.setBounds(400, 320, 100, 30);
    c.add(monsterLabel);

    JLabel noOfPits = new JLabel("Enter number of Pits:");
    noOfPits.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    noOfPits.setBounds(50, 360, 200, 30);
    c.add(noOfPits);

    pitLabel = new RoundJTextField(30);
    pitLabel.setBounds(400, 360, 100, 30);
    c.add(pitLabel);

    submit = new JButton("Submit");
    submit.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    submit.setBounds(100, 410, 150, 50);
    c.add(submit);
    reset = new JButton("Reset");
    reset.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
    reset.setBounds(300, 410, 150, 50);
    c.add(reset);


    displayMessage.setBounds(50, 490, 450, 100);
    c.add((displayMessage));
    formFrame.setVisible(true);


  }


  @Override
  public void addClickListener(GuiController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        JButton ch = (JButton) e.getSource();
        if (ch.getText().equals("Reset")) {
          rows.setText(null);
          columns.setText(null);
          wrapLabel.setText(null);
          incLabel.setText(null);
          treasureLabel.setText(null);
          monsterLabel.setText(null);
          pitLabel.setText(null);
        }
        else {
          try {
            int row = Integer.parseInt(rows.getText());
            int column = Integer.parseInt(columns.getText());
            int interconnectivity = Integer.parseInt(incLabel.getText());
            double treasureCoverage = Double.parseDouble(treasureLabel.getText());
            int noOfMonsters = Integer.parseInt(monsterLabel.getText());
            int noOfPits = Integer.parseInt(pitLabel.getText());
            if (row < 0) {
              displayMessage.setText("Enter valid rows");
            }
            if (column < 0) {
              displayMessage.setText("Enter valid columns");
            }
            if (interconnectivity < 0) {
              displayMessage.setText("Enter valid interconnectivity");
            } else {
              displayMessage.setText("The dungeon you created has " + row + " rows," + column
                      + " columns," + "\n with interconnectivity " + interconnectivity
                      + " which has treasure coverage of " + treasureLabel.getText()
                      + " \n and has " + monsterLabel.getText() + " monsters"
                      + "and" + pitLabel.getText() + "pits.");
              displayMessage.setWrapStyleWord(true);
            }
            System.out.println(listener + "1");
            if (wrapLabel.getText().equals("n") || wrapLabel.getText().equals("N")) {
              listener.createDungeon(row, column, false, interconnectivity, treasureCoverage,
                      noOfMonsters, noOfPits);
            } else {
              listener.createDungeon(row, column, true, interconnectivity,
                      treasureCoverage, noOfMonsters, noOfPits);

            }


          } catch (NumberFormatException exception) {
            displayMessage.setText("Your inputs are not valid. Please enter integers only!");

          }
        }
      }
    };
    submit.addMouseListener(clickAdapter);
    reset.addMouseListener(clickAdapter);
  }

  @Override
  public void refresh() {
    this.repaint();
  }


  @Override
  public void destroyView() {
    makeVisible(false);
    dispose();
  }


  @Override
  public void makeVisible(boolean visible) {
    frame.setVisible(visible);
    formFrame.setVisible(visible);
  }
}
