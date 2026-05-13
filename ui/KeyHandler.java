package ui;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
   
   public boolean upPressed; // Fields for each direction move inputs and fire input
   public boolean downPressed;
   public boolean leftPressed;
   public boolean rightPressed;
   public boolean firePressed;

   @Override
   public void keyPressed(KeyEvent e) { // Updates flags when key pressed on keybord
      switch(e.getKeyCode()) {
         case KeyEvent.VK_W: upPressed = true; break; 
         case KeyEvent.VK_A: leftPressed = true; break;
         case KeyEvent.VK_S: downPressed = true; break;
         case KeyEvent.VK_D: rightPressed = true; break;
         case KeyEvent.VK_SPACE: firePressed = true; break;
      }
   }

   @Override
   public void keyReleased(KeyEvent e) { // Updates flags to defoult to false when input key released
      switch(e.getKeyCode()) {
         case KeyEvent.VK_W: upPressed = false; break;
         case KeyEvent.VK_A: leftPressed = false; break;
         case KeyEvent.VK_S: downPressed = false; break;
         case KeyEvent.VK_D: rightPressed = false; break;
         case KeyEvent.VK_SPACE: firePressed = false; break;
      }
   }

   @Override
   public void keyTyped(KeyEvent e) {}

}
