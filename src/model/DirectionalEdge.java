package model;

import java.util.Comparator;

/**
 * Represents a directed, weighted edge in a graph that points in a particular direction.
 * Parameterized by the set of directions available for the edge to point in.
 *
 * @param <K> the set of directions available for the edge to point in
 */
public class DirectionalEdge {
  
  private Vertex to;
  private Vertex from;
  private Direction direction;
  private int weight;
  
  /**
   * Constructs a new {@code DirectionalEdge} object. Sets the weight to a default value of 0.
   * 
   * @param to the vertex the edge goes to
   * @param from the vertex the edge comes from
   * @param direction the direction the edge points in
   */
  public DirectionalEdge(Vertex to, Vertex from, Direction direction) {
    this.to = to;
    this.from = from;
    this.direction = direction;
    this.weight = 0;
  }
  
  /**
   * Constructs a new {@code DirectionalEdge} object.
   * 
   * @param to the vertex the edge goes to
   * @param from the vertex the edge comes from
   * @param direction the direction the edge points in
   * @param weight the weight of the edge
   */
  public DirectionalEdge(Vertex to, Vertex from, Direction direction, int weight) {
    this(to, from, direction);
    this.weight = weight;
  }
  
  /**
   * Produces the vertex that the edge goes to.
   * 
   * @return the vertex
   */
  public Vertex goesTo() {
    return to;
  }
  
  /**
   * Produces the vertex that the edge comes from.
   * 
   * @return the vertex
   */
  public Vertex comesFrom() {
    return from;
  }
  
  /**
   * Produces the direction this edge points in.
   * 
   * @return the direction
   */
  public Direction pointsIn() {
    return direction;
  }
  
  /**
   * Represents a comparator that compares the weights of two directional edges.
   */
  public final static class WeightComparator implements Comparator<DirectionalEdge> {

    @Override
    public int compare(DirectionalEdge e1, DirectionalEdge e2) {
      return e1.weight - e2.weight;
    }
    
  }
  
}
