package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import structures.Collection;

public class SearchUtil {
  
  private final Collection<Vertex> worklist;
  private final Map<Vertex, DirectionalEdge> cameFromEdge = new HashMap<>();
  private final List<Vertex> visited = new ArrayList<>();
  private final Vertex target;
  private final Vertex origin;
  private Vertex current;
  
  public SearchUtil(Collection<Vertex> worklist, Vertex origin, Vertex target) 
      throws IllegalArgumentException {
    if (worklist == null || origin == null || target == null) {
      throw new IllegalArgumentException("Worklist, starting, and ending nodes cannot be null.");
    }
    
    this.worklist = worklist;
    worklist.add(origin);
    this.origin = origin;
    this.target = target;
    this.current = target;
  }
  
  public void instantSearch() throws IllegalStateException {
    while (hasNextSearch()) {
      incrementSearch();
    }
  }
  
  public boolean hasNextSearch() throws IllegalStateException {
    if (!cameFromEdge.containsKey(target) && worklist.isEmpty()) {
      throw new IllegalStateException("Search could not find target.");
    }
    
    return !cameFromEdge.containsKey(target);
  }
  
  public Vertex incrementSearch() throws IllegalCallerException {
    if (!hasNextSearch()) {
      throw new IllegalCallerException("Cannot increment search now.");
    }
    
    Vertex next = worklist.remove();
    if (!visited.contains(next)) {
      for (DirectionalEdge e : next.getAllEdges()) {
        this.worklist.add(e.goesTo());
        if (!this.visited.contains(e.goesTo())) {
          this.cameFromEdge.put(e.goesTo(), e);
        }
      }
      
      next.setColor(ColorScheme.SEARCH_VISITED);
      visited.add(next);
      return next;
    }
    
    return null;
  }
  
  public boolean hasNextReconstruction() throws IllegalStateException {
    if (!cameFromEdge.containsKey(current) && !current.equals(origin)) {
      throw new IllegalStateException("Reconstruction impossible.");
    }
    
    return !current.equals(origin);
  }
  
  public void instantReconstruction() {
    while (hasNextReconstruction()) {
      incrementReconstruction();
    }
  }
  
  public Vertex incrementReconstruction() throws IllegalCallerException {
    if (!hasNextReconstruction()) {
      throw new IllegalCallerException("Cannot increment reconstruction now.");
    }
    
    Vertex temp = current;
    if (!current.equals(target)) {
      current.setColor(ColorScheme.RECON);
    }
    
    current = cameFromEdge.get(current).comesFrom();
    return temp;
  }
}
