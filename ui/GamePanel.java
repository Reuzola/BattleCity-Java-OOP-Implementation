package ui;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import data.Settings;
import game.Game;
import game.Game.GameState;
import model.Bullet;
import model.Direction;
import model.blocks.*;
import model.powerups.*;
import model.tanks.*;

public class GamePanel extends JPanel {

   private final Game game;

   public GamePanel(Game game, KeyHandler keyHandler){
      this.game = game;
      setPreferredSize(new Dimension(536, 416));
      setBackground(Color.BLACK);
      addKeyListener(keyHandler);
      setFocusable(true);
      requestFocusInWindow(); 
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);          
      
      for (Block block : game.getBlocks()) { // Blocks draw
         BufferedImage img = null;
         if(block instanceof BrickBlock) img = Sprites.BRICK;
         else if(block instanceof SteelBlock) img = Sprites.STEEL;
         else if(block instanceof WaterBlock) img = Sprites.WATER; // Skips base and bush block
         if(img != null) g.drawImage(img, block.getX(), block.getY(), Block.SIZE, Block.SIZE, null);
      }

      for (PowerUp pu : game.getPowerUps()) { // Powerups draw
         BufferedImage img = null;
         if(pu instanceof LifePowerUp) img = Sprites.LIFE;
         else if(pu instanceof StarPowerUp) img = Sprites.STAR;
         else if(pu instanceof BombPowerUp) img = Sprites.BOMB;
         else if(pu instanceof ClockPowerUp) img = Sprites.CLOCK;
         else if(pu instanceof ShovelPowerUp) img = Sprites.SHOVEL;
         else if(pu instanceof ShieldPowerUp) img = Sprites.SHIELD;
         if(img != null) g.drawImage(img, pu.getX(), pu.getY(), PowerUp.SIZE, PowerUp.SIZE, null);
      }

      if(!game.getBaseBlock().isDestroyed()) { // Base block draw if not destroyed
         g.drawImage(Sprites.BASE, game.getBaseBlock().getX(), game.getBaseBlock().getY(), Block.SIZE, Block.SIZE, null);
      }

      PlayerTank pt = game.getPlayerTank(); // Player tank draw
      g.drawImage(getPlayerSprite(pt.getDirection()), pt.getX(), pt.getY(), Tank.SIZE, Tank.SIZE, null);

      for (EnemyTank enemy : game.getEnemyTanks()) { // Enemy tanks draw
         g.drawImage(getEnemySprite(enemy), enemy.getX(), enemy.getY(), Tank.SIZE, Tank.SIZE, null);
      }

      g.setColor(Color.WHITE); // Bullets draw
      for (Bullet bullet : game.getBullets()) {
         g.fillRect(bullet.getX(), bullet.getY(), Bullet.SIZE, Bullet.SIZE);
      }

      for (Block block : game.getBlocks()) { // Bushes drawn last so they cover tanks (hiding mechanic)
         if(block instanceof BushBlock) {
            g.drawImage(Sprites.BUSH, block.getX(), block.getY(), Block.SIZE, Block.SIZE, null);
         }
      }

      if(game.getState() == GameState.GAME_OVER) { // GAME OVER title
         g.setFont(new Font("Arial", Font.BOLD, 40));
         g.setColor(Color.RED);
         g.drawString("GAME OVER", 536/2 - 150, 416/2);
      }

      if(game.getState() == GameState.LEVEL_COMPLETE) { // LEVEL COMPLETE title
         g.setFont(new Font("Arial", Font.BOLD, 40));
         g.setColor(Color.GREEN);
         g.drawString("LEVEL COMPLETE", 536/2 - 220, 416/2);
      }

      if(game.getState() == GameState.PAUSED) { // PAUSED title
         g.setFont(new Font("Arial", Font.BOLD, 40));
         g.setColor(Color.YELLOW);
         g.drawString("PAUSED", 536/2 - 120, 416/2);
      }

      g.setColor(new Color(40, 40, 40)); // HUD background
      g.fillRect(416, 0, 120, 416);

      g.setColor(new Color(100, 100, 100)); // Vertical separator line
      g.fillRect(416, 0, 2, 416);

      int hudX = 430; // Left margin inside HUD column
      int y = 25; // Running y cursor

