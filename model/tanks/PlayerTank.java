package model.tanks;
import model.Direction;
import ui.KeyHandler;

public class PlayerTank extends Tank {

   private KeyHandler keyHandler;

   public PlayerTank(int x, int y, KeyHandler keyHandler) {
      super(x, y, 1, 2, Direction.UP);
      this.keyHandler = keyHandler;
   }

   @Override
   public void act() {
      if(keyHandler.upPressed) {
         setDirection(Direction.UP);
         move();
      } else if(keyHandler.downPressed) {
         setDirection(Direction.DOWN);
         move();
      } else if(keyHandler.leftPressed) {
         setDirection(Direction.LEFT);
         move();
      } else if(keyHandler.rightPressed) {
         setDirection(Direction.RIGHT);
         move();
      }
   }
}