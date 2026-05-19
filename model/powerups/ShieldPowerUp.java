package model.powerups;
import game.Game;

public class ShieldPowerUp extends PowerUp {

   public ShieldPowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.getPlayerTank().activateShield();
   }
}