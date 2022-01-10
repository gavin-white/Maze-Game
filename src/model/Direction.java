package model;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
  UP, RIGHT, DOWN, LEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT, UPLEFT;
  private static final Map<Direction, Direction> opposites = new HashMap<>();
  static {
    opposites.put(UP, DOWN);
    opposites.put(RIGHT, LEFT);
    opposites.put(DOWN, UP);
    opposites.put(LEFT, RIGHT);
    opposites.put(UPRIGHT, DOWNRIGHT);
    opposites.put(DOWNRIGHT, UPRIGHT);
    opposites.put(DOWNLEFT, UPLEFT);
    opposites.put(UPLEFT, DOWNLEFT);
  }

  public Direction opposite() {
    return opposites.get(this);
  }
}
