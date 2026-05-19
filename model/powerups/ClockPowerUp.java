package model.powerups;
import game.Game;

public class ClockPowerUp extends PowerUp {

   public ClockPowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.activateFreeze();
   }
}