package model.blocks;

public class BaseBlock extends Block {

   public BaseBlock(int x, int y){
      super(x, y);
   }

   @Override
   public boolean isDestroyable(){ return true; }

   @Override
   public boolean blocksBullets(){ return true; }

   @Override
   public boolean blocksTanks(){ return true; }
}