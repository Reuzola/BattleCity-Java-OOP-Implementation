package ui;
import javax.swing.*;
import game.Game;
import game.GameLoop;

public class GameFrame extends JFrame {

   public GameFrame(){
      setTitle("Battle City");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JMenuBar menuBar = new JMenuBar();
      JMenu menu = new JMenu("Menu");
      JMenuItem newGameItem = new JMenuItem("New Game");
      JMenuItem mapEditorItem = new JMenuItem("Map Editor");
      JMenuItem optionsItem = new JMenuItem("Options");
      JMenuItem highScoresItem = new JMenuItem("High Scores");
      JMenuItem helpItem = new JMenuItem("Help");
      JMenuItem aboutItem = new JMenuItem("About");
      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(newGameItem);
      menu.add(mapEditorItem);
      menu.add(optionsItem);
      menu.add(highScoresItem);
      menu.add(helpItem);
      menu.add(aboutItem);
      menu.add(exitItem);
      menuBar.add(menu);
      setJMenuBar(menuBar);

      exitItem.addActionListener(e -> System.exit(0));

      KeyHandler keyHandler = new KeyHandler();
      Game game = new Game(1, keyHandler);
      GamePanel gamePanel = new GamePanel(game, keyHandler);
      add(gamePanel);
      pack();
      setLocationRelativeTo(null);
      
      GameLoop gameLoop = new GameLoop(game, gamePanel);
      gameLoop.start();

      setVisible(true);
   }
}