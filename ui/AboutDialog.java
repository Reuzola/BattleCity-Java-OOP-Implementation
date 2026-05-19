package ui;
import java.awt.*;
import javax.swing.*;

public class AboutDialog extends JDialog {

   public AboutDialog(JFrame owner) {
      super(owner, "About", true);
      setSize(360, 360);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      JTextArea area = new JTextArea();
      area.setEditable(false);
      area.setFont(new Font("Arial", Font.PLAIN, 14));
      area.setBackground(Color.BLACK);
      area.setForeground(Color.WHITE);
      area.setMargin(new Insets(15, 15, 15, 15));

      String content =
         "BATTLE CITY\n" +
         "\n" +
         "Developer:\n" +
         "\n" +
         "Name:     Ali\n" +
         "Surname:  Özüer\n" +
         "School#:  20240702046\n" +
         "Email:    aliozuer@gmail.com\n";

      area.setText(content);
      add(area, BorderLayout.CENTER);

      JButton closeBtn = new JButton("Close");
      closeBtn.addActionListener(e -> dispose());
      JPanel bottom = new JPanel();
      bottom.add(closeBtn);
      add(bottom, BorderLayout.SOUTH);
   }
}