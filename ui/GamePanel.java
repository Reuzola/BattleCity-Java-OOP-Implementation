package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import game.Game;
import game.Game.GameState;
import model.Bullet;
import model.Direction;
import model.blocks.*;
import model.powerups.BombPowerUp;
import model.powerups.ClockPowerUp;
import model.powerups.LifePowerUp;
import model.powerups.PowerUp;
import model.powerups.ShieldPowerUp;
import model.powerups.ShovelPowerUp;
import model.powerups.StarPowerUp;
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
         else if(block instanceof WaterBlock) img = Sprites.WATER;
         else if(block instanceof BushBlock)  img = Sprites.BUSH; // Skips base block
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

      g.setColor(new Color(60, 60, 60)); // HUD on the right side(416-536)
      g.fillRect(416, 0, 120, 416);

      g.setFont(new Font("Arial", Font.BOLD, 16)); // Lives title
      g.setColor(Color.WHITE);
      g.drawString("LIVES", 444, 30);

      g.setFont(new Font("Arial", Font.BOLD, 32)); // Lives count
      g.drawString(String.valueOf(game.getLives()), 462, 65);

      g.setFont(new Font("Arial", Font.BOLD, 16)); // Score title
      g.drawString("SCORE", 444, 110);

      g.setFont(new Font("Arial", Font.BOLD, 20)); // Score value
      g.drawString(String.valueOf(game.getScore()), 432, 140);

      g.setFont(new Font("Arial", Font.BOLD, 14)); // Enemies remaining
      g.drawString("ENEMIES", 440, 180);
      g.drawString("LEFT: " + (Game.MAX_TOTAL_ENEMIES - game.getEnemiesKilled()), 430, 200);
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