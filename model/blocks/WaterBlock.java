package model.blocks;

public class WaterBlock extends Block {

   public WaterBlock(int x, int y){
      super(x, y);
   }

   @Override
   public boolean isDestroyable(){ return false; }

   @Override
   public boolean blocksBullets(){ return false; }

   @Override
   public boolean blocksTanks(){ return true; }
}