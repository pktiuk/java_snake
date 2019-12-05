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

    int tickTime = 500;
    // Head-beginning
    Vector<Location> snake;
    Direction snakeDirection = Direction.UP;

    Location apple;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
        pcs.firePropertyChange("wall", null, wall);

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
        setApple(new Location(3, 3));
        setDefaultWalls();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub
    }

    // Returns true when snake did not hit wall
    public boolean moveSnakeSucceeded(Direction d) {
        int headX = snake.get(0).x;
        int headY = snake.get(0).y;
        switch (d) {
        case UP:
            headY -= 1;
            break;
        case DOWN:
            headY += 1;
            break;
        case LEFT:
            headX -= 1;
            break;
        case RIGHT:
            headX += 1;
            break;
        }
        if (snakeDirection == d) {
            snake.set(0, new Location(headX, headY));
        } else {
            snake.insertElementAt(new Location(headX, headY), 0);
        }

        snakeDirection = d;
        if (snake.firstElement().equal(apple)) {
            pcs.firePropertyChange("apple eaten", null, apple);
        } else {
            // distance between 2 last components of snake
            int dist = 0;
            boolean enoughLong = false;
            if (snake.size() > 2) {
                dist = snake.lastElement().x + snake.lastElement().y - snake.get(snake.size() - 2).x
                        - snake.get(snake.size() - 2).y;
                enoughLong = true;
            }

            if (Math.abs(dist) == 1 && enoughLong)
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
        }
        pcs.firePropertyChange("snakeModel", null, snake);
        if (getFilledTiles()[snake.firstElement().x][snake.firstElement().y])
            return false;
        else
            return true;

    }

    public void setTickTime(int newTick) {
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
        boolean filledTiles[][] = new boolean[gridX][];
        for (int i = 0; i < gridY; i++) {
            filledTiles[i] = wall[i].clone();
        }
        int a, begin, end;
        for (int i = 1; i < snake.size() - 1; i++) {
            if (snake.get(i).x == snake.get(i + 1).x) {
                a = snake.get(i).x;
                begin = Math.min(snake.get(i).y, snake.get(i + 1).y);
                end = Math.max(snake.get(i).y, snake.get(i + 1).y);
                for (; begin <= end; begin++) {
                    filledTiles[a][begin] = true;
                }
            } else {
                a = snake.get(i).y;
                begin = Math.min(snake.get(i).x, snake.get(i + 1).x);
                end = Math.max(snake.get(i).x, snake.get(i + 1).x);
                for (; begin <= end; begin++) {
                    filledTiles[begin][a] = true;
                }
            }
        }

        return filledTiles;
    }

    void setDefaultWalls() {
        for (int i = 0; i < gridX; i++) {
            wall[i][0] = true;
            wall[i][gridY - 1] = true;
        }
        for (int i = 1; i < gridY - 1; i++) {
            wall[0][i] = true;
            wall[gridX - 1][i] = true;
        }
        pcs.firePropertyChange("wall", null, wall);
    }

    public Location getSnakeHead() {
        return snake.get(0);
    }

    public void setSnake(Vector<Location> s) {
        pcs.firePropertyChange("snakeModel", snake, s);
        snake = s;
    }
}
