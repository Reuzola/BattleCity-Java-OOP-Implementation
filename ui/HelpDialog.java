package ui;
import java.awt.*;
import javax.swing.*;

public class HelpDialog extends JDialog {

   public HelpDialog(JFrame owner) {
      super(owner, "Help", true);
      setSize(460, 480);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      JTextArea area = new JTextArea();
      area.setEditable(false);
      area.setFont(new Font("Arial", Font.PLAIN, 13));
      area.setBackground(Color.BLACK);
      area.setForeground(Color.WHITE);
      area.setMargin(new Insets(15, 15, 15, 15));

      String content =
         "HOW TO PLAY\n" +
         "===========\n" +
         "\n" +
         "CONTROLS\n" +
         "  W / A / S / D  -  Move tank\n" +
         "  SPACE          -  Fire\n" +
         "\n" +
         "OBJECTIVE\n" +
         "  Destroy all 20 enemy tanks to clear the level.\n" +
         "  Protect your base (eagle) at the bottom center.\n" +
         "\n" +
         "GAME OVER WHEN\n" +
         "  Your base is destroyed, or\n" +
         "  You lose all 3 lives.\n" +
         "\n" +
         "ENEMIES\n" +
         "  Basic    (green)  -  1 hit  -  100 pts\n" +
         "  Fast     (red)    -  1 hit  -  200 pts\n" +
         "  Armored  (white)   -  3 hit  -  400 pts\n" +
         "\n" +
         "TERRAIN\n" +
         "  Brick  -  Destroyable by any bullet\n" +
         "  Steel  -  Only destroyable with 3+ star power-ups\n" +
         "  Water  -  Blocks tanks, bullets pass through\n" +
         "  Bush   -  Hides tanks underneath\n" +
         "\n" +
         "POWER-UPS\n" +
         "  Tank    -  Extra life\n" +
         "  Star    -  Upgrade weapon (1:fast, 2:double, 3:steel)\n" +
         "  Bomb    -  Destroy all visible enemies\n" +
         "  Clock   -  Freeze enemies temporarily\n" +
         "  Shovel  -  Steel walls around base temporarily\n" +
         "  Shield  -  Temporary invulnerability\n";

      area.setText(content);
      add(new JScrollPane(area), BorderLayout.CENTER);

      JButton closeBtn = new JButton("Close");
      closeBtn.addActionListener(e -> dispose());
      JPanel bottom = new JPanel();
      bottom.add(closeBtn);
      add(bottom, BorderLayout.SOUTH);
   }
}