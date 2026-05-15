package model.tanks;
import java.util.ArrayList;
import model.Direction;
import model.blocks.Block;
import ui.KeyHandler;

public class PlayerTank extends Tank {

   private KeyHandler keyHandler;

   public PlayerTank(int x, int y, KeyHandler keyHandler) {
      super(x, y, 1, 3, Direction.UP); // Initial state of player tank
      this.keyHandler = keyHandler;
   }

   @Override // Move method for player tank which is controlled by keyboard with KeyHandler class
   public void act(ArrayList<Block> blocks) {
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
   }
}