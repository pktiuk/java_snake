package app;

import java.util.Vector;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * <code>Model</code> - model describing state of snake and whole arena,
 * contains size of arena, state of snake, location of rest of the objects
 */
public class Model {

    private int gridX = 40;
    private int gridY = 40;
    // true is wall, false no wall
    boolean wall[][];
    // describes location of snake (begin - head)
    Vector<Integer> snakeX;
    Vector<Integer> snakeY;
    double tickTime = 0.7;
    // Apple position =(x,y)
    int applePosition[] = new int[2];

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

}
