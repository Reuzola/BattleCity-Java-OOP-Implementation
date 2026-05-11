package model;
import model.tanks.Tank;

public class Bullet {

   public static final int SIZE = 6;
   protected int x;
   protected int y;
   protected int speed;
   protected Direction direction;
   protected Tank owner;
   protected boolean active;

   public Bullet(int x, int y, int speed, Direction direction, Tank owner){
      this.x = x;
      this.y = y;
      this.speed = speed;
      this.direction = direction;
      this.owner = owner;
      active = true;
   }
   
   public int getX() {
      return x;
   }
   public int getY() {
      return y;
   }
   public Direction getDirection() {
      return direction;
   }
   public Tank getOwner() {
      return owner;
   }
   public boolean isActive() {
      return active;
   }

   public void move(){
      x += direction.getDx() * speed;
      y += direction.getDy() * speed;
   }

   public void deactivate(){
      active = false;
   }
}