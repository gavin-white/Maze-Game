package model;

import java.util.List;

/**
 * Represents the model for the state of a maze game. Provides methods to query the state of the game.
 * Parameterized by the set of movement directions available for this game.
 */
public interface MazeGameModelState {

  /**
   * Determines whether the maze is currently solved.
   * 
   * @return whether or not the maze is solved
   */
  public boolean isMazeSolved();
  
  /**
   * Produces the width of the maze in this game.
   * 
   * @return the width of the maze
   */
  public int getMazeWidth();
  
  /**
   * Produces the height of the maze in this game.
   * 
   * @return the height of the maze
   */
  public int getMazeHeight();
  
  /**
   * Produces the vertex that is the current position in the maze for the game.
   * 
   * @return the vertex of the current position
   */
  public Vertex getCurrentVertex();
  
  public List<Vertex> temp();

  /**
   * Produces the vertex that is the target in the maze for the game.
   * 
   * @return the vertex of the target
   */
  public Vertex getTargetVertex();
}
