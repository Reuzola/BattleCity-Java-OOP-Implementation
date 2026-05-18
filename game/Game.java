package game;
import java.util.ArrayList;
import model.Bullet;
import model.blocks.BaseBlock;
import model.blocks.Block;
import model.powerups.PowerUp;
import model.tanks.*;
import ui.KeyHandler;

public class Game {

   public enum GameState { RUNNING, PAUSED, GAME_OVER, LEVEL_COMPLETE }

   private Level activeLevel;
   private BaseBlock baseBlock;
   private PlayerTank playerTank;

   private ArrayList<Block> blocks;
   private ArrayList<EnemyTank> enemyTanks;
   private ArrayList<Bullet> bullets;
   private ArrayList<PowerUp> powerUps;

   private int score;
   private int lives;
   private int enemiesKilled;
   private int enemiesSpawned;
   private GameState state;
   
   public Game(int difficulty, KeyHandler keyHandler){
      enemyTanks = new ArrayList<>();
      enemyTanks.add(new BasicEnemy(Level.ENEMY_SPAWN_GRID_X[1] * Block.SIZE, Level.ENEMY_SPAWN_GRID_Y * Block.SIZE));
      bullets = new ArrayList<>();
      powerUps = new ArrayList<>();

      activeLevel = new Level(difficulty);
      blocks = activeLevel.buildBlocks();
      baseBlock = activeLevel.buildBase();

      int px = Level.PLAYER_SPAWN_GRID_X * Block.SIZE;
      int py = Level.PLAYER_SPAWN_GRID_Y * Block.SIZE;
      playerTank = new PlayerTank(px, py, keyHandler);

      score = 0;
      lives = 3;
      enemiesKilled = 0;
      enemiesSpawned = 0;
      state = GameState.RUNNING;
   }

   public Level getActiveLevel() {
      return activeLevel;
   }
   public BaseBlock getBaseBlock() {
      return baseBlock;
   }
   public PlayerTank getPlayerTank() {
      return playerTank;
   }
   public ArrayList<Block> getBlocks() {
      return blocks;
   }
   public ArrayList<EnemyTank> getEnemyTanks() {
      return enemyTanks;
   }
   public ArrayList<Bullet> getBullets() {
      return bullets;
   }
   public ArrayList<PowerUp> getPowerUps() {
      return powerUps;
   }
   public int getScore() {
      return score;
   }
   public int getLives() {
      return lives;
   }
   public int getEnemiesKilled() {
      return enemiesKilled;
   }
   public int getEnemiesSpawned() {
      return enemiesSpawned;
   }
   public GameState getState() {
      return state;
   }
   
   public void update() { // Added base block to allBlock list which needed in act method
      ArrayList<Block> allBlocks = new ArrayList<>(blocks);
      allBlocks.add(baseBlock);
      Bullet bullet = playerTank.act(allBlocks);

      for (EnemyTank et : enemyTanks) {
         Bullet enemyBullet = et.act(allBlocks);
         if(enemyBullet != null) bullets.add(enemyBullet);
      }

      int size = bullets.size();
      for (int i = 0; i < size; i++) {
         bullets.get(i).move();
      }

      for (int i = bullets.size() - 1; i >= 0; i--) { // Collision control of bullets with blocks
         for (int j = blocks.size() - 1; j >= 0; j--) {
            Block block = blocks.get(j);
            if (block.blocksBullets()) {
               Bullet b = bullets.get(i);
               if (b.getX() + Bullet.SIZE > block.getX() &&
                   b.getX() < block.getX() + Block.SIZE &&
                   b.getY() + Bullet.SIZE > block.getY() &&
                   b.getY() < block.getY() + Block.SIZE) {
                     b.deactivate();
                     bullets.remove(i);
                     if (block.isDestroyable()) blocks.remove(j);
                     break;
                  }
            }
         }
      }

      for (int i = bullets.size() - 1; i >= 0; i--) { // Collision control of bullets with game map
         if(bullets.get(i).getX() + Bullet.SIZE < 0 ||
            bullets.get(i).getX() > Level.GRID_WIDTH * Block.SIZE ||
            bullets.get(i).getY() + Bullet.SIZE < 0 ||
            bullets.get(i).getY() > Level.GRID_HEIGHT * Block.SIZE) {
               bullets.get(i).deactivate();
               bullets.remove(i);
            }
      }      

      if(bullet != null) bullets.add(bullet);
   }
}