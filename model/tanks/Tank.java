package model.tanks;
import model.Direction;
import model.blocks.Block;
import java.util.ArrayList;
import game.Level;
import model.Bullet;

public abstract class Tank {

   public static final int BULLET_SPEED = 5;
   public static final int SIZE = 24; // Size of tanks
   
   protected int x; // Coordinates
   protected int y;
   protected int health;
   protected int speed;
   protected Direction direction;

   public Tank(int x, int y, int health, int speed, Direction direction) {
      this.x = x;
      this.y = y;
      this.health = health;
      this.speed = speed;
      this.direction = direction;
   }
   
   public int getX() { // Getters
      return x;
   }
   public int getY() {
      return y;
   }
   public int getHealth() {
      return health;
   }
   public int getSpeed() {
      return speed;
   }
   public Direction getDirection() {
      return direction;
   }
   public void setDirection(Direction direction) {
      this.direction = direction;
   }

   public void move(ArrayList<Block> blocks) {
      int newX = x + direction.getDx() * speed; // current + delta
      int newY = y + direction.getDy() * speed;
      if(newX < 0 || newX > Level.GRID_WIDTH * Block.SIZE - SIZE || // Checking if tank collides edges
         newY < 0 || newY > Level.GRID_HEIGHT * Block.SIZE - SIZE) return;

      for (Block b : blocks) {
         if(b.blocksTanks()) {
            if(newX < b.getX() + Block.SIZE &&  // Checking each block coordinates and compares
               newX + SIZE > b.getX() &&        // tank coordinates to check collision
               newY < b.getY() + Block.SIZE &&
               newY + SIZE > b.getY()) return;
         }
      }

      x = newX; // If not collide (passes all return statements) update x and y
      y = newY;
   }

   public void takeDamage(int damage){
      health -= damage;
   }

   public boolean isDead() {
      return health <= 0;
   }

   public Bullet fire(){ // Fires Bullet by declaring new Bullet object
      int centerX = x + SIZE / 2 - Bullet.SIZE / 2;
      int centerY = y + SIZE / 2 - Bullet.SIZE / 2;
      int bulletX = centerX + direction.getDx() * (SIZE / 2);
      int bulletY = centerY + direction.getDy() * (SIZE / 2);

      return new Bullet(bulletX, bulletY, BULLET_SPEED, direction, this);
   }

   public abstract Bullet act(ArrayList<Block> blocks);
}