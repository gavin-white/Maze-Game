package structures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a disjoint set (aka Union/Find).
 *
 * @param <K> the type of the members of the set
 */
public class DisjointSet<K> {
  
  private final Map<K, K> reps = new HashMap<>();
  
  /**
   * Constructs a new {@code DisjointSet} object setting all members representatives
   * to themselves.
   * 
   * @param members the members to be added to this disjoint set.
   */
  public DisjointSet(List<K> members) {
    for (K member : members) {
      reps.put(member, member);
    }
  }
  
  /**
   * Unions two given members of this disjoint set, setting the second's representative to
   * be the first's representative.
   * 
   * @param member1 the member whose representative is being applied to the other
   * @param member2 the member whose representative is being updated
   * @throws IllegalArgumentException if either of the given members do not exist in the disjoint set
   */
  public void union(K member1, K member2) throws IllegalArgumentException {
    if (!reps.containsKey(member1) || !reps.containsKey(member2)) {
      throw new IllegalArgumentException("One or both members do not exist in disjoint set.");
    }
    reps.put(find(member1), find(member2));
    //reps.put(member1, find(member2));
  }
  
  /**
   * Produces the base representative of the given member.
   * 
   * @param member the member whose representative is being sought
   * @return the representative of the given member
   * @throws IllegalArgumentException if the given member does not exist in the disjoint set
   */
  public K find(K member) throws IllegalArgumentException {
    K representative = reps.getOrDefault(member, null);
    if (representative == null) {
      throw new IllegalArgumentException("Member does not exist in disjoint set.");
    }
    if (representative.equals(member)) {
      return representative;
    } else {
      return find(representative);
    }
  }
}
