package structures;

import java.util.Deque;
import java.util.LinkedList;

public class Queue<E> implements Collection<E> {
  
  private final Deque<E> contents = new LinkedList<>();

  @Override
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  @Override
  public void add(E item) {
    this.contents.addLast(item);
  }

  @Override
  public E remove() {
    return this.contents.removeFirst();
  }

  @Override
  public E peek() {
    return this.contents.peek();
  }
}
