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

   public static final int MAX_ACTIVE_ENEMIES = 4;
   public static final int MAX_TOTAL_ENEMIES = 20;
   public static final int SPAWN_INTERVAL_FRAMES = 90; // Enemy tanks spawn every 3 seconds

   private Level activeLevel;
   private BaseBlock baseBlock;
   private PlayerTank playerTank;
   private KeyHandler keyHandler;

   private ArrayList<Block> blocks;
   private ArrayList<EnemyTank> enemyTanks;
   private ArrayList<Bullet> bullets;
   private ArrayList<PowerUp> powerUps;
   private ArrayList<String> enemyComposition;

   private int score;
   private int lives;
   private int enemiesKilled;
   private int enemiesSpawned;
   private int spawnTimer;
   private int elapsedFrames;
   private GameState state;
   
   public Game(int difficulty, KeyHandler keyHandler){
      this.keyHandler = keyHandler;
      enemyTanks = new ArrayList<>();
      bullets = new ArrayList<>();
      powerUps = new ArrayList<>();

      activeLevel = new Level(difficulty);
      enemyComposition = activeLevel.buildEnemyComposition();
      blocks = activeLevel.buildBlocks();
      baseBlock = activeLevel.buildBase();
      blocks.add(baseBlock);

      int px = Level.PLAYER_SPAWN_GRID_X * Block.SIZE;
      int py = Level.PLAYER_SPAWN_GRID_Y * Block.SIZE;
      playerTank = new PlayerTank(px, py, keyHandler);

      score = 0;
      lives = 3;
      enemiesKilled = 0;
      enemiesSpawned = 0;
      elapsedFrames = 0;
      spawnTimer = SPAWN_INTERVAL_FRAMES;
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
   public int getElapsedSeconds() {
      return elapsedFrames / 30; // 30 frames at every second
   }
   public GameState getState() {
      return state;
   }

   public void togglePause() {
      if(state == GameState.RUNNING) state = GameState.PAUSED;
      else if(state == GameState.PAUSED) state = GameState.RUNNING;
   }
   
   public void update() { // General game update method
      if(state != GameState.RUNNING) return;

      elapsedFrames++;
      spawnTimer++;
      if(spawnTimer >= SPAWN_INTERVAL_FRAMES && enemyTanks.size() < MAX_ACTIVE_ENEMIES && enemiesSpawned < MAX_TOTAL_ENEMIES) {
         int randomIndex = (int)(Math.random() * Level.ENEMY_SPAWN_GRID_X.length);
         String type = enemyComposition.get(enemiesSpawned);
         int spawnX = Level.ENEMY_SPAWN_GRID_X[randomIndex] * Block.SIZE;
         int spawnY = Level.ENEMY_SPAWN_GRID_Y * Block.SIZE;

         switch(type) {
            case "B": enemyTanks.add(new BasicEnemy(spawnX, spawnY)); break;
            case "F": enemyTanks.add(new FastEnemy(spawnX, spawnY)); break;
            case "A": enemyTanks.add(new ArmoredEnemy(spawnX, spawnY)); break;
         }

         enemiesSpawned++;
         spawnTimer = 0;
      }

      Bullet bullet = playerTank.act(blocks);
      if(bullet != null) bullets.add(bullet);

      for (EnemyTank et : enemyTanks) {
         Bullet enemyBullet = et.act(blocks);
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
                     if (block.isDestroyable()) {
                        if(block == baseBlock && b.getOwner() instanceof EnemyTank) {
                           baseBlock.destroy();
                           state = GameState.GAME_OVER;
                        }
                        if(block != baseBlock) blocks.remove(j);
                     }
                     break;
                  }
            }
         }
      }

      for (int i = bullets.size() - 1; i >= 0; i--) { // Collision control of player-bullet enemy tank and enemy-bullet player tank
         Bullet b = bullets.get(i);
         if(b.getOwner() instanceof PlayerTank) { // If bullet shooted from player, its only effect enemy
            for (int j = enemyTanks.size() - 1; j >= 0; j--) {
               EnemyTank et = enemyTanks.get(j);
               if(et.getX() + Tank.SIZE > b.getX() &&
                  et.getX() < b.getX() + Bullet.SIZE &&
                  et.getY() + Tank.SIZE > b.getY() &&
                  et.getY() < b.getY() + Bullet.SIZE) {
                     et.takeDamage(1);
                     b.deactivate();
                     bullets.remove(i);
                     if(et.isDead()) {
                        enemiesKilled++;
                        if(et instanceof BasicEnemy) score += 100;
                        else if(et instanceof FastEnemy) score += 200;
                        else if(et instanceof ArmoredEnemy) score += 400;
                        enemyTanks.remove(j);
                        if(enemiesKilled == MAX_TOTAL_ENEMIES) state = GameState.LEVEL_COMPLETE;
                     }
                     break;
                  }
            }
         } else { // If bullet shooted from enemy, its only effect player
            if(playerTank.getX() + Tank.SIZE > b.getX() &&
               playerTank.getX() < b.getX() + Bullet.SIZE &&
               playerTank.getY() + Tank.SIZE > b.getY() &&
               playerTank.getY() < b.getY() + Bullet.SIZE) {
                  playerTank.takeDamage(1);
                  b.deactivate();
                  bullets.remove(i);
                  if(playerTank.isDead()) {
                     lives--;
                     if(lives > 0) { // Respawn
                        int px = Level.PLAYER_SPAWN_GRID_X * Block.SIZE;
                        int py = Level.PLAYER_SPAWN_GRID_Y * Block.SIZE;
                        playerTank = new PlayerTank(px, py, keyHandler);
                     } else state = GameState.GAME_OVER; // Game over
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
   }
}