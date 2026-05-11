package model.blocks;

public abstract class Block {

   public static final int SIZE = 32;
   protected int x;
   protected int y;
   
   public Block(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }
   public int getY() {
      return y;
   }

   public abstract boolean isDestroyable();

   public abstract boolean blocksBullets();

   public abstract boolean blocksTanks();
}