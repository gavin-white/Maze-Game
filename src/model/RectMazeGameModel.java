package model;

import java.util.List;

import model.Graph.Builder;
import model.Graph.RectMazeBuilder;
import structures.Collection;
import structures.Queue;
import structures.Stack;

public class RectMazeGameModel implements MazeGameModel {
  
  private Graph maze;
  private Vertex current;
  private Vertex target;
  private int height;
  private int width;
  private double bias;
  private boolean freeToMove = false;
  private Collection<Vertex> moves;
  
  public List<Vertex> temp() {
    return maze.getVertices();
  }
  
  public RectMazeGameModel(int height, int width, double bias) throws IllegalArgumentException {
    if (height < 3 || width < 3) {
      throw new IllegalArgumentException("Height and width must be greater than 3.");
    }
    if (height > 100 || width > 160) {
      throw new IllegalArgumentException("Max dimensions are height = 100, width = 160.");
    }
    if (bias < 0 || bias > 1) {
      throw new IllegalArgumentException("Bias must be between 0 and 1.");
    }
    
    this.height = height;
    this.width = width;
    this.bias = bias;
  }

  @Override
  public boolean isMazeSolved() {
    return current.equals(target);
  }

  @Override
  public int getMazeWidth() {
    return width;
  }

  @Override
  public int getMazeHeight() {
    return height;
  }

  @Override
  public Vertex getCurrentVertex() {
    return current;
  }
  
  @Override
  public Vertex getTargetVertex() {
    return target;
  }

  @Override
  public Builder startGame() {
    RectMazeBuilder b = Graph.rectMazeBuilder();
    maze = b.height(height).width(width).bias(bias).buildIncrementally();
    current = maze.getVertexAt(0);
    target = maze.getVertexAt(maze.numVertices() - 1);
    target.setColor(ColorScheme.TARGET);
    moves =  new Stack<>();
    
    freeToMove = false;
    
    return b;
  }

  @Override
  public void move(Direction direction) throws IllegalArgumentException, IllegalStateException {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null.");
    }
    if (!freeToMove) {
      throw new IllegalStateException("Cannot move now.");
    }
    if (current.hasEdgePointingIn(direction)) {
      Vertex moveFrom = current;
      current.setColor(ColorScheme.USER_VISITED);
      current = current.getNeighborIn(direction);
      current.setColor(ColorScheme.USER_CURSOR);
      if (moves.peek() == current) {
        moves.remove();
      } else {
        if (moveFrom != target) {
          moves.add(moveFrom);
        }
      }
    } else {
      throw new IllegalArgumentException("Cannot move in this direction.");
    }
  }
  
  @Override
  public void setFreeToMove(boolean free) {
    freeToMove = free;
  }

  @Override
  public SearchUtil bfs() throws IllegalStateException {
    if (!freeToMove) {
      throw new IllegalStateException("Cannot start bfs now.");
    }
    
    freeToMove = false;
    current =  temp().get(0);
    return new SearchUtil(new Queue<Vertex>(), current, target);
  }

  @Override
  public SearchUtil dfs() throws IllegalStateException {
    if (!freeToMove) {
      throw new IllegalStateException("Cannot start dfs now.");
    }
    
    freeToMove = false;
    current =  temp().get(0);
    return new SearchUtil(new Stack<Vertex>(), current, target);
  }
  
  @Override
  public ReconstructUtil reconstruct() {
    return new ReconstructUtil(moves);
  }
}
