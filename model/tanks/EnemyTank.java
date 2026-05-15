package model.tanks;
import java.util.ArrayList;
import model.Direction;
import model.blocks.Block;

public abstract class EnemyTank extends Tank {

   public EnemyTank(int x, int y, int health, int speed) {
      super(x, y, health, speed, Direction.DOWN);
   }

   @Override
   public void act(ArrayList<Block> blocks){
      
   }
}