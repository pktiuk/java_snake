package app;

import javax.swing.SwingUtilities;

/**
 * main class for whole application
 */
public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Game started");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int x = 40;
                int y = 40;
                Model m = new Model(x, y);
                Gui g = new Gui(x, y);
                Controller c = new Controller(x, y, m, g);
            }
        });
    }

}
