package data;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {

   private static final String FILE_PATH = "scores.csv"; // Constant file path(root) for scores.csv
   private static final int TOP_N = 10;

   public static ArrayList<ScoreEntry> loadScores() { // Loads scores from scores.csv and returns in a arraylist
      ArrayList<ScoreEntry> list = new ArrayList<>();

      try { // Try blocks for checked exceptions
         BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
         String line;
         while((line = br.readLine()) != null) {
            try {
               list.add(ScoreEntry.fromCsvLine(line));
            } catch(Exception e) {
               System.err.println("Skipping malformed line: " + line);
            }
         }
         br.close();
      } catch(FileNotFoundException e) {
         System.err.println("Score file not found, starting fresh.");
      } catch(IOException e) {
         System.err.println("Error reading score file: " + e.getMessage());
      }

      Collections.sort(list, (a, b) -> b.getScore() - a.getScore());
      if(list.size() > TOP_N) list = new ArrayList<>(list.subList(0, TOP_N));
      return list;
   }

   public static void saveScore(ScoreEntry entry) { // Saves user score to scores.csv file
      try { // Try block for checked exception
         BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true)); // True means append mode
         bw.write(entry.toCsvLine());
         bw.newLine();
         bw.close();
      } catch(IOException e) {
         System.err.println("Error saving score: " + e.getMessage());
      }
   }
}