package data;

public class ScoreEntry {

   private final String name;
   private final String date;
   private final int time;
   private final int score;

   public ScoreEntry(String name, String date, int time, int score) {
      this.name = name.replace(",", " ").trim(); // If name contains comma, deletes it
      this.date = date;
      this.time = time;
      this.score = score;
   }

   public String getName() {
      return name;
   }
   public String getDate() {
      return date;
   }
   public int getTime() {
      return time;
   }
   public int getScore() {
      return score;
   }
   
   public String toCsvLine() {
      return name + "," + date + "," + time + "," + score;
   }

   public static ScoreEntry fromCsvLine(String line) {
      String[] parts = line.split(",");
      return new ScoreEntry(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
   }
}