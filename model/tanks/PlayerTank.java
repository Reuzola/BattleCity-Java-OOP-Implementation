package model.tanks;
import java.util.ArrayList;
import model.Bullet;
import model.Direction;
import model.blocks.Block;
import ui.KeyHandler;

public class PlayerTank extends Tank {

   private static final int FIRE_COOLDOWN_FRAMES = 15; // Equals half second
   private static final int SHIELD_DURATION = 300; // Equals 10 seconds

   private KeyHandler keyHandler;
   private int fireCooldown; 
   private int starCount;
   private int shieldTicks;

   public PlayerTank(int x, int y, KeyHandler keyHandler) {
      super(x, y, 1, 3, Direction.UP); // Initial state of player tank
      this.keyHandler = keyHandler;
      fireCooldown = 0;
      starCount = 0;
      shieldTicks = 0;
   }

   public int getStarCount() {
      return starCount;
   }
   public int getShieldTicks() {
      return shieldTicks;
   }
   public boolean hasShield() {
      return shieldTicks > 0;
   }
   public void addStar() { // Caps at 3 because higher tiers have no defined behavior
      if(starCount < 3) starCount++;
   }
   public void activateShield() {
      shieldTicks = SHIELD_DURATION;
   }

   @Override // Move method for player tank which is controlled by keyboard with KeyHandler class
   public Bullet act(ArrayList<Block> blocks) {
      if(fireCooldown > 0) fireCooldown--;
      if(shieldTicks > 0) shieldTicks--;

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
         ui.SoundPlayer.play("fire.wav");
         return fire();
      } else return null;
   }

   @Override
   public void takeDamage(int damage) {
      if(shieldTicks > 0) return; // Shield absorbs damage
      super.takeDamage(damage);
   }

   @Override
   public Bullet fire() {
      if(starCount >= 1) { // Star tier 1+: faster bullet
         int centerX = x + SIZE / 2 - Bullet.SIZE / 2;
         int centerY = y + SIZE / 2 - Bullet.SIZE / 2;
         int bulletX = centerX + direction.getDx() * (SIZE / 2);
         int bulletY = centerY + direction.getDy() * (SIZE / 2);

         return new Bullet(bulletX, bulletY, BULLET_SPEED + 3, direction, this);
      } else return super.fire();
   }

   public Bullet fireSecond() { // Seconds bullet, 12px behind spawn direction
      int centerX = x + SIZE / 2 - Bullet.SIZE / 2;
      int centerY = y + SIZE / 2 - Bullet.SIZE / 2;
      int bulletSpeed = (starCount >= 1) ? BULLET_SPEED + 3 : BULLET_SPEED;
      return new Bullet(centerX, centerY, bulletSpeed, direction, this);
   }
}