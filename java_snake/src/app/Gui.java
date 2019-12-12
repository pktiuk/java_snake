package app;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;


/**
 * <code>Gui</code> - GUI visualizing snake, arena, and apples
 * shows state of game and reads pressed keys.
 */
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

    private static final long serialVersionUID = 1L;

    private int defaultX = 15; // default size of box
    private int defaultY = 15;
    private int gridX = 40;
    private int gridY = 40;
    private Color snakeColor = Color.GREEN;
    private Color snakeBackColor = Color.BLUE;
    private Color appleColor = Color.RED;
    private Color wallColor = Color.WHITE;
    public KeyWatch buttonsListener = new KeyWatch();

    private Location apple = new Location(0, 0);
    private Vector<Location> snake = new Vector<Location>();
    private boolean[][] wall;

    private int lastButton = 0;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean gameLost=false;

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
            apple = (Location) evt.getNewValue();
            repaint();
        } else if (evt.getPropertyName() == "wall") {
            wall = (boolean[][]) evt.getNewValue();
            repaint();
        }else if (evt.getPropertyName() == "gameLost") {
            gameLost=true;
            repaint();
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
        drawWalls(g);
        drawApple(g);
        drawSnake(g);
        if(gameLost)
        {
            gameLost=false;
            drawLost(g);
        }
    }

    protected void drawSnake(Graphics g) {
        g.setColor(snakeColor);
        for (int i = 0; i < snake.size() - 1; i++) {
            if (snake.get(i).x == snake.get(i + 1).x) {
                g.fillRect(snake.get(i).x * defaultX, Math.min(snake.get(i).y, snake.get(i + 1).y) * defaultY, defaultX,
                        Math.abs(snake.get(i).y - snake.get(i + 1).y) * defaultY + defaultY);

            } else {
                g.fillRect(Math.min(snake.get(i).x, snake.get(i + 1).x) * defaultX, snake.get(i).y * defaultY,
                        Math.abs(snake.get(i).x - snake.get(i + 1).x) * defaultX + defaultX, defaultY);
            }

        }
        for (int i = 0; i < snake.size() - 1; i++) {
            g.setColor(snakeBackColor);
            if (snake.get(i).x == snake.get(i + 1).x) {
                g.fillRect(snake.get(i).x * defaultX + defaultX / 2 - 1,
                        Math.min(snake.get(i).y, snake.get(i + 1).y) * defaultY + defaultY / 2, 3,
                        Math.abs(snake.get(i).y - snake.get(i + 1).y) * defaultY + 3);
            } else {
                g.fillRect(Math.min(snake.get(i).x, snake.get(i + 1).x) * defaultX + defaultX / 2 - 1,
                        snake.get(i).y * defaultY + defaultY / 2,
                        Math.abs(snake.get(i).x - snake.get(i + 1).x) * defaultX + 3, 3);
            }

        }
    }

    protected void drawApple(Graphics g) {
        g.setColor(appleColor);
        g.fillRect(apple.x * defaultX, apple.y * defaultY, defaultX, defaultY);
    }

    protected void drawWalls(Graphics g) {
        for (int i = 0; i < gridX; i++) {
            for (int j = 0; j < gridY; j++) {
                if (wall[i][j]) {
                    g.setColor(wallColor);
                    g.fillRect(i * defaultX, j * defaultY, defaultX, defaultY);
                }
            }
        }
    }

    protected void drawLost(Graphics g)
    {
        g.setColor(Color.WHITE);
        Font  f1  = new Font(Font.SERIF, Font.BOLD,  20);
        g.setFont(f1);
        g.drawString("LOST", defaultX*gridX/2 - 20, defaultY*3);
    }

    class KeyWatch implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (lastButton != e.getKeyCode()) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    pcs.firePropertyChange("pauseButton", false, true);
                    break;
                case KeyEvent.VK_UP:
                    pcs.firePropertyChange("directionChanged", null, Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    pcs.firePropertyChange("directionChanged", null, Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    pcs.firePropertyChange("directionChanged", null, Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    pcs.firePropertyChange("directionChanged", null, Direction.RIGHT);
                    break;
                default:
                    break;
                }
                lastButton = e.getKeyCode();
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

}
