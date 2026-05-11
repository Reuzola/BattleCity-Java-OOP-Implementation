package model.blocks;

public class BushBlock extends Block {

   public BushBlock(int x, int y){
      super(x, y);
   }

   @Override
   public boolean isDestroyable(){ return false; }

   @Override
   public boolean blocksBullets(){ return false; }

   @Override
   public boolean blocksTanks(){ return false; }
}