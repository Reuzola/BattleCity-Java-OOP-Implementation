package model.powerups;
import game.Game;

public class LifePowerUp extends PowerUp {

   public LifePowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.addLife();
   }
}