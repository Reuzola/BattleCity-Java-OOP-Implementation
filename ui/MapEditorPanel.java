package ui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import game.Level;
import model.blocks.Block;

public class MapEditorPanel extends JDialog {

   private char[][] grid; // Each cell holds the map symbol: '.', 'B', 'S', 'W', 'G'
   private char selectedTool = 'B'; // Current paint tool
   private JPanel canvas;

   public MapEditorPanel(JFrame owner) {
      super(owner, "Map Editor", true);
      setLayout(new BorderLayout());

      grid = new char[Level.GRID_HEIGHT][Level.GRID_WIDTH];
      for (int y = 0; y < Level.GRID_HEIGHT; y++) // Initialize blank grid
         for (int x = 0; x < Level.GRID_WIDTH; x++)
            grid[y][x] = '.';

      canvas = new JPanel() { // Custom-painted grid where blocks are placed
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int y = 0; y < Level.GRID_HEIGHT; y++) {
               for (int x = 0; x < Level.GRID_WIDTH; x++) {
                  int px = x * Block.SIZE;
                  int py = y * Block.SIZE;
                  char c = grid[y][x];
                  drawCell(g, px, py, c);

                  if(x == Level.BASE_GRID_X && y == Level.BASE_GRID_Y) { // Base position marker
                     g.drawImage(Sprites.BASE, px, py, Block.SIZE, Block.SIZE, null);
                     g.setColor(Color.YELLOW);
                     g.drawRect(px, py, Block.SIZE - 1, Block.SIZE - 1);
                  }
                  if(x == Level.PLAYER_SPAWN_GRID_X && y == Level.PLAYER_SPAWN_GRID_Y) { // Player spawn marker
                     g.setColor(new Color(0, 200, 0, 120));
                     g.fillRect(px, py, Block.SIZE, Block.SIZE);
                  }
                  for (int sx : Level.ENEMY_SPAWN_GRID_X) { // Enemy spawn markers
                     if(x == sx && y == Level.ENEMY_SPAWN_GRID_Y) {
                        g.setColor(new Color(200, 0, 0, 120));
                        g.fillRect(px, py, Block.SIZE, Block.SIZE);
                     }
                  }

                  g.setColor(new Color(50, 50, 50)); // Grid lines
                  g.drawRect(px, py, Block.SIZE, Block.SIZE);
               }
            }
         }
      };
      canvas.setPreferredSize(new Dimension(Level.GRID_WIDTH * Block.SIZE, Level.GRID_HEIGHT * Block.SIZE));
      canvas.setBackground(Color.BLACK);
      canvas.addMouseListener(new MouseAdapter() { // Single click paint
         @Override
         public void mousePressed(MouseEvent e) { paintAt(e); }
      });
      canvas.addMouseMotionListener(new MouseMotionAdapter() { // Drag paint
         @Override
         public void mouseDragged(MouseEvent e) { paintAt(e); }
      });

      JPanel toolbar = new JPanel(); // Tool selection buttons
      toolbar.setLayout(new GridLayout(0, 1, 4, 4));
      addToolButton(toolbar, "Empty", '.');
      addToolButton(toolbar, "Brick", 'B');
      addToolButton(toolbar, "Steel", 'S');
      addToolButton(toolbar, "Water", 'W');
      addToolButton(toolbar, "Bush",  'G');

      JButton clearBtn = new JButton("Clear All");
      clearBtn.addActionListener(e -> {
         for (int y = 0; y < Level.GRID_HEIGHT; y++)
            for (int x = 0; x < Level.GRID_WIDTH; x++) grid[y][x] = '.';
         canvas.repaint();
      });
      toolbar.add(new JLabel(" "));
      toolbar.add(clearBtn);

      JButton saveBtn = new JButton("Save");
      saveBtn.addActionListener(e -> saveMap()); // Wired in next step
      toolbar.add(saveBtn);

      JButton loadBtn = new JButton("Load");
      loadBtn.addActionListener(e -> loadMap()); // Wired in next step
      toolbar.add(loadBtn);

      add(canvas, BorderLayout.CENTER);
      add(toolbar, BorderLayout.EAST);

      pack();
   }

   private void addToolButton(JPanel parent, String label, char symbol) {
      JButton b = new JButton(label);
      b.addActionListener(e -> selectedTool = symbol);
      parent.add(b);
   }

   private void paintAt(MouseEvent e) { // Convert mouse coords to grid cell, place selected tool
      int gx = e.getX() / Block.SIZE;
      int gy = e.getY() / Block.SIZE;
      if(gx < 0 || gx >= Level.GRID_WIDTH || gy < 0 || gy >= Level.GRID_HEIGHT) return;
      if(gx == Level.BASE_GRID_X && gy == Level.BASE_GRID_Y) return; // Base cell is locked
      grid[gy][gx] = selectedTool;
      canvas.repaint();
   }

   private void drawCell(Graphics g, int px, int py, char c) { // Draws sprite for given symbol
      switch(c) {
         case 'B': g.drawImage(Sprites.BRICK, px, py, Block.SIZE, Block.SIZE, null); break;
         case 'S': g.drawImage(Sprites.STEEL, px, py, Block.SIZE, Block.SIZE, null); break;
         case 'W': g.drawImage(Sprites.WATER, px, py, Block.SIZE, Block.SIZE, null); break;
         case 'G': g.drawImage(Sprites.BUSH,  px, py, Block.SIZE, Block.SIZE, null); break;
      }
   }

   private void saveMap() {
      try {
         File dir = new File("maps");
         if(!dir.exists()) dir.mkdirs(); // Create maps directory if missing

         BufferedWriter bw = new BufferedWriter(new FileWriter("maps/custom.map"));
         for (int y = 0; y < Level.GRID_HEIGHT; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < Level.GRID_WIDTH; x++) {
               if(x == Level.BASE_GRID_X && y == Level.BASE_GRID_Y) line.append('E'); // Base always at fixed cell
               else line.append(grid[y][x]);
            }
            bw.write(line.toString());
            bw.newLine();
         }
         bw.close();
         JOptionPane.showMessageDialog(this, "Map saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
      } catch(IOException e) {
         JOptionPane.showMessageDialog(this, "Error saving map: " + e.getMessage(), "Save Failed", JOptionPane.ERROR_MESSAGE);
      }
   }

   private void loadMap() {
      File f = new File("maps/custom.map");
      if(!f.exists()) {
         JOptionPane.showMessageDialog(this, "No saved map found.", "Load", JOptionPane.WARNING_MESSAGE);
         return;
      }
      try {
         BufferedReader br = new BufferedReader(new FileReader(f));
         for (int y = 0; y < Level.GRID_HEIGHT; y++) {
            String line = br.readLine();
            if(line == null || line.length() < Level.GRID_WIDTH) { // Corrupted or short file
               br.close();
               JOptionPane.showMessageDialog(this, "Map file is corrupted.", "Load Failed", JOptionPane.ERROR_MESSAGE);
               return;
            }
            for (int x = 0; x < Level.GRID_WIDTH; x++) {
               char c = line.charAt(x);
               if(c == 'E') grid[y][x] = '.'; // Base is implicit, do not store as a placeable cell
               else grid[y][x] = c;
            }
         }
         br.close();
         canvas.repaint();
         JOptionPane.showMessageDialog(this, "Map loaded successfully.", "Load", JOptionPane.INFORMATION_MESSAGE);
      } catch(IOException e) {
         JOptionPane.showMessageDialog(this, "Error loading map: " + e.getMessage(), "Load Failed", JOptionPane.ERROR_MESSAGE);
      }
   }
}