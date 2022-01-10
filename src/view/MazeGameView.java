package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ColorScheme;
import model.Direction;
import model.MazeGameModelState;
import model.Vertex;

public final class MazeGameView {

  private final MazeGameModelState model;
  private final JFrame component;
  private final JLabel layer = new JLabel();
  private final JLabel label = new JLabel();
  private BufferedImage img;
  private static final int BORDER_WIDTH = 12;
  private int cellSize;
  private int exdCellSize;

  public MazeGameView(MazeGameModelState model, KeyListener kl, int maxWidth, int maxHeight) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }

    this.model = model;
    this.component = new JFrame();
   
    // max the extended cell size could be given the maxHeight
    int hBasedSize = (maxHeight - 50 - BORDER_WIDTH) / model.getMazeHeight(); 
    
    // max the extended cell size could be given the maxWidth
    int wBasedSize = (maxWidth - BORDER_WIDTH) / model.getMazeWidth(); 
    
    exdCellSize = Math.min(hBasedSize, wBasedSize);
    cellSize = exdCellSize - 2;
    
    component.setTitle("Maze Game");
    component.setSize(maxWidth, maxHeight);
    component.setLayout(new BoxLayout(component.getContentPane(), BoxLayout.PAGE_AXIS));
    JPanel panel = new JPanel();
    
    panel.add(layer);
    
    label.setMaximumSize(new Dimension(maxWidth, 20));
    label.setMinimumSize(new Dimension(maxWidth, 20));
    label.setPreferredSize(new Dimension(maxWidth, 20));
    component.add(label);
    component.add(panel);
    component.addKeyListener(kl);

    component.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    component.setVisible(true);
  }

  public void renderGame() {
    int height = model.getMazeHeight();
    int width = model.getMazeWidth();
    int heightPixels = model.getMazeHeight() * exdCellSize;
    int widthPixels = model.getMazeWidth() * exdCellSize;
    
    img = new BufferedImage(widthPixels + BORDER_WIDTH, heightPixels + BORDER_WIDTH, BufferedImage.TYPE_INT_RGB);
    BufferedImage brd = img.getSubimage(BORDER_WIDTH / 2 + 1, BORDER_WIDTH / 2 + 1, widthPixels, heightPixels);
    
    Vertex curr = model.getCurrentVertex();
    Vertex target = model.getTargetVertex();
    
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Vertex vr = model.temp().get(j + i * width);
        Color body;
        if (vr == curr) {
          body = ColorScheme.USER_CURSOR;
        } else if (vr == target) {
          body = ColorScheme.TARGET;
        } else {
          body = vr.getColor();
        }
        Color rBorder = ColorScheme.LINES;
        Color bBorder = ColorScheme.LINES;
        if (vr.hasEdgePointingIn(Direction.DOWN)) {
          bBorder = body;
        }
        if (vr.hasEdgePointingIn(Direction.RIGHT)) {
          rBorder = body;
        }
        for (int y = exdCellSize * i; y <= cellSize + 1 + exdCellSize * i; y++) {
          for (int x = exdCellSize * j; x <= cellSize + 1 + exdCellSize * j; x++) {
            if (y >= cellSize + exdCellSize * i) {
              if (x >= cellSize + exdCellSize * j) {
                brd.setRGB(x, y, ColorScheme.LINES.getRGB());
                continue;
              }
              brd.setRGB(x, y, bBorder.getRGB());
            } else if (x >= cellSize + exdCellSize * j) {
              brd.setRGB(x, y, rBorder.getRGB());
            } else {
              brd.setRGB(x, y, body.getRGB());
            }
          }
        }
      }
    }
    
    layer.setIcon(new ImageIcon(img));
    component.repaint();
  }
  
  public void updateColor(Vertex v, Color c) {
    for (int i = 0; i < model.getMazeHeight(); i++) {
      for (int j = 0; j < model.getMazeWidth(); j++) {
        if (model.temp().get(i * model.getMazeWidth() + j) == v) {
          renderFill(j, i, c);
        }
      }
    }
    
    component.repaint();
  }
  
  public void renderMessage(String msg) throws IllegalArgumentException {
    if (msg == null) {
      throw new IllegalArgumentException("Message cannot be null.");
    }
    
    label.setText(msg);
  }
  
  private void renderFill(int x, int y, Color c) {
    BufferedImage brd = img.getSubimage(7, 7, model.getMazeWidth() * exdCellSize, model.getMazeHeight() * exdCellSize);
    Vertex vr = model.temp().get(y * model.getMazeWidth() + x);
    
    for (int i = exdCellSize * y; i <= cellSize + 1 + exdCellSize * y; i++) {
      for (int j = exdCellSize * x; j <= cellSize + 1 + exdCellSize * x; j++) {
        Color rBorder = ColorScheme.LINES;
        Color bBorder = ColorScheme.LINES;
        if (vr.hasEdgePointingIn(Direction.DOWN)) {
          bBorder = c;
        }
        if (vr.hasEdgePointingIn(Direction.RIGHT)) {
          rBorder = c;
        }
        if (i >= cellSize + exdCellSize * y) {
          if (j >= cellSize + exdCellSize * x) {
            brd.setRGB(j, i, ColorScheme.LINES.getRGB());
            continue;
          }
          brd.setRGB(j, i, bBorder.getRGB());
        } else if (j >= cellSize + exdCellSize * x) {
          brd.setRGB(j, i, rBorder.getRGB());
        } else {
          brd.setRGB(j, i, c.getRGB());
        }
      }
    }
  }
}
