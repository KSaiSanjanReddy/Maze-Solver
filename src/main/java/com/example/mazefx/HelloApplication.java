package com.example.mazefx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


class Node {
    public int x, y;
    public Node parent;

    public Node(int x, int y, Node parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
}

class Maze {
    private final int[][] grid;

    public Maze(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid;
    }
}

class DFSSolver {
    public static List<Node> solveMaze(Maze maze, Node start, Node end) {
        boolean[][] visited = new boolean[maze.getGrid().length][maze.getGrid()[0].length];
        Stack<Node> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();

            if (current.x == end.x && current.y == end.y) {
                return constructPath(current);
            }

            if (visited[current.x][current.y]) continue;
            visited[current.x][current.y] = true;

            for (Node neighbor : getNeighbors(current, maze, visited)) {
                stack.push(neighbor);
                neighbor.parent = current;
            }
        }
        return new ArrayList<>();
    }

    // Updated getNeighbors method with randomized directions
    private static List<Node> getNeighbors(Node node, Maze maze, boolean[][] visited) {
        List<Node> neighbors = new ArrayList<>();
        List<int[]> directions = Arrays.asList(
                new int[]{0, 1},  // Right
                new int[]{1, 0},  // Down
                new int[]{0, -1}, // Left
                new int[]{-1, 0}  // Up
        );

        // Shuffle directions to randomize DFS behavior
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];
            if (isValid(newX, newY, maze, visited)) {
                neighbors.add(new Node(newX, newY, null));
            }
        }
        return neighbors;
    }

    private static boolean isValid(int x, int y, Maze maze, boolean[][] visited) {
        return x >= 0 && x < maze.getGrid().length &&
                y >= 0 && y < maze.getGrid()[0].length &&
                maze.getGrid()[x][y] == 0 && !visited[x][y];
    }

    private static List<Node> constructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        for (Node at = endNode; at != null; at = at.parent) {
            path.add(at);
        }
        List<Node> reversePath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversePath.add(path.get(i));
        }
        return reversePath;
    }
}


class BFSSolver {
    public static List<Node> solveMaze(Maze maze, Node start, Node end) {
        boolean[][] visited = new boolean[maze.getGrid().length][maze.getGrid()[0].length];
        Queue<Node> queue = new LinkedList<>();
        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.x == end.x && current.y == end.y) {
                return constructPath(current);
            }

            for (Node neighbor : getNeighbors(current, maze, visited)) {
                if (!visited[neighbor.x][neighbor.y]) {
                    queue.add(neighbor);
                    visited[neighbor.x][neighbor.y] = true;
                    neighbor.parent = current;
                }
            }
        }
        return new ArrayList<>();
    }

    private static List<Node> getNeighbors(Node node, Maze maze, boolean[][] visited) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];
            if (isValid(newX, newY, maze, visited)) {
                neighbors.add(new Node(newX, newY, null));
            }
        }
        return neighbors;
    }

    private static boolean isValid(int x, int y, Maze maze, boolean[][] visited) {
        return x >= 0 && x < maze.getGrid().length &&
                y >= 0 && y < maze.getGrid()[0].length &&
                maze.getGrid()[x][y] == 0 && !visited[x][y];
    }

    private static List<Node> constructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        for (Node at = endNode; at != null; at = at.parent) {
            path.add(at);
        }
        List<Node> reversePath = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            reversePath.add(path.get(i));
        }
        return reversePath;
    }


}

public class HelloApplication extends Application {

    private static final int CELL_SIZE = 30;

    private static final int[][] MAZE_GRID = {
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
    };

    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        solveAndVisualizeBothAlgorithms();
    }

    private void solveAndVisualizeBothAlgorithms() {
        Maze maze = new Maze(MAZE_GRID);
        Node start = new Node(0, 0, null);
        Node end = new Node(MAZE_GRID.length - 1, MAZE_GRID[0].length - 1, null);

        // Solve with BFS first
        long bfsStartTime = System.nanoTime();
        List<Node> bfsPath = BFSSolver.solveMaze(maze, start, end);
        long bfsTime = System.nanoTime() - bfsStartTime;

        // Visualize BFS
        visualizePath(maze.getGrid(), bfsPath, "BFS", bfsTime, Color.GREEN, () -> {
            // Solve with DFS after BFS visualization
            long dfsStartTime = System.nanoTime();
            List<Node> dfsPath = DFSSolver.solveMaze(maze, start, end);
            long dfsTime = System.nanoTime() - dfsStartTime;

            // Visualize DFS
            visualizePath(maze.getGrid(), dfsPath, "DFS", dfsTime, Color.BLUE, null);
        });
    }

    private void visualizePath(int[][] maze, List<Node> path, String algorithm, long time, Color color, Runnable onComplete) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: white;");

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == 1) {
                    drawWall(pane, col, row);
                }
            }
        }

        Label algorithmLabel = new Label(String.format("%s Time: %.3f ms", algorithm, time / 1_000_000.0));
        algorithmLabel.setLayoutX(10);
        algorithmLabel.setLayoutY(maze.length * CELL_SIZE + 10);
        pane.getChildren().add(algorithmLabel);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        int[] index = {0};
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2), e -> {
            if (index[0] < path.size() - 1) {
                Node current = path.get(index[0]);
                Node next = path.get(index[0] + 1);

                Line line = new Line(
                        current.y * CELL_SIZE + CELL_SIZE / 2.0,
                        current.x * CELL_SIZE + CELL_SIZE / 2.0,
                        next.y * CELL_SIZE + CELL_SIZE / 2.0,
                        next.x * CELL_SIZE + CELL_SIZE / 2.0
                );
                line.setStroke(color);
                line.setStrokeWidth(4);
                pane.getChildren().add(line);

                index[0]++;
            } else {
                timeline.stop();
                if (onComplete != null) onComplete.run();
            }
        }));

        timeline.play();

        Scene scene = new Scene(pane, maze[0].length * CELL_SIZE, maze.length * CELL_SIZE + 50);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Maze Solver - " + algorithm);
        primaryStage.show();
    }

    private void drawWall(Pane pane, int col, int row) {
        double x = col * CELL_SIZE;
        double y = row * CELL_SIZE;

        Line left = new Line(x, y, x, y + CELL_SIZE);
        Line right = new Line(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
        Line top = new Line(x, y, x + CELL_SIZE, y);
        Line bottom = new Line(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);

        left.setStrokeWidth(2);
        right.setStrokeWidth(2);
        top.setStrokeWidth(2);
        bottom.setStrokeWidth(2);

        left.setStroke(Color.BLACK);
        right.setStroke(Color.BLACK);
        top.setStroke(Color.BLACK);
        bottom.setStroke(Color.BLACK);

        pane.getChildren().addAll(left, right, top, bottom);
    }

    public static void main(String[] args) {
        launch();
    }
}