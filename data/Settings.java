package data;

public class Settings { // Global game settings, modified via Options dialog
   
   public static boolean soundEnabled = true;
   public static boolean showFps = false;
   public static int startingLives = 3;
   
   private Settings() {} // Prevent instantiation, all access is static
}