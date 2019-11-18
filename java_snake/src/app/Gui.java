package app;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

public class Gui {
    ArenaPanel arena;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui window = new Gui(40, 40);
                window.arena.repaint();
            }
        });
    }

    public Gui(int x, int y) {
        JFrame f = new JFrame("JavaSnake");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        arena = new ArenaPanel(x, y);
        f.add(arena);
        f.pack();
        f.setVisible(true);
        f.addKeyListener(arena.buttonsListener);
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
    public KeyWatch buttonsListener=new KeyWatch();

    private Location apple = new Location(0, 0);
    private Vector<Location> snake = new Vector<Location>();

    public ArenaPanel(int x, int y) {
        gridX = x;
        gridY = y;
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
            snake = (Vector<Location>) evt.getNewValue();
            repaint();
        } else if (evt.getPropertyName() == "appleLocation") {

        }
    }

    public void setApple(Location newApple) {
        apple = newApple;
    }

    public void setSnake(Vector<Location> newSnake) {
        snake = newSnake;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, defaultX * gridX, defaultY * gridY);
        drawApple(g);
        drawSnake(g);
    }

    protected void drawSnake(Graphics g) {
        for (int i = 0; i < snake.size() - 1; i++) {
            g.setColor(snakeColor);
            if (snake.get(i).x == snake.get(i + 1).x) {
                g.fillRect(snake.get(i).x * defaultX, Math.min(snake.get(i).y, snake.get(i + 1).y) * defaultY, defaultX,
                        Math.abs(snake.get(i).y - snake.get(i + 1).y) * defaultY + defaultY);
            } else {
                g.fillRect(Math.min(snake.get(i).x, snake.get(i + 1).x) * defaultX, snake.get(i).y * defaultY,
                        Math.abs(snake.get(i).x - snake.get(i + 1).x) * defaultX + defaultX, defaultY);
            }

        }
    }

    protected void drawApple(Graphics g) {
        g.setColor(appleColor);
        g.fillRect(apple.x * defaultX, apple.y * defaultY, defaultX, defaultY);
    }

    class KeyWatch implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 32: //SPACE
                      
                    break;
                default:

                    break;
            }
        }
    }
    

}
