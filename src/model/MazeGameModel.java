package model;

/**
 * Represents the model for a maze game. Provides additional methods to modify the state of the game.
 * Parameterized by the set of movement directions available for this game.
 */
public interface MazeGameModel extends MazeGameModelState {
  
  /**
   * Starts the maze game by preparing the maze and putting the current position at the start.
   */
  public Graph.Builder startGame();
  
  /**
   * Moves the current position to a given destination.
   * 
   * @param direction the direction to move in.
   * @throws IllegalArgumentException if the attempted move is invalid
   */
  public void move(Direction direction) throws IllegalArgumentException;
  
  /**
   * Sets whether or not the current maze is free for moves.
   * 
   * @param free whether or not this maze game is free for moves.
   */
  public void setFreeToMove(boolean free);
  
  public SearchUtil bfs();
  
  public SearchUtil dfs();

  public ReconstructUtil reconstruct();
}
