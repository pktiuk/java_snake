package app;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

enum Direction {
    LEFT, RIGHT, UP, DOWN
}

/**
 * <code>Model</code> - model describing state of snake and whole arena,
 * contains size of arena, state of snake, location of rest of the objects
 */
public class Model {

    private int gridX = 40;
    private int gridY = 40;
    // true is wall, false no wall
    boolean wall[][];

    double tickTime = 0.7;
    // Apple position =(x,y)
    int applePosition[] = new int[2];
    // Head-beginning
    Vector<Location> snake = new Vector<Location>();
    Direction snakeDirection = Direction.UP;

    Location apple;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public Model(int x, int y) {
        gridX = x;
        gridY = y;
        for (int i = 0; i < gridX; i++) {
            wall[i] = new boolean[gridY];
        }
    }



    public void setTickTime(double newTick) {
        pcs.firePropertyChange("tickTime", tickTime, newTick);
        tickTime = newTick;
    }

    public void setApple(Location newApple) {
        pcs.firePropertyChange("appleLocation", apple, newApple);
        apple = newApple;
    }

    public Location getApple() {
        return apple;
    }

    public double getTickTime() {
        return tickTime;
    }

    public boolean[][] getFilledTiles() {
        boolean filledTiles[][] = wall.clone();
        int a, begin, end;
        for (int i = 0; i < snake.size() - 1; i++) {
            if (snake.get(i).x == snake.get(i + 1).x) {
                a = snake.get(i).x;
                begin = Math.min(snake.get(i).y, snake.get(i + 1).y);
                end = Math.max(snake.get(i).y, snake.get(i + 1).y);
                for (; begin <= end; begin++) {
                    wall[a][begin] = true;
                }
            } else {
                a = snake.get(i).y;
                begin = Math.min(snake.get(i).x, snake.get(i + 1).x);
                end = Math.max(snake.get(i).x, snake.get(i + 1).x);
                for (; begin <= end; begin++) {
                    wall[begin][a] = true;
                }
            }
        }
        return filledTiles;
    }

    public Location getSnakeHead() {
        return snake.get(0);
    }
}
