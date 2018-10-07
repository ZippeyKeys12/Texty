package zaindrew.graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import zaindrew.Player;

public class TextyGUI extends JFrame {

  private final Player player;
  private final JPanel mainScreen;
  private Map map;

  public TextyGUI(final Player player) {
    this.player = player;
    //Main Window
    setTitle("TextyGUI");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(640, 640);
    setResizable(false);
    setVisible(true);
    mainScreen = new MainPanel() {
      {
        this.setFocusable(true);
        this.setBackground(Color.WHITE);
        this.setSize(640, 640);
      }
    };
    final GroupLayout gameScreenLayout = new GroupLayout(mainScreen);
    mainScreen.setLayout(gameScreenLayout);
    getContentPane().add(mainScreen);
  }


  public static void main(final String[] args) {
    //Startup Options
    final JComboBox<String> classes = new JComboBox<String>() {
      {
        this.setModel(new DefaultComboBoxModel<>(new String[]{"Warrior", "Mage", "Spellsword"}));
      }
    };
    final JDialog startPopUp = new JDialog() {
      {
        this.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(final WindowEvent e) {
            System.exit(0);
          }
        });
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Options");
        this.setAlwaysOnTop(true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setSize(new Dimension(212, 520));
        this.setResizable(false);
        this.setVisible(true);
      }
    };
    final JLabel classLbl = new JLabel("Class: ");
    final JLabel gameOptions = new JLabel("Options");
    final JTextField playerName = new JTextField("Player Name");
    final JButton donDone = new JButton("Done");
    donDone.setText("Done");
    final GroupLayout startPopUpLayout = new GroupLayout(startPopUp.getContentPane());
    startPopUp.getContentPane().setLayout(startPopUpLayout);
    startPopUpLayout.setHorizontalGroup(
        startPopUpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(startPopUpLayout.createSequentialGroup()
                .addGroup(startPopUpLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(startPopUpLayout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(startPopUpLayout.createSequentialGroup()
                            .addGap(73, 73, 73)
                            .addComponent(gameOptions))
                        .addGroup(startPopUpLayout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addComponent(playerName, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(startPopUpLayout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(classLbl)
                        .addGap(19, 19, 19)
                        .addComponent(classes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, startPopUpLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(donDone)
                .addGap(76, 76, 76))
    );
    startPopUpLayout.setVerticalGroup(
        startPopUpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(startPopUpLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(gameOptions, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(startPopUpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(classes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(classLbl)
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(donDone)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    donDone.addActionListener(e -> {
      final double[] stats, magicStats;
      switch (classes.getSelectedItem().toString().toLowerCase()) {
        case "warrior":
          stats = new double[]{200, 100, 0, 25, 0, 15, 0};
          magicStats = new double[]{0, 0, 0, 0, 0, 0};
          break;
        case "mage":
          stats = new double[]{200, 0, 100, 0, 25, 0, 15};
          magicStats = new double[]{18.75, 18.75, 18.75, 11.25, 11.25, 11.25};
          break;
        case "spellsword":
          stats = new double[]{150, 75, 75, 12.5, 12.5, 7.5, 7.5};
          magicStats = new double[]{9.375, 9.375, 9.375, 5.625, 5.625, 5.625};
          break;
        default:
          stats = new double[]{};
          magicStats = new double[]{};
      }
      startPopUp.setVisible(false);
      startPopUp.setEnabled(false);
      SwingUtilities.invokeLater(() -> new TextyGUI(new Player(playerName
          .getText(), 0, 0, stats, magicStats)));
    });
  }

  private class MainPanel extends JPanel {

    @Override
    public void paintComponent(final Graphics g) {
      final Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      super.paintComponent(g2);
    }
  }
}
