package model.blocks;

public class BaseBlock extends Block {

   private boolean destroyed = false;

   public BaseBlock(int x, int y){
      super(x, y);
   }

   public boolean isDestroyed() {
      return destroyed;
   }
   public void destroy() {
      destroyed = true;
   }

   @Override
   public boolean isDestroyable() { return true; }

   @Override
   public boolean blocksBullets() { return true; }

   @Override
   public boolean blocksTanks() { return true; }
}