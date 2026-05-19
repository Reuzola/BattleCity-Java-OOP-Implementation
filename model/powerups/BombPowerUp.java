package model.powerups;
import game.Game;

public class BombPowerUp extends PowerUp {

   public BombPowerUp(int x, int y) {
      super(x, y);
   }

   @Override
   public void applyEffect(Game game){
      game.bombVisibleEnemies();
   }
}