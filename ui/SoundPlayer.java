package ui;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import data.Settings;

public class SoundPlayer { // Plays WAV sound effects, no-op when sound is disabled

   public static void play(String fileName) {
      if(!Settings.soundEnabled) return;

      try {
         File f = new File("assets/sounds/" + fileName);
         AudioInputStream stream = AudioSystem.getAudioInputStream(f);
         DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat()); // Format-aware line request
         Clip clip = (Clip) AudioSystem.getLine(info);
         clip.open(stream);
         clip.start();
      } catch(UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
         System.err.println("Sound playback failed for " + fileName + ": " + e.getMessage());
      }
   }

   private SoundPlayer() {} // Static utility, no instances
}