package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a vertex in a graph. 
 */
public class Vertex {
  
  private final List<DirectionalEdge> outEdges = new ArrayList<>();
  private Color data;
  
  /**
   * Constructs a new {@code Vertex} object.
   * 
   * @param data the data associated with the vertex
   */
  public Vertex(Color data) {
    this.data = data;
  }
  
  /**
   * Adds a given edge to this vertex's list of edges.
   * 
   * @param edge the edge to be added
   */
  public void addEdge(DirectionalEdge edge) {
    outEdges.add(edge);
  }

  /**
   * Determines whether this vertex has an edge pointing in a given direction.
   * 
   * @param d the direction
   * @return true if this vertex has an edge pointing in the given direction, false if not
   */
  public boolean hasEdgePointingIn(Direction d) {
    for (DirectionalEdge edge : outEdges) {
      if (edge.pointsIn().equals(d)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Produces the vertex neighboring this vertex in a given direction
   * 
   * @param d the direction the neighbor is in
   * @return the neighboring vertex
   * @throws IllegalArgumentException if there is no neighbor in the given direction
   */
  public Vertex getNeighborIn(Direction d) throws IllegalArgumentException {
    for (DirectionalEdge edge : outEdges) {
      if (edge.pointsIn().equals(d)) {
        return edge.goesTo();
      }
    }
    throw new IllegalArgumentException("Vertex has no neighbor in this direction.");
  }
  
  /**
   * Removes all edges from a vertex.
   */
  public void removeAllEdges() {
    outEdges.clear();
  }
  
  /**
   * Produces a list of all edges connected to this vertex.
   * 
   * @return a list of the edges connecting to this vertex
   */
  public List<DirectionalEdge> getAllEdges() {
    return new ArrayList<>(outEdges);
  }
  
  /**
   * Sets the color of this vertex to a given color.
   * 
   * @param c the color to set this vertex to
   * @throws IllegalArgumentException if the given color is null
   */
  public void setColor(Color c) throws IllegalArgumentException {
    if (c == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    data = c;
  }
  
  
  /**
   * Gets the color of this vertex.
   * 
   * @return the color of this vertex
   */
  public Color getColor() {
    return data;
  }
}
