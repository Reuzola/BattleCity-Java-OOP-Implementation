package ui;
import java.awt.*;
import javax.swing.*;
import data.Settings;

public class OptionsDialog extends JDialog {

   public OptionsDialog(JFrame owner) {
      super(owner, "Options", true);
      setSize(320, 240);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      JPanel form = new JPanel(); // Settings form, grid layout: label + control per row
      form.setLayout(new GridLayout(0, 2, 8, 12));
      form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

      JCheckBox soundBox = new JCheckBox("", Settings.soundEnabled);
      form.add(new JLabel("Sound:"));
      form.add(soundBox);

      JCheckBox fpsBox = new JCheckBox("", Settings.showFps);
      form.add(new JLabel("Show FPS:"));
      form.add(fpsBox);

      JSpinner livesSpinner = new JSpinner(new SpinnerNumberModel(Settings.startingLives, 1, 5, 1));
      form.add(new JLabel("Starting Lives:"));
      form.add(livesSpinner);

      add(form, BorderLayout.CENTER);

      JPanel bottom = new JPanel();
      JButton applyBtn = new JButton("Apply");
      applyBtn.addActionListener(e -> {
         Settings.soundEnabled = soundBox.isSelected();
         Settings.showFps = fpsBox.isSelected();
         Settings.startingLives = (Integer) livesSpinner.getValue();
         dispose();
      });
      JButton cancelBtn = new JButton("Cancel");
      cancelBtn.addActionListener(e -> dispose()); // Discard changes
      bottom.add(applyBtn);
      bottom.add(cancelBtn);
      add(bottom, BorderLayout.SOUTH);
   }
}