package model.powerups;
import game.Game;

public class StarPowerUp extends PowerUp {

   public StarPowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.getPlayerTank().addStar();
   }
}