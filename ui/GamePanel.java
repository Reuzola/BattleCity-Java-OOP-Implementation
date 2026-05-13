package ui;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;
import game.Game;
import model.Bullet;
import model.Direction;
import model.blocks.*;
import model.tanks.*;

public class GamePanel extends JPanel {

   private final Game game;

   public GamePanel(Game game){
      this.game = game;

      setPreferredSize(new Dimension(536, 416));
      setBackground(Color.BLACK);
   }

   @Override
   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      
      for (Block block : game.getBlocks()) {
         if(block instanceof BrickBlock) g.setColor(Color.ORANGE);
         else if(block instanceof SteelBlock) g.setColor(Color.LIGHT_GRAY);
         else if(block instanceof WaterBlock) g.setColor(Color.BLUE);
         else if(block instanceof BushBlock) g.setColor(Color.GREEN);
         g.fillRect(block.getX(), block.getY(), Block.SIZE, Block.SIZE);
      }

      g.setColor(Color.WHITE);
      g.fillRect(game.getBaseBlock().getX(), game.getBaseBlock().getY(), Block.SIZE, Block.SIZE);

      g.setColor(Color.YELLOW);
      g.fillRect(game.getPlayerTank().getX(), game.getPlayerTank().getY(), Tank.SIZE, Tank.SIZE);
      g.setColor(Color.BLACK);
      int x = game.getPlayerTank().getX() + Tank.SIZE/2 + game.getPlayerTank().getDirection().getDx() * 10 - 2;
      int y = game.getPlayerTank().getY() + Tank.SIZE/2 + game.getPlayerTank().getDirection().getDy() * 10 - 2;
      g.fillRect(x, y, 5, 5);

      for (EnemyTank enemy : game.getEnemyTanks()) {
         if(enemy instanceof BasicEnemy) g.setColor(Color.RED);
         else if(enemy instanceof FastEnemy) g.setColor(Color.PINK);
         else if(enemy instanceof ArmoredEnemy) g.setColor(Color.DARK_GRAY);
         g.fillRect(enemy.getX(), enemy.getY(), Tank.SIZE, Tank.SIZE);
      }

      for (Bullet bullet : game.getBullets()) {
         g.setColor(Color.WHITE);
         g.fillRect(bullet.getX(), bullet.getY(), Bullet.SIZE, Bullet.SIZE);
      }

   }
   
}