      // LEVEL
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("LEVEL", hudX, y);
      y += 22;
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 24));
      g.drawString(String.valueOf(game.getActiveLevel().getDifficulty()), hudX + 30, y);
      y += 25;

      // LIVES
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("LIVES", hudX, y);
      y += 22;
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 24));
      g.drawString(String.valueOf(game.getLives()), hudX + 30, y);
      y += 25;

      // SCORE
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("SCORE", hudX, y);
      y += 20;
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 18));
      g.drawString(String.valueOf(game.getScore()), hudX, y);
      y += 25;

      // TIME
      int secs = game.getElapsedSeconds();
      String timeStr = (secs / 60) + ":" + String.format("%02d", secs % 60);
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("TIME", hudX, y);
      y += 20;
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 18));
      g.drawString(timeStr, hudX, y);
      y += 25;

      // ENEMIES LEFT
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("ENEMIES", hudX, y);
      y += 20;
      g.setColor(Color.WHITE);
      g.setFont(new Font("Arial", Font.BOLD, 18));
      g.drawString(String.valueOf(Game.MAX_TOTAL_ENEMIES - game.getEnemiesKilled()), hudX, y);
      y += 25;

      // STARS (icon row)
      g.setColor(Color.LIGHT_GRAY);
      g.setFont(new Font("Arial", Font.PLAIN, 12));
      g.drawString("STARS", hudX, y);
      y += 6;
      int stars = game.getPlayerTank().getStarCount();
      for (int i = 0; i < 3; i++) {
         if(i < stars) {
            g.drawImage(Sprites.STAR, hudX + i * 22, y, 18, 18, null);
         } else {
            g.setColor(new Color(80, 80, 80));
            g.fillRect(hudX + i * 22, y, 18, 18);
         }
      }
      y += 28;

      // Active power-up timers (only shown when active)
      g.setFont(new Font("Arial", Font.BOLD, 11));
      int shieldTicks = game.getPlayerTank().getShieldTicks();
      if(shieldTicks > 0) {
         g.setColor(new Color(100, 200, 255));
         g.drawString("SHIELD " + (shieldTicks / 30 + 1) + "s", hudX, y);
         y += 16;
      }
      if(game.getFreezeTicks() > 0) {
         g.setColor(new Color(150, 220, 255));
         g.drawString("FREEZE " + (game.getFreezeTicks() / 30 + 1) + "s", hudX, y);
         y += 16;
      }
      if(game.getShovelTicks() > 0) {
         g.setColor(new Color(255, 200, 100));
         g.drawString("SHOVEL " + (game.getShovelTicks() / 30 + 1) + "s", hudX, y);
         y += 16;
      }

      if(Settings.showFps) { // Shows fps
         g.setColor(Color.YELLOW);
         g.setFont(new Font("Arial", Font.PLAIN, 10));
         g.drawString("FPS: ~30", hudX, 400);
      }
   }

   private BufferedImage getPlayerSprite(Direction d) { // Helper for determine player tanks direction
      switch(d) {
         case UP:    return Sprites.PLAYER_UP;
         case LEFT:  return Sprites.PLAYER_LEFT;
         case DOWN:  return Sprites.PLAYER_DOWN;
         case RIGHT: return Sprites.PLAYER_RIGHT;
         default:    return Sprites.PLAYER_UP;
      }
   }

   private BufferedImage getEnemySprite(EnemyTank enemy) { // Helper for determine enemy tanks type and direction
      Direction d = enemy.getDirection();
      if(enemy instanceof BasicEnemy) {
         switch(d) {
            case UP: return Sprites.BASIC_UP;
            case LEFT: return Sprites.BASIC_LEFT;
            case DOWN: return Sprites.BASIC_DOWN;
            case RIGHT: return Sprites.BASIC_RIGHT;
         }
      } else if(enemy instanceof FastEnemy) {
         switch(d) {
            case UP: return Sprites.FAST_UP;
            case LEFT: return Sprites.FAST_LEFT;
            case DOWN: return Sprites.FAST_DOWN;
            case RIGHT: return Sprites.FAST_RIGHT;
         }
      } else if(enemy instanceof ArmoredEnemy) {
         switch(d) {
            case UP: return Sprites.ARMORED_UP;
            case LEFT: return Sprites.ARMORED_LEFT;
            case DOWN: return Sprites.ARMORED_DOWN;
            case RIGHT: return Sprites.ARMORED_RIGHT;
         }
      }
      return Sprites.BASIC_UP;
   }
}