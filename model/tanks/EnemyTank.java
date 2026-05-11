package model.tanks;
import model.Direction;

public abstract class EnemyTank extends Tank {

   public EnemyTank(int x, int y, int health, int speed) {
      super(x, y, health, speed, Direction.DOWN);
   }

   @Override
   public void act(){
      
   }
}