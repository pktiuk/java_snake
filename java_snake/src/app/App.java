package app;

import javax.swing.SwingUtilities;

/**
 * Main class for whole application - whole app is turned on from there
 */
public class App {

    /**
     * Main function initializing all of the elements and connecting them
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Game started");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int x = 30;
                int y = 30;
                try {
                    if (args.length >= 1) {
                        x = Integer.parseInt(args[0]);
                        if (args.length >= 2) {
                            y = Integer.parseInt(args[1]);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Wrong value of arguments (expected x y as intigers)");
                }
                
                Model m = new Model(x, y);
                Gui g = new Gui(x, y);
                Controller c = new Controller(x, y, m, g);
            }
        });
    }

}
