package ui;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprites {

   public static BufferedImage BRICK; // Block sprites
   public static BufferedImage STEEL;
   public static BufferedImage WATER;
   public static BufferedImage BUSH;
   public static BufferedImage BASE;

   public static BufferedImage PLAYER_UP; // Player tank, 4 directions
   public static BufferedImage PLAYER_LEFT;
   public static BufferedImage PLAYER_DOWN;
   public static BufferedImage PLAYER_RIGHT;

   public static BufferedImage BASIC_UP; // Basic enemy (green)
   public static BufferedImage BASIC_LEFT;
   public static BufferedImage BASIC_DOWN;
   public static BufferedImage BASIC_RIGHT;

   public static BufferedImage FAST_UP; // Fast enemy (red/pink)
   public static BufferedImage FAST_LEFT;
   public static BufferedImage FAST_DOWN;
   public static BufferedImage FAST_RIGHT;

   public static BufferedImage ARMORED_UP; // Armored enemy (blue/silver)
   public static BufferedImage ARMORED_LEFT;
   public static BufferedImage ARMORED_DOWN;
   public static BufferedImage ARMORED_RIGHT;

   public static void load() {
      try {
         BufferedImage sheet = ImageIO.read(new File("assets/general_sprites.png")); // Read image

         BRICK = sheet.getSubimage(256, 0,  16, 16); // Block pixels in image
         STEEL = sheet.getSubimage(256, 16, 16, 16);
         WATER = sheet.getSubimage(256, 32, 16, 16);
         BUSH  = sheet.getSubimage(272, 32, 16, 16);
         BASE  = sheet.getSubimage(304, 32, 16, 16);

         PLAYER_UP    = sheet.getSubimage(0,  0, 16, 16); // Player tank pixels in image
         PLAYER_LEFT  = sheet.getSubimage(32, 0, 16, 16);
         PLAYER_DOWN  = sheet.getSubimage(64, 0, 16, 16);
         PLAYER_RIGHT = sheet.getSubimage(96, 0, 16, 16);

         BASIC_UP    = sheet.getSubimage(0,  128, 16, 16); // Basic enemy pixels in image
         BASIC_LEFT  = sheet.getSubimage(32, 128, 16, 16);
         BASIC_DOWN  = sheet.getSubimage(64, 128, 16, 16);
         BASIC_RIGHT = sheet.getSubimage(96, 128, 16, 16);

         FAST_UP    = sheet.getSubimage(128, 128, 16, 16); // Fast enemy pixels in image
         FAST_LEFT  = sheet.getSubimage(160, 128, 16, 16);
         FAST_DOWN  = sheet.getSubimage(192, 128, 16, 16);
         FAST_RIGHT = sheet.getSubimage(224, 128, 16, 16);

         ARMORED_UP    = sheet.getSubimage(128, 0, 16, 16); // Armored enemy pixels in image
         ARMORED_LEFT  = sheet.getSubimage(160, 0, 16, 16);
         ARMORED_DOWN  = sheet.getSubimage(192, 0, 16, 16);
         ARMORED_RIGHT = sheet.getSubimage(224, 0, 16, 16);

      } catch(IOException e) {
         System.err.println("Sprite not found: " + e.getMessage());
         System.exit(1);
      }
   }
}