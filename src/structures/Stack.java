package structures;

import java.util.Deque;
import java.util.LinkedList;

public class Stack<E> implements Collection<E> {
  
  private final Deque<E> contents = new LinkedList<>();

  @Override
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  @Override
  public E remove() {
    return this.contents.removeFirst();
  }

  @Override
  public void add(E item) {
    this.contents.addFirst(item);
  }
  
  @Override
  public E peek() {
    return this.contents.peek();
  }
}

