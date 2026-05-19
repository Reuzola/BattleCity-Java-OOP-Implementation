package ui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import data.*;

public class HighScorePanel extends JDialog {

   public HighScorePanel(JFrame owner) {
      super(owner, "High Scores", true); // true = modal
      setSize(420, 360);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      JTextArea area = new JTextArea();
      area.setEditable(false);
      area.setFont(new Font("Monospaced", Font.PLAIN, 14));
      area.setBackground(Color.BLACK);
      area.setForeground(Color.WHITE);
      area.setMargin(new Insets(10, 10, 10, 10));

      ArrayList<ScoreEntry> scores = ScoreManager.loadScores();

      StringBuilder sb = new StringBuilder();
      sb.append(String.format("%-4s %-12s %-8s %-6s %-16s%n", "#", "NAME", "SCORE", "TIME", "DATE"));
      sb.append("--------------------------------------------------\n");

      if(scores.isEmpty()) sb.append("\n           No scores yet.\n");
      else {
         for(int i = 0; i < scores.size(); i++) {
            ScoreEntry s = scores.get(i);
            sb.append(String.format("%-4d %-12s %-8d %-6s %-16s%n",
               i + 1,
               truncate(s.getName(), 12),
               s.getScore(),
               formatTime(s.getTime()),
               s.getDate()
            ));
         }
      }

      area.setText(sb.toString());
      add(new JScrollPane(area), BorderLayout.CENTER);

      JButton closeBtn = new JButton("Close");
      closeBtn.addActionListener(e -> dispose());
      JPanel bottom = new JPanel();
      bottom.add(closeBtn);
      add(bottom, BorderLayout.SOUTH);
   }

   private String formatTime(int seconds) { // Converts seconds to "M:SS" format
      return (seconds / 60) + ":" + String.format("%02d", seconds % 60);
   }

   private String truncate(String s, int max) { // Truncates string if too long
      if(s.length() <= max) return s;
      return s.substring(0, max - 1) + ".";
   }
}