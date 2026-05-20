package game;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import model.blocks.*;

public class Level {

   public static final int GRID_WIDTH = 13; // Map size 13x13
   public static final int GRID_HEIGHT = 13;

   public static final int PLAYER_SPAWN_GRID_X = 2; // Defoult spawn points for player
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
      ".G...GG...S..",
      ".GGG...B...S.",
      ".G..G......B.",
      ".G...G.......",
      ".......GGG...",
      ".......G..B.S",
      "B......G..G..",
      "BB.....G...G.",
      "SBB.......GG.",
      "S.BB..B....G.",
      "S..B.BBB...G.",
      "S..B.BEB...G."
   };
   private static final String[] LEVEL_2_MAP = {
      ".............",
      "G.BB..B.S.B.B",
      "G.WWWWWWWWW.W",
      ".B..B..B....S",
      ".B..B..BB.S..",
      "BB..B..B..BB.",
      ".B..S..GG....",
      "WWWWWWWGGWWWW",
      "G....B......B",
      "G.S..B..S.B.B",
      "G.B..B..B.B.B",
      "G.B..BBB...BB",
      ".....BEB....."
   };
   private static final String[] LEVEL_3_MAP = {
      ".............",
      "G.WW..W.W.WG.",
      "G..WW.S.W..W.",
      "W...WWW.WWWW.",
      "W.G...W.W..W.",
      "W...B.W.G.WW.",
      "B.WWW.W.G.WW.",
      "B...W.W.G..W.",
      "WW..W...G.BW.",
      "WW.BB...WWWW.",
      "W..G.......W.",
      "W..GGBBB...WW",
      "W..G.BEB...WW"
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

   public Level(String filePath) { // Custom map loader: difficulty mirrors level 2 (medium)
      this.difficulty = 2;
      this.enemySpeed = 2;

      String[] loaded = new String[GRID_HEIGHT];
      try {
         BufferedReader br = new BufferedReader(new FileReader(filePath));
         for (int y = 0; y < GRID_HEIGHT; y++) {
            String line = br.readLine();
            if(line == null || line.length() < GRID_WIDTH) {
               br.close();
               throw new IOException("Map file too short or malformed");
            }
            loaded[y] = line;
         }
         br.close();
      } catch(IOException e) {
         System.err.println("Error loading custom map, falling back to level 1: " + e.getMessage());
         loaded = LEVEL_1_MAP; // Defensive fallback if file is missing or broken
      }
      this.mapData = loaded;
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

   public ArrayList<String> buildEnemyComposition() { // Returns array of string. B for basic, F for fast, A for armored. Effected by difficulty
      ArrayList<String> enemyDiff = new ArrayList<>();
      switch(difficulty) {
         case 1: 
            for(int i = 0; i < 20; i++) { // 14B 4F 2A
               if(i < 14) enemyDiff.add("B");
               else if(i < 18) enemyDiff.add("F");
               else enemyDiff.add("A");
            } break;
         case 2:
            for(int i = 0; i < 20; i++) { // 8B 8F 4A
               if(i < 8) enemyDiff.add("B");
               else if(i < 16) enemyDiff.add("F");
               else enemyDiff.add("A");
            } break;
         case 3:
            for(int i = 0; i < 20; i++) { // 4B 8F 8A
               if(i < 4) enemyDiff.add("B");
               else if(i < 12) enemyDiff.add("F");
               else enemyDiff.add("A");
            } break;
      }
      Collections.shuffle(enemyDiff);
      return enemyDiff;
   }
}