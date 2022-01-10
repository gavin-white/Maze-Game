package structures;

public interface Collection<E> {
  
  boolean isEmpty();

  void add(E item);

  E remove();
  
  E peek();
}
