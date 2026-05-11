package model.tanks;
import model.Direction;

public class PlayerTank extends Tank {

   public PlayerTank(int x, int y) {
      super(x, y, 1, 2, Direction.UP);
   }

   @Override
   public void act() {

   }
}