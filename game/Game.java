package game;
import java.util.ArrayList;
import model.Bullet;
import model.blocks.BaseBlock;
import model.blocks.Block;
import model.powerups.PowerUp;
import model.tanks.EnemyTank;
import model.tanks.PlayerTank;

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
   
   public Game(int difficulty){
      enemyTanks = new ArrayList<>();
      bullets = new ArrayList<>();
      powerUps = new ArrayList<>();

      activeLevel = new Level(difficulty);
      blocks = activeLevel.buildBlocks();
      baseBlock = activeLevel.buildBase();

      int px = Level.PLAYER_SPAWN_GRID_X * Block.SIZE;
      int py = Level.PLAYER_SPAWN_GRID_Y * Block.SIZE;
      playerTank = new PlayerTank(px, py);

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
   

}