package model.tanks;
import java.util.ArrayList;
import model.Bullet;
import model.Direction;
import model.blocks.Block;

public abstract class EnemyTank extends Tank {

   public EnemyTank(int x, int y, int health, int speed) {
      super(x, y, health, speed, Direction.DOWN);
   }

   @Override
   public Bullet act(ArrayList<Block> blocks){ // Basic enemy tank AI implementation
      boolean isMoved = move(blocks); // isMoved stores the information that tank is moved or not

      if(!isMoved) { // If enemy tank cannot move, Immediately turns randomly
         Direction[] d = Direction.values();
         int randomIndex = (int)(Math.random() * d.length);
         setDirection(d[randomIndex]);
      }

      if(Math.random() < 0.02) { // 2% chance to turn every tick(~0.6 turn per second)
         Direction[] d = Direction.values();
         int randomIndex = (int)(Math.random() * d.length);
         setDirection(d[randomIndex]);
      }
      return null;
   }
}