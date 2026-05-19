package model.powerups;
import game.Game;

public class ShovelPowerUp extends PowerUp {

   public ShovelPowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.activateShovel();
   }
}