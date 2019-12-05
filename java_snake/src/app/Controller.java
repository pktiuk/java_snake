package app;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;
import java.util.Vector;
import javax.swing.Timer;

/**
 * Controller - main class in MVC model it contains logic of game, decides when
 * it is lost and it contains timer controling speed of game
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

    public Controller(int x, int y, Model model, Gui gui) {
        m = model;
        g = gui;
        m.addPropertyChangeListener(g.arena);
        m.addPropertyChangeListener(this);
        addPropertyChangeListener(g.arena);
        addPropertyChangeListener(m);
        g.arena.addPropertyChangeListener(this);
        Vector<Location> l = new Vector<Location>();
        l.add(new Location(x / 2, y / 2));
        l.add(new Location(x / 2, y / 2 + 3));
        m.setSnake(l);
        timer = new Timer(m.tickTime, taskPerformer);
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
        } else if (evt.getPropertyName() == "apple eaten") {
            boolean filled[][] = m.getFilledTiles();
            int x, y;
            Random rand = new Random();
            do {
                x = rand.nextInt(filled.length);
                y = rand.nextInt(filled[0].length);
            } while (filled[x][y]);
            m.setApple(new Location(x, y));
        }

    }

}
