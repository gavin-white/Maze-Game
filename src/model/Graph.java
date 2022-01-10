package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import structures.DisjointSet;

/**
 * Represents a graph. Parameterizes by the type of vertices in the graph, K.
 *
 * @param <K> the type of vertices in the graph
 */
public class Graph {
  
  private final List<Vertex> vertices;
  
  /**
   * Constructs a new {@code Graph} object for a rectangular graph.
   * 
   * @param height the height of the graph in number of nodes.
   * @param width the width of the graph in number of nodes.
   */
  private Graph(int height, int width, double bias) {
    
    int horizontalMax = (int) ((1 - bias) * 10000);
    int verticalMax = (int) (bias * 10000);
    vertices = new ArrayList<>(height * width);
    Random rand = new Random();
    
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Vertex v = new Vertex(ColorScheme.BACKGROUND);
        if (j > 0) {
          v.addEdge(new DirectionalEdge(
              vertices.get(i * width + j - 1), v, Direction.LEFT, rand.nextInt(horizontalMax)));
        }
        if (i > 0) {
          v.addEdge(new DirectionalEdge(
              vertices.get((i - 1) * width + j), v, Direction.UP, rand.nextInt(verticalMax)));
        }
        vertices.add(v);
      }
    }
  }
  
  public List<Vertex> getVertices() {
    return this.vertices;
  }
  
  /**
   * Constructs a new {@code Graph} object for a hexagonal graph.
   * 
   * @param height the height of the graph in number of nodes.
   * @param width the width of the graph in number of nodes.
   */
  private Graph(int size, double bias) {
    
    int diagonalMax = (int) ((1 - bias) * 10000);
    int verticalMax = (int) (bias * 10000);
    vertices = new ArrayList<>(size * (1 + size) / 2 + 1);    
    int up = 0; int upLeft = 0; int upRight = 0;
    int height = 1 + 4 * size;
    int width = 0;
    
    for (int i = 0; i < height; i++) {
      if (i > height - size || width == 1 + size) width--; else width++;
      for (int j = 0; j < width; j++) {
        Vertex v = new Vertex(Color.WHITE);
        if (i > size || (j != 0 && j != width))
          v.addEdge(new DirectionalEdge(vertices.get(up++), v, Direction.UP, verticalMax));
        if (j != 0 || i >= height - size)
          v.addEdge(new DirectionalEdge(vertices.get(upLeft++), v, Direction.UPLEFT, diagonalMax));
        if (j != width || i >= height - size)
          v.addEdge(new DirectionalEdge(vertices.get(upRight++), v, Direction.UPRIGHT, diagonalMax));
        this.vertices.add(v);
      }
    }
  }

  /**
   * Produces the vertex in the graph at the given index.
   * 
   * @param index
   * @return
   * @throws IllegalArgumentException
   */
  public Vertex getVertexAt(int index) throws IllegalArgumentException {
    try {
      return vertices.get(index);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Invalid index.");
    }
  }
  
  /**
   * Gets the number of vertices in this graph.
   * 
   * @return the number of vertices in this graph
   */
  public int numVertices() {
    return vertices.size();
  }
  
  /**
   * Removes all edges from every vertex in this graph.
   */
  private void removeAllEdges() {
    for (Vertex vertex : vertices) {
      vertex.removeAllEdges();
    }
  }

  /**
   * Produces a list of all of the edges in this graph.
   * 
   * @return a list of every edge in this graph.
   */
  private List<DirectionalEdge> getAllEdges() {
    List<DirectionalEdge> listOfEdges = new ArrayList<>();
    for (Vertex vertex : vertices) {
      listOfEdges.addAll(vertex.getAllEdges());
    }
    return listOfEdges;
  }
  
  /**
   * Produces a minimum spanning tree for this graph using Kruskal's algorithm.
   * 
   * @return a list of edges in a minimum spanning tree of this graph
   */
  public List<DirectionalEdge> createMinimumSpanningTree() {
    List<DirectionalEdge> edgesInTree = new ArrayList<>();
    List<DirectionalEdge> worklist = getAllEdges();
    DisjointSet<Vertex> disjSet = new DisjointSet<>(vertices);
    
    worklist.sort(new DirectionalEdge.WeightComparator());
    
    while (edgesInTree.size() < numVertices() - 1) {
      DirectionalEdge e = worklist.remove(0);
      Vertex from = e.comesFrom();
      Vertex to = e.goesTo();
      if (!disjSet.find(from).equals(disjSet.find(to))) {
        edgesInTree.add(e);
        disjSet.union(from, to);
      }
    }
    
    return edgesInTree;
  }
  
  public static RectMazeBuilder rectMazeBuilder() {
    return new RectMazeBuilder();
  }
  
  public static HexMazeBuilder hexMazeBuilder() {
    return new HexMazeBuilder();
  }
  
  public static abstract class Builder {
    public abstract boolean hasNextBuild();
    public abstract void nextBuild();
    public abstract Graph buildIncrementally();
    public abstract Graph buildInstant();
  }
  
  public static class RectMazeBuilder extends Builder {
    
    private int height = 10;
    private int width = 10;
    private double bias = 0.5;
    private List<DirectionalEdge> mst;
    
    public RectMazeBuilder height(int height) {
      this.height = height;
      return this;
    }
    
    public RectMazeBuilder width(int width) {
      this.width = width;
      return this;
    }
    
    public RectMazeBuilder bias(double bias) {
      this.bias = bias;
      return this;
    }
    
    public boolean hasNextBuild() {
      return mst.size() > 0;
    }
    
    public void nextBuild() throws IllegalStateException {
      if (mst.size() <= 0) {
        throw new IllegalStateException("No next build.");
      }
      DirectionalEdge next = mst.remove(0);
      next.comesFrom().addEdge(next);
      next.goesTo().addEdge(new DirectionalEdge(next.comesFrom(), next.goesTo(), next.pointsIn().opposite()));
    }
    
    public Graph buildIncrementally() {
      Graph g = new Graph(height, width, bias);
      mst = g.createMinimumSpanningTree();
      g.removeAllEdges();
      
      return g;
    }

    public Graph buildInstant() {
      Graph g = new Graph(height, width, bias);
      mst = g.createMinimumSpanningTree();
      g.removeAllEdges();
      while (hasNextBuild()) {
        nextBuild();
      }
      return g;
    }
    
  }
  
  public static class HexMazeBuilder extends Builder {
    
    private int size = 1;
    private double bias = 0.5;
    
    public HexMazeBuilder size(int size) {
      this.size = size;
      return this;
    }
    
    public HexMazeBuilder bias(double bias) {
      this.bias = bias;
      return this;
    }

    public Graph build() {
      return new Graph(size, bias);
    }

    @Override
    public boolean hasNextBuild() {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void nextBuild() {
      // TODO Auto-generated method stub
      
    }

    @Override
    public Graph buildIncrementally() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public Graph buildInstant() {
      // TODO Auto-generated method stub
      return null;
    }
    
  }
}
