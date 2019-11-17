package app;

import java.beans.PropertyChangeEvent;
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
public class Model implements PropertyChangeListener {

    private int gridX = 40;
    private int gridY = 40;
    // true is wall, false no wall
    boolean wall[][];

    double tickTime = 0.7;
    // Apple position =(x,y)
    int applePosition[] = new int[2];
    // Head-beginning
    Vector<Location> snake;
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
        wall = new boolean[x][];
        for (int i = 0; i < gridX; i++) {
            wall[i] = new boolean[gridY];
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

    public void moveSnake(Direction d) {
        if (snakeDirection == d)
            snake.set(0, new Location(snake.get(0).x, snake.get(0).y));
        else {
            Location newHead = snake.get(0);
            switch (d) {
            case UP:
                newHead.y -= 1;
                break;
            case DOWN:
                newHead.y += 1;
                break;
            case LEFT:
                newHead.x -= 1;
                break;
            case RIGHT:
                newHead.x += 1;
                break;
            }
            snake.insertElementAt(newHead, 0);
            snakeDirection = d;
        }
        // distance between 2 last components of snake
        int dist = snake.lastElement().x + snake.lastElement().y - snake.get(snake.size() - 2).x
                - snake.get(snake.size() - 2).y;
        if (Math.abs(dist) == 1)
            snake.remove(snake.size() - 1);
        else {
            if (snake.lastElement().x < snake.get(snake.size() - 2).x) {
                snake.lastElement().x += 1;
            } else if (snake.lastElement().x > snake.get(snake.size() - 2).x) {
                snake.lastElement().x -= 1;
            } else if (snake.lastElement().y < snake.get(snake.size() - 2).y) {
                snake.lastElement().y += 1;
            } else if (snake.lastElement().y > snake.get(snake.size() - 2).y) {
                snake.lastElement().y -= 1;
            }
        }
        pcs.firePropertyChange("snakeModel", null, snake);
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

    public void setSnake(Vector<Location> s) {
        pcs.firePropertyChange("snakeModel", snake, s);
        snake = s;
    }
}
