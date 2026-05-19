package game;
import javax.swing.JOptionPane;
import data.ScoreEntry;
import data.ScoreManager;
import ui.GamePanel;

public class GameLoop implements Runnable{

   private final Game game;
   private final GamePanel panel;
   private boolean running;
   private boolean gameOverHandled;
   private int levelCompleteTimer;

   public GameLoop(Game game, GamePanel panel) {
      this.game = game;
      this.panel = panel;
   }

   public void start() {
      running = true;
      Thread thread = new Thread(this);
      thread.start();
   }

   public void stop() { running = false; }

   private void handleGameOver() { // Enter user name to append user score to csv file when game over
      String name = JOptionPane.showInputDialog(
         panel,
         "Game Over!\nScore: " + game.getScore() + "\nTime: " + game.getElapsedSeconds() + "s\n\nEnter your name:",
         "Game Over",
         JOptionPane.PLAIN_MESSAGE
      );
      if(name == null || name.trim().isEmpty()) name = "Anonymous";

      String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());
      ScoreManager.saveScore(new ScoreEntry(name, date, game.getElapsedSeconds(), game.getScore()));
   }

   @Override
   public void run() {
      while(running) {
         long startTime = System.currentTimeMillis();

         game.update();
         panel.repaint();

         if(game.getState() == Game.GameState.GAME_OVER && !gameOverHandled) {
            gameOverHandled = true;
            handleGameOver();
         }

         if(game.getState() == Game.GameState.LEVEL_COMPLETE) {
            levelCompleteTimer++;
            if(levelCompleteTimer >= 90) { // Waits 3 seconds to advence to next level
               levelCompleteTimer = 0;
               game.advenceToNextLevel();
            }
         } else {
            levelCompleteTimer = 0; // Reset when not in LEVEL_COMPLETE
         }

         long sleepTime = 33 - (System.currentTimeMillis() - startTime);
         if(sleepTime > 0) {
            try {
               Thread.sleep(sleepTime);
            } catch(InterruptedException e) {
               System.err.println(e.getMessage());
            }
         }
      }
   }
   
}