package game;
import ui.GamePanel;

public class GameLoop implements Runnable{

   private final Game game;
   private final GamePanel panel;
   private boolean running;

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

   @Override
   public void run() {
      while(running) {
         game.update();
         panel.repaint();
         try {
            Thread.sleep(33);
         } catch(InterruptedException e) {
            System.err.println(e.getMessage());
         }
      }
   }
   
}