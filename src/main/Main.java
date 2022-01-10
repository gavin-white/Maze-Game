package main;

import java.util.HashMap;
import java.util.Map;

import model.Direction;
import controller.MazeGameController;
import model.RectMazeGameModel;

public class Main {
  public static void main(String[] args) {
     MazeGameController controller = new MazeGameController(
         new RectMazeGameModel(20, 40, 0.5), 1250, 650);
     Map<String, Direction> controls = new HashMap<>();
     controls.put("w", Direction.UP);
     controls.put("a", Direction.LEFT);
     controls.put("s", Direction.DOWN);
     controls.put("d", Direction.RIGHT);
     controller.runGame(controls);
  }
}