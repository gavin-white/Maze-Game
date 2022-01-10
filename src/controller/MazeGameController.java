package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import model.ColorScheme;
import model.Direction;
import model.Graph.Builder;
import model.MazeGameModel;
import model.ReconstructUtil;
import model.SearchUtil;
import view.MazeGameView;

public class MazeGameController implements KeyListener {

  private final MazeGameModel model;
  private final MazeGameView view;
  private SearchUtil search;
  private Builder build;
  private ReconstructUtil recon;
  private Map<String, Direction> controls;

  public MazeGameController(MazeGameModel model, int maxWidth, int maxHeight) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    this.model = model;
    this.view = new MazeGameView(model, this, maxWidth, maxHeight);
    build = model.startGame();
  }

  public void runGame(Map<String, Direction> controls) 
      throws IllegalArgumentException, IllegalStateException {
    if (controls == null) {
      throw new IllegalArgumentException("Given map of controls cannot be null.");
    }
    
    this.controls = controls;
    
    view.renderGame();
    
    new javax.swing.Timer(1, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         onTick();
      }
   }).start();
  }

  public void takeInput(String next) {

    try {
      
      Direction d = controls.getOrDefault(next, null);
      if (d != null) {
        try {
          model.move(d);
          if (model.isMazeSolved()) {
            view.renderMessage("Maze solved!");
            model.setFreeToMove(false);
            recon = model.reconstruct();
          }
        } catch (IllegalArgumentException e) {
          return;
        }
        view.renderGame();
        return;
      }
      
      switch (next) {
      case "d":
        search = model.dfs();
        break;
      case "b":
        search = model.bfs();
        break;
      case "r":
        build = model.startGame();
        view.renderMessage("");
      }
    } catch (IllegalStateException e) {

    }

  }

  public void onTick() {
    if (search != null) {
      if (search.hasNextSearch()) {
        view.renderMessage("Searching for path...");
        view.updateColor(search.incrementSearch(), ColorScheme.SEARCH_VISITED);
      } 
      else if (search.hasNextReconstruction()) {
        view.renderMessage("Path found! Reconstructing path...");
        view.updateColor(search.incrementReconstruction(), ColorScheme.RECON);
      } else {
        view.updateColor(model.getCurrentVertex(), ColorScheme.RECON);
        view.renderMessage("");
        search = null;
      }
    }
    if (build != null) {
      if (build.hasNextBuild()) {
        view.renderMessage("Building maze...");
        build.nextBuild();
      } 
      else {
        view.renderMessage("");
        model.setFreeToMove(true);
        build = null;
      }
      view.renderGame();
    }
    if (recon != null) {
      if (recon.hasNextReconstruction()) {
        view.updateColor(recon.nextReconstruction(), ColorScheme.RECON);
      }
      else {
        recon = null;
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub
    takeInput(Character.toString(e.getKeyChar()));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }
}
