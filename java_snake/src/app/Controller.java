package app;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.Timer;

/**
 * Controller
 */
public class Controller implements PropertyChangeListener {
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Model m;
    Gui g;
    Direction d = Direction.UP;
    Direction lastMoved = Direction.UP;
    Timer timer;
    boolean isTimerOn = true;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public Controller(int x, int y) {
        m = new Model(x, y);// Najlepiej w mainie()
        g = new Gui(x, y);
        m.addPropertyChangeListener(g.arena);
        m.addPropertyChangeListener(this);
        addPropertyChangeListener(g.arena);
        addPropertyChangeListener(m);
        g.arena.addPropertyChangeListener(this);
        Vector<Location> l = new Vector<Location>();
        l.add(new Location(x / 2, y / 2));
        l.add(new Location(x / 2, y / 2 + 3));
        m.setSnake(l);
        timer = new Timer(1000, taskPerformer);
        timer.start();
        m.moveSnakeSucceeded(d);
        m.setApple(new Location(3, 3));
    }

    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            doClockTick();
        }
    };

    void doClockTick() {
        lastMoved = d;
        if (!m.moveSnakeSucceeded(d)) {
            pcs.firePropertyChange("gameLost", null, true);
            timer.stop();
            System.out.println("Lost");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "directionChanged") {
            if (!((lastMoved == Direction.UP && (Direction) evt.getNewValue() == Direction.DOWN)
                    || (lastMoved == Direction.DOWN && (Direction) evt.getNewValue() == Direction.UP)
                    || (lastMoved == Direction.RIGHT && (Direction) evt.getNewValue() == Direction.LEFT)
                    || (lastMoved == Direction.LEFT && (Direction) evt.getNewValue() == Direction.RIGHT)))
                d = (Direction) evt.getNewValue();
        } else if (evt.getPropertyName() == "pauseButton") {
            if (isTimerOn) {

                isTimerOn = false;
            } else {
                timer.start();
                isTimerOn = true;
            }
        }

    }

}
