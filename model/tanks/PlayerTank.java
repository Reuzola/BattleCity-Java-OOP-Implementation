package model.tanks;
import java.util.ArrayList;
import model.Bullet;
import model.Direction;
import model.blocks.Block;
import ui.KeyHandler;

public class PlayerTank extends Tank {

   private static final int FIRE_COOLDOWN_FRAMES = 15; // Equals half second
   private KeyHandler keyHandler;
   private int fireCooldown; 

   public PlayerTank(int x, int y, KeyHandler keyHandler) {
      super(x, y, 1, 2, Direction.UP); // Initial state of player tank
      this.keyHandler = keyHandler;
      fireCooldown = 0;
   }

   @Override // Move method for player tank which is controlled by keyboard with KeyHandler class
   public Bullet act(ArrayList<Block> blocks) {
      if(fireCooldown > 0) fireCooldown--;

      if(keyHandler.upPressed) {
         setDirection(Direction.UP);
         move(blocks);
      } else if(keyHandler.downPressed) {
         setDirection(Direction.DOWN);
         move(blocks);
      } else if(keyHandler.leftPressed) {
         setDirection(Direction.LEFT);
         move(blocks);
      } else if(keyHandler.rightPressed) {
         setDirection(Direction.RIGHT);
         move(blocks);
      }
      if(keyHandler.firePressed && fireCooldown == 0) { // Waits for cooldown to shoot again
         fireCooldown = FIRE_COOLDOWN_FRAMES;
         return fire();
      } else return null;
   }
}