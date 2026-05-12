package model.powerups;
import model.tanks.PlayerTank;

public abstract class PowerUp {
   
   public static final int SIZE = 24;
   protected int x;
   protected int y;
   protected boolean collected;
   
   public PowerUp(int x, int y) {
      this.x = x;
      this.y = y;
      collected = false;
   }
   
   public int getX() {
      return x;
   }
   public int getY() {
      return y;
   }
   public boolean isCollected() {
      return collected;
   }

   public void collect(){
      collected = true;
   }

   public abstract void applyEffect(PlayerTank player);
}
