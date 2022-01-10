package model;

import java.util.List;

import model.Graph.Builder;

public class HexMazeGameModel implements MazeGameModel {
  
  public List<Vertex> temp() {
    return null;
  }

  @Override
  public boolean isMazeSolved() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getMazeWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getMazeHeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Vertex getCurrentVertex() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void move(Direction direction) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setFreeToMove(boolean free) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Builder startGame() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SearchUtil bfs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SearchUtil dfs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Vertex getTargetVertex() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReconstructUtil reconstruct() {
    // TODO Auto-generated method stub
    return null;
  }

}
