package model;

import structures.Collection;

public class ReconstructUtil {
  
  private Collection<Vertex> moves;

  public ReconstructUtil(Collection<Vertex> moves) {
    this.moves = moves;
  }
  
  public boolean hasNextReconstruction() {
    return !moves.isEmpty();
  }
  
  public Vertex nextReconstruction() {
    Vertex v = moves.remove();
    v.setColor(ColorScheme.RECON);
    return v;
  }
  
  public void instantReconstruction() {
    while (hasNextReconstruction()) {
      nextReconstruction();
    }
  }
}
