package app;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Gui {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("JavaSnake");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new ArenaPanel());
        f.pack();
        f.setVisible(true);
    }
}
/**
 * <code>ArenaPanel</code>  - panel containing visualization of snake's arena (snake,walls,apples)
 */
class ArenaPanel extends JPanel implements PropertyChangeListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int defaultX = 15; // default size of box
    private int defaultY = 15;
    private int gridX = 40;
    private int gridY = 40;

    public ArenaPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(defaultX * gridX, defaultY * gridY);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "snakeModel") {

        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, defaultX * gridX, defaultY * gridY);
    }

    protected void drawBox(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x * defaultX, y * defaultY, defaultX, defaultY);
    }
}
