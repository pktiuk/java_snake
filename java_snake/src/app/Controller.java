package app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

/**
 * Controller
 */
public class Controller implements PropertyChangeListener {
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    Model m;
    Gui g;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    public Controller(int x, int y) {
        m = new Model(x, y);
        g = new Gui(x, y);
        m.addPropertyChangeListener(g.arena);
        addPropertyChangeListener(g.arena);
        addPropertyChangeListener(m);
        g.arena.addPropertyChangeListener(this);
        Vector<Location> l = new Vector<Location>();
        l.add(new Location(x / 2, y / 2));
        l.add(new Location(1 + x / 2, y / 2));
        m.setSnake(l);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO Auto-generated method stub

    }

}
