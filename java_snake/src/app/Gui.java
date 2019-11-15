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
import java.util.Vector;

public class Gui {
    ArenaPanel arena;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui window=new Gui(40,40);
                window.arena.
            }
        });
    }

    public Gui(int x, int y) {
        JFrame f = new JFrame("JavaSnake");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        arena=new ArenaPanel(x,y);
        f.add(arena);
        f.pack();
        f.setVisible(true);
    }
}

/**
 * <code>ArenaPanel</code> - panel containing visualization of snake's arena
 * (snake,walls,apples)
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
    private Color snakeColor = Color.GREEN;
    private Color appleColor = Color.RED;
    private Color wallColor = Color.WHITE;

    private Location apple;
    private Vector<Location> snake;

    public ArenaPanel(int x, int y)
    {
        gridX=x;
        gridY=y;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    public ArenaPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(defaultX * gridX, defaultY * gridY);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() == "snakeModel") {

        }else if(evt.getPropertyName()=="appleLocation")
        {

        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, defaultX * gridX, defaultY * gridY);
        drawApple(g);
    }
    protected void drawSnake(Graphics g){
        Location current,next;
        while (current!=) {
            
        }
    }
    protected void drawApple(Graphics g) {
        g.setColor(appleColor);
        g.fillRect(apple.x * defaultX, apple.y * defaultY, defaultX, defaultY);
    }
}
