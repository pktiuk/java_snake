package app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Controller
 */
public class Controller implements PropertyChangeListener {
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Model m;
    Gui g;
    Direction d = Direction.UP;
    Timer timer = new Timer();
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
        addPropertyChangeListener(g.arena);
        addPropertyChangeListener(m);
        g.arena.addPropertyChangeListener(this);
        Vector<Location> l = new Vector<Location>();
        l.add(new Location(x / 2, y / 2));
        l.add(new Location(1 + x / 2, y / 2));
        m.setSnake(l);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                doClockTick();
            }
        }, (long) m.getTickTime() * 1000, (long) m.getTickTime() * 1000);
    }

    void doClockTick() {

        if (m.moveSnakeSucceeded(d)) {
            // if (m.getApple().equal(m.getSnakeHead())

        } else {
            pcs.firePropertyChange("gameLost", null, true);
            timer.cancel();
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "directionChanged") {
            d = (Direction) evt.getNewValue();
        } else if (evt.getPropertyName() == "pauseButton") {
            if (isTimerOn) {
                timer.cancel();
                isTimerOn = false;
            } else {
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        doClockTick();
                    }
                }, (long) m.getTickTime() * 1000, (long) m.getTickTime() * 1000);
                isTimerOn = true;
            }
        }

    }

}
