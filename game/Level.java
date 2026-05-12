package game;
import java.util.ArrayList;
import model.blocks.*;

public class Level {

   public static final int GRID_WIDTH = 13; // Map size 13x13
   public static final int GRID_HEIGHT = 13;

   public static final int PLAYER_SPAWN_GRID_X = 4; // Defoult spawn points for player
   public static final int PLAYER_SPAWN_GRID_Y = 12;
   public static final int BASE_GRID_X = 6;
   public static final int BASE_GRID_Y = 12;
   public static final int[] ENEMY_SPAWN_GRID_X = {0, 6, 12}; // Enemy can be spawn 3 different points. top left, top mid, top right
   public static final int ENEMY_SPAWN_GRID_Y = 0; // Enemy spawns top of the map
   
   private final int difficulty; // 1, 2, 3
   private final String[] mapData; // This map data relative to difficulty. '.' = Empty, 'B' = brick, 'S' = steel, 'W' = water, 'G' = grass (bush), 'E' = base (eagle)
   private final int enemySpeed; // Speed of enemies in this level

   private static final String[] LEVEL_1_MAP = { // Static map datas for each level
      ".............",
      ".BB.BB.BB.BB.",
      ".BB.BB.BB.BB.",
      ".............",
      ".BB.SS.SS.BB.",
      ".BB.SS.SS.BB.",
      ".............",
      ".BB.BB.BB.BB.",
      ".BB.BB.BB.BB.",
      ".............",
      ".BB.......BB.",
      ".BB.BBEBB.BB.",
      "....B...B...."
   };
   private static final String[] LEVEL_2_MAP = {
      ".............",
      ".BBBB.W.BBBB.",
      ".BBBB.W.BBBB.",
      ".....SWS.....",
      ".GG.......GG.",
      ".GG.BB.BB.GG.",
      "WWWW.....WWWW",
      ".GG.BB.BB.GG.",
      ".GG.......GG.",
      ".....SWS.....",
      ".BBBB.W.BBBB.",
      ".BBBB.WEBBBB.",
      "....BB.BB...."
   };
   private static final String[] LEVEL_3_MAP = {
      ".............",
      ".SS.SS.SS.SS.",
      ".SS.SS.SS.SS.",
      ".............",
      ".SS.GG.GG.SS.",
      ".SS.GG.GG.SS.",
      "..W.......W..",
      ".SS.GG.GG.SS.",
      ".SS.GG.GG.SS.",
      ".............",
      ".SS.......SS.",
      ".SS.SSESS.SS.",
      "....S...S...."
   };

   public Level(int difficulty){
      this.difficulty = difficulty;

      switch(difficulty){
         case 1:
            mapData = LEVEL_1_MAP;
            enemySpeed = 1;
            break;
         case 2:
            mapData = LEVEL_2_MAP;
            enemySpeed = 2;
            break;
         case 3:
            mapData = LEVEL_3_MAP;
            enemySpeed = 3;
            break;
         default: throw new IllegalArgumentException("Difficulty must be 1, 2 or 3");
      }
   }

   public int getDifficulty() {
      return difficulty;
   }
   public int getEnemySpeed() {
      return enemySpeed;
   }
   
   public ArrayList<Block> buildBlocks(){ // Iterates mapData array and for each symbol, adds corresponding Block object to ArrayList then returns
      ArrayList<Block> list = new ArrayList<>();

      for(int y = 0; y < GRID_HEIGHT; y++){
         for(int x = 0; x < GRID_WIDTH; x++){
            char c = mapData[y].charAt(x); // Extract each symbol

            int px = x * Block.SIZE; // Converting to pixel
            int py = y * Block.SIZE;

            switch (c) { // Add Block objects to list
               case 'B':
                  list.add(new BrickBlock(px, py));
                  break;
               case 'S':
                  list.add(new SteelBlock(px, py));
                  break;
               case 'W':
                  list.add(new WaterBlock(px, py));
                  break;
               case 'G':
                  list.add(new BushBlock(px, py));
                  break;
            }
         }
      }

      return list;
   }

   public BaseBlock buildBase(){ // Specific method for BaseBlock
      int px = BASE_GRID_X * Block.SIZE; // Converting to pixel
      int py = BASE_GRID_Y * Block.SIZE;

      return new BaseBlock(px, py);
   }
}