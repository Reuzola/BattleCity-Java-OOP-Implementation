package ui;
import javax.swing.*;
import game.Game;
import game.GameLoop;

public class GameFrame extends JFrame {

   private Game game;
   private GamePanel gamePanel;
   private GameLoop gameLoop;
   private KeyHandler keyHandler;

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
      JMenuItem pauseItem = new JMenuItem("Pause");
      JMenuItem exitItem = new JMenuItem("Exit");
      menu.add(newGameItem);
      menu.add(mapEditorItem);
      menu.add(optionsItem);
      menu.add(highScoresItem);
      menu.add(helpItem);
      menu.add(aboutItem);
      menu.add(pauseItem);
      menu.add(exitItem);
      menuBar.add(menu);
      setJMenuBar(menuBar);

      startNewGame(1); // Starts new game with difficulty 1

      aboutItem.addActionListener(e -> new AboutDialog(this).setVisible(true));
      helpItem.addActionListener(e -> new HelpDialog(this).setVisible(true));
      highScoresItem.addActionListener(e -> new HighScorePanel(this).setVisible(true));
      mapEditorItem.addActionListener(e -> {
         Game.GameState prev = game.getState();
         if(prev == Game.GameState.RUNNING) game.setState(Game.GameState.PAUSED); // Freeze world while editing

         MapEditorPanel editor = new MapEditorPanel(this); // Modal, blocks here until closed
         editor.setLocationRelativeTo(this);
         editor.setVisible(true);

         if(prev == Game.GameState.RUNNING) game.setState(Game.GameState.RUNNING); // Restore only if we paused it
         gamePanel.requestFocusInWindow();
      });
      newGameItem.addActionListener(e -> {
         String[] options = {"Level 1", "Level 2", "Level 3"};
         int choice = JOptionPane.showOptionDialog(
            this,
            "Select difficulty:",
            "New Game",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]
         );
         if(choice >= 0) startNewGame(choice + 1); // +1 because difficulty is 1-3, index 0-2
      });
      exitItem.addActionListener(e -> System.exit(0));
      pauseItem.addActionListener(e -> {
         game.togglePause();
         gamePanel.requestFocusInWindow();
      });

      setVisible(true);
   }

   private void startNewGame(int difficulty) {
      if(gameLoop != null) gameLoop.stop(); // Stop the previous loop thread if any
      if(gamePanel != null) remove(gamePanel); // Detach old panel from the frame

      keyHandler = new KeyHandler();
      game = new Game(difficulty, keyHandler);
      keyHandler.setGame(game);
      gamePanel = new GamePanel(game, keyHandler);
      add(gamePanel);
      pack();
      setLocationRelativeTo(null);

      gameLoop = new GameLoop(game, gamePanel);
      gameLoop.start();
   
      gamePanel.requestFocusInWindow();
      revalidate();
      repaint();
   }
}