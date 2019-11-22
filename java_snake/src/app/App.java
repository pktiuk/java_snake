package app;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Controller c = new Controller(40, 40);
            }
        });
    }

}
