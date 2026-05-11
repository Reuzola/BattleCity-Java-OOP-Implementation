package model.blocks;

public class SteelBlock extends Block {

   public SteelBlock(int x, int y){
      super(x, y);
   }

   @Override
   public boolean isDestroyable(){ return false; }

   @Override
   public boolean blocksBullets(){ return true; }

   @Override
   public boolean blocksTanks(){ return true; }
}